package com.github.mtsochacki.advent_of_code;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Day11 {
    public static class Point {
        int x;
        int y;

        Point(int horizontal, int vertical) {
            x = horizontal;
            y = vertical;
        }
    }

    public static ArrayList<ArrayList<Integer>> readInput(String filename) {
        ArrayList<ArrayList<Integer>> octoGrid = new ArrayList<>();
        Scanner sc;
        try {
            sc = new Scanner(new File(filename));
            sc.useDelimiter("");
            while (sc.hasNextInt()) {
                ArrayList<Integer> octoRow = new ArrayList<>();
                while (sc.hasNextInt()) {
                    octoRow.add(sc.nextInt());
                }
                octoGrid.add(octoRow);
                if (sc.hasNextLine()) {
                    sc.nextLine();
                }
            }
            sc.close();
        } catch (Exception e) {
            System.out.println("Something went wrong " + e);
        }
        return octoGrid;
    }

    public static ArrayList<Point> getNeighbours(int x, int y, int width, int height) {
        ArrayList<Point> neighbours = new ArrayList<>();
        for (int nx = Math.max(0, x - 1); nx < Math.min(width, x + 2); ++nx) {
            for (int ny = Math.max(0, y - 1); ny < Math.min(height, y + 2); ++ny) {
                if (nx != x || ny != y) {
                    neighbours.add(new Point(nx, ny));
                }
            }
        }
        return neighbours;
    }

    public static void increaseAll(ArrayList<ArrayList<Integer>> octoGrid) {
        for (ArrayList<Integer> octoRow : octoGrid) {
            for (int i = 0; i < octoRow.size(); i++) {
                octoRow.set(i, octoRow.get(i) + 1);
            }
        }
    }

    public static void increaseUnflashed(ArrayList<ArrayList<Integer>> octoGrid,
            int x, int y) {
        if (octoGrid.get(y).get(x) != 0) {
            octoGrid.get(y).set(x, octoGrid.get(y).get(x) + 1);
        }
    }

    public static void processFlashes(ArrayList<ArrayList<Integer>> octoGrid) {
        int width = octoGrid.get(0).size();
        int height = octoGrid.size();
        for (int j = 0; j < octoGrid.size(); j++) {
            for (int k = 0; k < octoGrid.get(j).size(); k++) {
                if (octoGrid.get(j).get(k) > 9) {
                    octoGrid.get(j).set(k, 0);
                    for (Point point : getNeighbours(k, j, height, width)) {
                        increaseUnflashed(octoGrid, point.x, point.y);
                    }
                }
            }
        }
    }

    public static boolean isFlashReady(ArrayList<ArrayList<Integer>> octoGrid) {
        for (ArrayList<Integer> octoRow : octoGrid) {
            for (Integer octopus : octoRow) {
                if (octopus > 9) {
                    return true;
                }
            }
        }
        return false;
    }

    public static int countFlashes(ArrayList<ArrayList<Integer>> octoGrid) {
        int counter = 0;
        for (ArrayList<Integer> octoRow : octoGrid) {
            for (Integer octopus : octoRow) {
                if (octopus == 0) {
                    counter++;
                }
            }
        }
        return counter;
    }

    public static boolean areAllFlashing(ArrayList<ArrayList<Integer>> octoGrid) {
        for (ArrayList<Integer> octoRow : octoGrid) {
            for (Integer octopus : octoRow) {
                if (octopus != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public static int processStep(ArrayList<ArrayList<Integer>> octoGrid) {
        increaseAll(octoGrid);
        while (isFlashReady(octoGrid)) {
            processFlashes(octoGrid);
        }
        return countFlashes(octoGrid);
    }

    public static int countAllFlashes(int steps) {
        ArrayList<ArrayList<Integer>> octoGrid = readInput("data.txt");
        int flashes = 0;
        for (int i = 0; i < steps; i++) {
            flashes += processStep(octoGrid);
        }
        return flashes;
    }

    public static int findSyncStep(int steps) {
        ArrayList<ArrayList<Integer>> octoGrid = readInput("data.txt");
        for (int i = 0; i < steps; i++) {
            processStep(octoGrid);
            if (areAllFlashing(octoGrid)) {
                return i + 1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        System.out.println(countAllFlashes(100));
        System.out.println(findSyncStep(500));
    }
}