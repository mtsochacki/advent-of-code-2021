package com.github.mtsochacki.advent_of_code;

import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;

public class Day05 {
    public static class LineOfVents {
        int xStart;
        int xEnd;
        int yStart;
        int yEnd;
    }

    public static Boolean isVertical(LineOfVents line) {
        return line.xStart == line.xEnd; 
    }

    public static Boolean isHorizontal(LineOfVents line) {
        return line.yStart == line.yEnd;
    }

    public static ArrayList<LineOfVents> readInput(String filename) {
        Scanner sc;
        ArrayList<LineOfVents> input = new ArrayList<>();
        try {
            sc = new Scanner(new File(filename));
            sc.useDelimiter(",|\\n| -> ");
            while (sc.hasNextInt()) {
                LineOfVents line = new LineOfVents();
                line.xStart = sc.nextInt();
                line.yStart = sc.nextInt();
                line.xEnd = sc.nextInt();
                line.yEnd = sc.nextInt();
                input.add(line);
            }
            sc.close();
        } catch (Exception e) {
            System.out.println("Something went wrong" + e);
        }
        return input;
    }

    public static int calculateOverlap(Boolean includePart2) {
        ArrayList<LineOfVents> input = readInput("data.txt");
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
        for (int[] line : diagram){
            for (int cell : line){
                if (cell >= 2){
                    count++;
                }
            }
        }
        return count;
    }

    public static void main(String[] args) {
        System.out.println(calculateOverlap(false));
        System.out.println(calculateOverlap(true));
    }
}