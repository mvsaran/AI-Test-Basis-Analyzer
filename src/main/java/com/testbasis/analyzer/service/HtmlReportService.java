package com.testbasis.analyzer.service;

import com.testbasis.analyzer.models.TestBasisOutput;

import java.util.List;

public class HtmlReportService {

    public static void generateReport(TestBasisOutput output, String outputPath) {
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>\n")
            .append("<html lang='en'>\n")
            .append("<head>\n")
            .append("  <meta charset='UTF-8'>\n")
            .append("  <title>AI Test Basis Report</title>\n")
            .append("  <style>\n")
            .append("    body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f4f7f6; color: #333; margin: 40px; }\n")
            .append("    h1 { color: #2c3e50; border-bottom: 2px solid #3498db; padding-bottom: 10px; }\n")
            .append("    .section { background: white; padding: 20px; margin-bottom: 20px; border-radius: 8px; box-shadow: 0 4px 6px rgba(0,0,0,0.1); }\n")
            .append("    h2 { color: #2980b9; margin-top: 0; }\n")
            .append("    ul { list-style-type: square; }\n")
            .append("    li { margin-bottom: 8px; }\n")
            .append("    .badge { display: inline-block; padding: 4px 8px; background: #3498db; color: white; border-radius: 12px; font-size: 12px; margin-left: 10px; }\n")
            .append("  </style>\n")
            .append("</head>\n")
            .append("<body>\n")
            .append("  <h1>AI Test Basis Analyzer Report</h1>\n");

        html.append(buildSection("Entities", output.getEntities(), "#9b59b6"));
        html.append(buildSection("Actions", output.getActions(), "#e67e22"));
        html.append(buildSection("Validations", output.getValidations(), "#27ae60"));
        html.append(buildSection("Flows", output.getFlows(), "#f39c12"));
        html.append(buildSection("Edge Cases", output.getEdgeCases(), "#e74c3c"));

        html.append("</body>\n</html>");

        FileService.writeFile(outputPath, html.toString());
    }

    private static String buildSection(String title, List<String> items, String colorCode) {
        StringBuilder section = new StringBuilder();
        int count = items != null ? items.size() : 0;
        section.append("  <div class='section'>\n")
               .append("    <h2>").append(title).append(" <span class='badge' style='background:").append(colorCode).append("'>").append(count).append("</span></h2>\n");

        if (items != null && !items.isEmpty()) {
            section.append("    <ul>\n");
            for (String item : items) {
                section.append("      <li>").append(escapeHtml(item)).append("</li>\n");
            }
            section.append("    </ul>\n");
        } else {
            section.append("    <p><i>No data found.</i></p>\n");
        }
        section.append("  </div>\n");
        return section.toString();
    }

    private static String escapeHtml(String text) {
        if (text == null) return "";
        return text.replace("&", "&amp;")
                   .replace("<", "&lt;")
                   .replace(">", "&gt;")
                   .replace("\"", "&quot;")
                   .replace("'", "&#39;");
    }
}
