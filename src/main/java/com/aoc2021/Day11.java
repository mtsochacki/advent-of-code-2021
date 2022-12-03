package com.aoc2021;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day11 implements Day {
    private static class Point {
        int x;
        int y;

        Point(int horizontal, int vertical) {
            x = horizontal;
            y = vertical;
        }
    }

    private static List<ArrayList<Integer>> readInput(String filename) {
        ArrayList<ArrayList<Integer>> octoGrid = new ArrayList<>();
        try (Scanner sc = new Scanner(new File(filename))) {
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
        } catch (Exception e) {
            System.out.println("Something went wrong " + e);
        }
        return octoGrid;
    }

    private static List<Point> getNeighbours(int x, int y, int width, int height) {
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

    private static void increaseAll(List<ArrayList<Integer>> octoGrid) {
        for (ArrayList<Integer> octoRow : octoGrid) {
            octoRow.replaceAll(integer -> integer + 1);
        }
    }

    private static void increaseUnflashed(List<ArrayList<Integer>> octoGrid,
                                          int x, int y) {
        if (octoGrid.get(y).get(x) != 0) {
            octoGrid.get(y).set(x, octoGrid.get(y).get(x) + 1);
        }
    }

    private static void processFlashes(List<ArrayList<Integer>> octoGrid) {
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

    private static boolean isFlashReady(List<ArrayList<Integer>> octoGrid) {
        for (ArrayList<Integer> octoRow : octoGrid) {
            for (Integer octopus : octoRow) {
                if (octopus > 9) {
                    return true;
                }
            }
        }
        return false;
    }

    private static int countFlashes(List<ArrayList<Integer>> octoGrid) {
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

    private static boolean areAllFlashing(List<ArrayList<Integer>> octoGrid) {
        for (ArrayList<Integer> octoRow : octoGrid) {
            for (Integer octopus : octoRow) {
                if (octopus != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private static int processStep(List<ArrayList<Integer>> octoGrid) {
        increaseAll(octoGrid);
        while (isFlashReady(octoGrid)) {
            processFlashes(octoGrid);
        }
        return countFlashes(octoGrid);
    }

    private static int countAllFlashes(int steps, String filename) {
        List<ArrayList<Integer>> octoGrid = readInput(filename);
        int flashes = 0;
        for (int i = 0; i < steps; i++) {
            flashes += processStep(octoGrid);
        }
        return flashes;
    }

    private static int findSyncStep(int steps, String filename) {
        List<ArrayList<Integer>> octoGrid = readInput(filename);
        for (int i = 0; i < steps; i++) {
            processStep(octoGrid);
            if (areAllFlashing(octoGrid)) {
                return i + 1;
            }
        }
        return -1;
    }

    public String part1(String filename) {
        return String.valueOf(countAllFlashes(100, filename));
    }

    public String part2(String filename) {
        return String.valueOf(findSyncStep(500, filename));
    }
}