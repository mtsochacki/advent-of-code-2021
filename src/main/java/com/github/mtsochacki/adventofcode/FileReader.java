package com.github.mtsochacki.adventofcode;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class FileReader {
    List<String> readFile(File file) throws IOException {
        return Files.readAllLines(file.toPath());
    }
}
