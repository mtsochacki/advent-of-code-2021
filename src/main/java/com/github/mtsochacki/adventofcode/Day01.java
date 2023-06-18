package com.github.mtsochacki.adventofcode;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class Day01 implements Day {
    @Override
    public String part1(List<String> input) {
        List<Integer> report = transformIntoReport(input);
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

    @Override
    public String part2(List<String> input) {
        List<Integer> report = transformIntoReport(input);
        int counter = 0;
        for (int i = 0; i < report.size() - 3; i++) {
            if (report.get(i) < report.get(i + 3)) {
                counter++;
            }
        }
        return String.valueOf(counter);
    }

    private List<Integer> transformIntoReport(List<String> input) {
        return input.stream()
                .map(Integer::valueOf)
                .toList();
    }
}