package com.aoc2021;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Day17 implements Day {
    private int total = 0;

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

    private boolean isInTrench(int xVelocity, int yVelocity,
                               int xStart, int xEnd, int yStart, int yEnd) {
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

    private int[] readInput(String filename) {
        int[] trenchCoordinates = new int[4];
        try (Scanner sc = new Scanner(new File(filename))) {
            String[] newLine = sc.nextLine().split(":")[1].split(",");
            String[] coordinatesX = newLine[0].split("=")[1].split("\\..");
            String[] coordinatesY = newLine[1].split("=")[1].split("\\..");
            trenchCoordinates[0] = Integer.parseInt(coordinatesX[0]);
            trenchCoordinates[1] = Integer.parseInt(coordinatesX[1]);
            trenchCoordinates[2] = Integer.parseInt(coordinatesY[1]);
            trenchCoordinates[3] = Integer.parseInt(coordinatesY[0]);
        } catch (Exception e) {
            System.out.println("There was an error " + e);
        }
        return trenchCoordinates;
    }

    public String part1(String filename) {
        int[] trenchCoordinates = readInput(filename);
        int xStart = trenchCoordinates[0];
        int xEnd = trenchCoordinates[1];
        int yStart = trenchCoordinates[2];
        int yEnd = trenchCoordinates[3];
        ArrayList<Integer> xVelocities = new ArrayList<>();
        ArrayList<Integer> yVelocities = new ArrayList<>();
        for (int i = -500; i < 500; i++) {
            if (isXInTrench(i, xStart, xEnd)) {
                xVelocities.add(i);
            }
            if (isYInTrench(i, yStart, yEnd)) {
                yVelocities.add(i);
            }
        }
        for (Integer xVelocity : xVelocities) {
            for (Integer yVelocity : yVelocities) {
                if (isInTrench(xVelocity, yVelocity, xStart, xEnd, yStart, yEnd)) {
                    total++;
                }
            }
        }
        return String.valueOf(calculateHeight(yVelocities.get(yVelocities.size() - 1)));
    }

    public String part2(String filename) {
        part1(filename);
        return String.valueOf(total);
    }
}