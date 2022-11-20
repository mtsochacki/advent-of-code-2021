package com.github.mtsochacki.advent_of_code;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day01 implements Day {
    private static List<Integer> readReport(String filename) {
        List<Integer> input = new ArrayList<>();
        try (Scanner sc = new Scanner(new File(filename))){
            while (sc.hasNextInt()) {
                input.add(sc.nextInt());
            }
        } catch (Exception e) {
            System.out.println("Something went horribly wrong: " + e);
        }
        return input;
    }

    public String part1(String filename) {
        List<Integer> report = readReport(filename);
        int counter = 0;
        int previousMeasurement = report.get(0);
        for (Integer currentMeasurement : report) {
            if (currentMeasurement > previousMeasurement) {
                counter++;
            }
            previousMeasurement = currentMeasurement;
        }
        return String.valueOf(counter);
    }

    public String part2(String filename) {
        List<Integer> report = readReport(filename);
        int counter = 0;
        for (int i = 0; i < report.size() - 3; i++) {
            if (report.get(i) < report.get(i + 3)) {
                counter++;
            }
        }
        return String.valueOf(counter);
    }
}