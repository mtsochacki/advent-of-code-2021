package com.github.mtsochacki.advent_of_code;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day05 implements Day {
    private static class LineOfVents {
        int xStart;
        int xEnd;
        int yStart;
        int yEnd;
    }

    private Boolean isVertical(LineOfVents line) {
        return line.xStart == line.xEnd;
    }

    private Boolean isHorizontal(LineOfVents line) {
        return line.yStart == line.yEnd;
    }

    private List<LineOfVents> readInput(String filename) {
        ArrayList<LineOfVents> input = new ArrayList<>();
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
            System.out.println("Something went wrong" + e);
        }
        return input;
    }

    private int calculateOverlap(String filename, Boolean includePart2) {
        List<LineOfVents> input = readInput(filename);
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
        return count;
    }

    public String part1(String filename) {
        return String.valueOf(calculateOverlap(filename, false));
    }

    public String part2(String filename) {
        return String.valueOf(calculateOverlap(filename, true));
    }
}