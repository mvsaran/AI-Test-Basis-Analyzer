package com.testbasis.analyzer.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class LLMService {
    private final String apiKey;
    private final OkHttpClient client;
    private final ObjectMapper mapper;

    public LLMService() {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        this.apiKey = dotenv.get("OPENAI_API_KEY", System.getenv("OPENAI_API_KEY"));
        
        if (this.apiKey == null || this.apiKey.trim().isEmpty()) {
            throw new RuntimeException("OPENAI_API_KEY is missing. Please set it in the .env file or system environments.");
        }
        
        this.client = new OkHttpClient.Builder()
            .readTimeout(120, TimeUnit.SECONDS)
            .build();
        this.mapper = new ObjectMapper();
    }

    public String analyzeInputs(String requirement, String logs, String apiSpec) throws IOException {
        String systemPrompt = "You are a Senior SDET and AI Engineer. Your goal is to convert unstructured inputs " +
                "(requirements, logs, API specs) into a structured, machine-readable test basis dataset.\n\n" +
                "You must parse this data and output pure JSON matching the following schema natively:\n" +
                "{\n" +
                "  \"entities\": [\"e.g., user, admin, database\"],\n" +
                "  \"actions\": [\"e.g., login, submit data\"],\n" +
                "  \"validations\": [\"e.g., error displayed on invalid input, success message\"],\n" +
                "  \"flows\": [\"e.g., User logs in -> Submits form -> Form is saved\"],\n" +
                "  \"edge_cases\": [\"e.g., network timeout, invalid token format\"]\n" +
                "}\n\n" +
                "Instructions:\n" +
                "1. Analyze Requirement text to extract actors, actions, conditions, and validations.\n" +
                "2. Analyze Logs to identify errors, categorize failure types, and document them as edge_cases or validations.\n" +
                "3. Use the API Spec (if any) to augment the actions and entities.\n" +
                "4. Consolidate everything into the single JSON output.\n" +
                "Ensure NO markdown formatting (like ```json), just pure JSON text.";

        String userPrompt = String.format("REQUIREMENT:\n%s\n\nLOGS:\n%s\n\nAPI_SPEC:\n%s", 
                requirement.isEmpty() ? "N/A" : requirement, 
                logs.isEmpty() ? "N/A" : logs, 
                apiSpec.isEmpty() ? "N/A" : apiSpec);

        String requestBodyJson = String.format("""
            {
                "model": "gpt-4o",
                "messages": [
                    {"role": "system", "content": %s},
                    {"role": "user", "content": %s}
                ],
                "response_format": { "type": "json_object" },
                "temperature": 0.2
            }
            """, 
            mapper.writeValueAsString(systemPrompt), 
            mapper.writeValueAsString(userPrompt)
        );

        RequestBody body = RequestBody.create(requestBodyJson, MediaType.get("application/json"));
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/chat/completions")
                .header("Authorization", "Bearer " + apiKey)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("OpenAI API call failed with code " + response.code() + ": " + response.body().string());
            }
            String responseBody = response.body().string();
            JsonNode rootNode = mapper.readTree(responseBody);
            return rootNode.path("choices").get(0).path("message").path("content").asText();
        }
    }
}
