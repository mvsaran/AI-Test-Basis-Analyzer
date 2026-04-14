package com.testbasis.analyzer.service;

import com.testbasis.analyzer.models.TestBasisOutput;
import java.util.List;

public class PlaywrightGeneratorService {

    public static void generatePlaywrightTests(TestBasisOutput output, String outputPath) {
        StringBuilder script = new StringBuilder();
        
        script.append("// AI Generated Playwright Test Suite\n")
              .append("// Generated based on extracted Test Basis Flows\n\n")
              .append("const { test, expect } = require('@playwright/test');\n\n");

        List<String> flows = output.getFlows();
        if (flows != null && !flows.isEmpty()) {
            for (int i = 0; i < flows.size(); i++) {
                String flow = flows.get(i);
                String testName = "AI-Generated Test Case " + (i + 1) + ": " + flow;
                
                script.append("test('").append(testName.replace("'", "\\'")).append("', async ({ page }) => {\n")
                      .append("    // Flow: ").append(flow).append("\n")
                      .append("    console.log('Starting flow execution...');\n\n");

                // Break the flow into segments and add as comments
                String[] steps = flow.split("->");
                for (String step : steps) {
                    script.append("    // Action: ").append(step.trim()).append("\n")
                          .append("    // TODO: Implement interaction for '").append(step.trim()).append("'\n\n");
                }

                script.append("    console.log('Flow extraction complete.');\n")
                      .append("});\n\n");
            }
        } else {
            script.append("// No flows found in Test Basis. Check requirements/logs.\n");
        }

        FileService.writeFile(outputPath, script.toString());
    }
}
