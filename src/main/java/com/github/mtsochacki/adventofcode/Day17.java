package com.github.mtsochacki.adventofcode;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Day17 implements Day {
    private boolean isYInTrench(int yVelocity, int yStart, int yEnd) {
        int y = 0;
        while (!(y >= yEnd && y <= yStart)) {
            y += yVelocity;
            if (y < yEnd) {
                return false;
            }
            yVelocity--;
        }
        return true;
    }

    private int calculateHeight(int yVelocity) {
        int y = 0;
        while (yVelocity > 0) {
            y += yVelocity;
            yVelocity--;
        }
        return y;
    }

    private boolean isXInTrench(int xVelocity, int xStart, int xEnd) {
        int x = 0;
        while (!(x >= xStart && x <= xEnd)) {
            x += xVelocity;
            if (x > xEnd || xVelocity == 0) {
                return false;
            }
            xVelocity--;
        }
        return true;
    }

    @Override
    public String part1(List<String> input) {
        int[] trenchCoordinates = readInput(input);
        int yStart = trenchCoordinates[2];
        int yEnd = trenchCoordinates[3];
        List<Integer> yVelocities = new ArrayList<>();
        for (int i = -500; i < 500; i++) {
            if (isYInTrench(i, yStart, yEnd)) {
                yVelocities.add(i);
            }
        }
        return String.valueOf(calculateHeight(yVelocities.get(yVelocities.size() - 1)));
    }

    @Override
    public String part2(List<String> input) {
        int[] trenchCoordinates = readInput(input);
        int xStart = trenchCoordinates[0];
        int xEnd = trenchCoordinates[1];
        int yStart = trenchCoordinates[2];
        int yEnd = trenchCoordinates[3];
        List<Integer> xVelocities = new ArrayList<>();
        List<Integer> yVelocities = new ArrayList<>();
        for (int i = -500; i < 500; i++) {
            if (isXInTrench(i, xStart, xEnd)) {
                xVelocities.add(i);
            }
            if (isYInTrench(i, yStart, yEnd)) {
                yVelocities.add(i);
            }
        }
        int total = 0;
        for (Integer xVelocity : xVelocities) {
            for (Integer yVelocity : yVelocities) {
                if (isInTrench(xVelocity, yVelocity, xStart, xEnd, yStart, yEnd)) {
                    total++;
                }
            }
        }
        return String.valueOf(total);
    }

    private boolean isInTrench(int xVelocity, int yVelocity, int xStart, int xEnd, int yStart, int yEnd) {
        int x = 0;
        int y = 0;
        while (!(x >= xStart && x <= xEnd && y >= yEnd && y <= yStart)) {
            x += xVelocity;
            y += yVelocity;
            if (x < xStart && xVelocity == 0 || x > xEnd || y < yEnd) {
                return false;
            }
            if (xVelocity > 0) {
                xVelocity--;
            }
            yVelocity--;
        }
        return true;
    }

    private int[] readInput(List<String> input) {
        String line = input.get(0);
        String[] newLine = line.split(":")[1].split(",");
        String[] coordinatesX = newLine[0].split("=")[1].split("\\..");
        String[] coordinatesY = newLine[1].split("=")[1].split("\\..");
        return new int[]{Integer.parseInt(coordinatesX[0]), Integer.parseInt(coordinatesX[1]), Integer.parseInt(coordinatesY[1]), Integer.parseInt(coordinatesY[0])};
    }
}