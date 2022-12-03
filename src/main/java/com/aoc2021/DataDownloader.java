package com.aoc2021;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class DataDownloader {
    public void downloadData(int day) throws IOException {
        Path path = Paths.get("src/main/resources/day" + day + ".txt");
        System.out.println(path);
        if (Files.exists(path)) {
            System.out.println("Using cached file");
            return;
        }
        String cookieString = null;
        try (Scanner sc = new Scanner(new File("src/main/resources/cookieSession.txt"))) {
            cookieString = sc.next();
        }
        String command =
                "curl -b " + cookieString + " https://adventofcode.com/2021/day/" + day + "/input -o " + path;
        System.out.println("Downloading input");
        Runtime.getRuntime().exec(command);
    }
}
