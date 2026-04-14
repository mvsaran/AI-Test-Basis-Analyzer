package com.testbasis.analyzer.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileService {

    public static String readFile(String relativePath) {
        try {
            Path path = Paths.get(relativePath);
            if (!Files.exists(path)) {
                return "";
            }
            return Files.readString(path);
        } catch (IOException e) {
            System.err.println("Warning: Could not read file " + relativePath + " - " + e.getMessage());
            return "";
        }
    }

    public static void writeFile(String relativePath, String content) {
        try {
            Path path = Paths.get(relativePath);
            if (path.getParent() != null && !Files.exists(path.getParent())) {
                Files.createDirectories(path.getParent());
            }
            Files.writeString(path, content);
            System.out.println("✅ Output successfully written to: " + path.toAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error: Could not write file " + relativePath + " - " + e.getMessage());
        }
    }
}
