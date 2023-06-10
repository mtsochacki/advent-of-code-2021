package com.github.mtsochacki.advent_of_code;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Slf4j
public class Day01 implements Day {
    private List<Integer> readReport(String filename) {
        List<Integer> input = new ArrayList<>();
        try (Scanner sc = new Scanner(new File(filename))) {
            while (sc.hasNextInt()) {
                input.add(sc.nextInt());
            }
        } catch (Exception e) {
            log.error("Something went horribly wrong: {}", e.getMessage());
        }
        return input;
    }

    public String part1() {
        List<Integer> report = readReport("data.txt");
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

    public String part2() {
        List<Integer> report = readReport("data.txt");
        int counter = 0;
        for (int i = 0; i < report.size() - 3; i++) {
            if (report.get(i) < report.get(i + 3)) {
                counter++;
            }
        }
        return String.valueOf(counter);
    }
}