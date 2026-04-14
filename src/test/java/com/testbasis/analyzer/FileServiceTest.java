package com.testbasis.analyzer;

import com.testbasis.analyzer.service.FileService;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class FileServiceTest {

    @Test
    public void testFileWriteAndRead() throws IOException {
        String testFile = "test-output/temp.txt";
        String content = "Hello JUnit AI Test Basis!";

        // Test Write
        FileService.writeFile(testFile, content);

        // Test Read
        String readContent = FileService.readFile(testFile);
        assertEquals(content, readContent);

        // Cleanup
        Files.deleteIfExists(Path.of(testFile));
    }
}
