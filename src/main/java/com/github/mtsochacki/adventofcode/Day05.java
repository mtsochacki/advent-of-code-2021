package com.github.mtsochacki.adventofcode;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class Day05 implements Day {
    @Override
    public String part1(List<String> input) {
        return calculateOverlap(false, input);
    }

    @Override
    public String part2(List<String> input) {
        return calculateOverlap(true, input);
    }

    private String calculateOverlap(boolean includePart2, List<String> input) {
        List<LineOfVents> vents = readInput(input);
        int[][] diagram = new int[10][10];
        int count = 0;

        for (LineOfVents lineOfVents : vents) {
            if (isVertical(lineOfVents)) {
                for (int j = Integer.min(lineOfVents.yStart, lineOfVents.yEnd); j <= Integer.max(lineOfVents.yStart,
                        lineOfVents.yEnd); j++) {
                    diagram[j][lineOfVents.xEnd] += 1;
                }
            } else if (isHorizontal(lineOfVents)) {
                for (int j = Integer.min(lineOfVents.xStart, lineOfVents.xEnd); j <= Integer.max(lineOfVents.xStart,
                        lineOfVents.xEnd); j++) {
                    diagram[lineOfVents.yEnd][j] += 1;
                }
            } else if (includePart2) {
                int lineLength = Math.abs(lineOfVents.xStart - lineOfVents.xEnd);
                int x = Integer.min(lineOfVents.xStart, lineOfVents.xEnd);
                if ((lineOfVents.xStart < lineOfVents.xEnd) && (lineOfVents.yStart < lineOfVents.yEnd)
                        || (lineOfVents.xStart > lineOfVents.xEnd) && (lineOfVents.yStart > lineOfVents.yEnd)) {
                    int y = Integer.min(lineOfVents.yStart, lineOfVents.yEnd);
                    for (int j = 0; j <= lineLength; j++) {
                        diagram[y + j][x + j] += 1;
                    }
                } else {
                    int y = Integer.max(lineOfVents.yStart, lineOfVents.yEnd);
                    for (int j = 0; j <= lineLength; j++) {
                        diagram[y - j][x + j] += 1;
                    }
                }
            }
        }
        for (int[] line : diagram) {
            for (int cell : line) {
                if (cell >= 2) {
                    count++;
                }
            }
        }
        return String.valueOf(count);
    }

    private List<LineOfVents> readInput(List<String> input) {
        return input.stream()
                .map(line -> {
                    String[] tokens = line.split(",| -> ");
                    return new LineOfVents(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[2]), Integer.parseInt(tokens[1]), Integer.parseInt(tokens[3]));
                })
                .toList();
    }

    private boolean isVertical(LineOfVents line) {
        return line.xStart == line.xEnd;
    }

    private boolean isHorizontal(LineOfVents line) {
        return line.yStart == line.yEnd;
    }

    @AllArgsConstructor
    private static class LineOfVents {
        int xStart;
        int xEnd;
        int yStart;
        int yEnd;
    }
}