package com.testbasis.analyzer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.testbasis.analyzer.models.TestBasisOutput;
import com.testbasis.analyzer.service.FileService;
import com.testbasis.analyzer.service.LLMService;
import com.testbasis.analyzer.service.HtmlReportService;
import com.testbasis.analyzer.service.PlaywrightGeneratorService;

public class AnalyzerApp {

    public static void main(String[] args) {
        System.out.println("🚀 Starting AI Test Basis Analyzer...\n");

        String reqPath = "src/main/resources/input/requirement.txt";
        String logPath = "src/main/resources/input/logs.txt";
        String apiPath = "src/main/resources/input/api-spec.json";

        String requirementData = FileService.readFile(reqPath);
        String logData = FileService.readFile(logPath);
        String apiSpecData = FileService.readFile(apiPath);
        
        System.out.println("📦 Loaded Inputs:");
        System.out.println("- Requirements: " + (requirementData.isEmpty() ? "Not Found / Empty" : requirementData.length() + " chars"));
        System.out.println("- Logs:         " + (logData.isEmpty() ? "Not Found / Empty" : logData.length() + " chars"));
        System.out.println("- API Spec:     " + (apiSpecData.isEmpty() ? "Not Found / Empty" : apiSpecData.length() + " chars"));
        System.out.println();

        if (requirementData.isEmpty() && logData.isEmpty()) {
            System.err.println("❌ No inputs found! Please ensure input files exist in src/main/resources/input/.");
            return;
        }

        try {
            System.out.println("🧠 Sending data to OpenAI for processing...");
            LLMService llmService = new LLMService();
            String jsonResult = llmService.analyzeInputs(requirementData, logData, apiSpecData);

            // Parse and format the result nicely
            ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
            TestBasisOutput outputObj = mapper.readValue(jsonResult, TestBasisOutput.class);
            String prettyJson = mapper.writeValueAsString(outputObj);

            // Write output JSON file
            String outPath = "test-basis.json";
            FileService.writeFile(outPath, prettyJson);

            // Generate HTML Report
            String htmlPath = "test-basis-report.html";
            HtmlReportService.generateReport(outputObj, htmlPath);

            // Generate Playwright Script
            String playwrightPath = "generated-e2e.spec.js";
            PlaywrightGeneratorService.generatePlaywrightTests(outputObj, playwrightPath);

            // Print Console Summary
            System.out.println("\n📊 --- CONSOLE SUMMARY ---");
            System.out.println("Entities Found:    " + (outputObj.getEntities() != null ? outputObj.getEntities().size() : 0));
            System.out.println("Actions Found:     " + (outputObj.getActions() != null ? outputObj.getActions().size() : 0));
            System.out.println("Validations Found: " + (outputObj.getValidations() != null ? outputObj.getValidations().size() : 0));
            System.out.println("Flows Extracted:   " + (outputObj.getFlows() != null ? outputObj.getFlows().size() : 0));
            System.out.println("Edge Cases:        " + (outputObj.getEdgeCases() != null ? outputObj.getEdgeCases().size() : 0));
            System.out.println("--------------------------");
            System.out.println("✅ Process completed. Check test-basis.json, test-basis-report.html and generated-e2e.spec.js!");

        } catch (Exception e) {
            System.err.println("\n❌ An error occurred during analysis:");
            e.printStackTrace();
        }
    }
}
