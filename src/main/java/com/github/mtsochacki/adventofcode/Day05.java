package com.github.mtsochacki.adventofcode;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Slf4j
public class Day05 implements Day {
    private static class LineOfVents {
        int xStart;
        int xEnd;
        int yStart;
        int yEnd;
    }

    public String part1(String filename) {
        return calculateOverlap(false);
    }

    public String part2(String filename) {
        return calculateOverlap(true);
    }

    private boolean isVertical(LineOfVents line) {
        return line.xStart == line.xEnd;
    }

    private boolean isHorizontal(LineOfVents line) {
        return line.yStart == line.yEnd;
    }

    private List<LineOfVents> readInput(String filename) {
        List<LineOfVents> input = new ArrayList<>();
        try (Scanner sc = new Scanner(new File(filename))) {
            sc.useDelimiter(",|\\n| -> ");
            while (sc.hasNextInt()) {
                LineOfVents line = new LineOfVents();
                line.xStart = sc.nextInt();
                line.yStart = sc.nextInt();
                line.xEnd = sc.nextInt();
                line.yEnd = sc.nextInt();
                input.add(line);
            }
        } catch (Exception e) {
            log.error("Something went horribly wrong: {}", e.getMessage());
        }
        return input;
    }

    private String calculateOverlap(boolean includePart2) {
        List<LineOfVents> input = readInput("data.txt");
        int[][] diagram = new int[1000][1000];
        int count = 0;

        for (LineOfVents lineOfVents : input) {
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
}