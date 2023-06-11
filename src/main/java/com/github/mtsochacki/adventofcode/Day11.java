package com.github.mtsochacki.adventofcode;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Slf4j
public class Day11 implements Day {
    private static class Point {
        int x;
        int y;

        Point(int horizontal, int vertical) {
            x = horizontal;
            y = vertical;
        }
    }

    private List<List<Integer>> readInput(String filename) {
        List<List<Integer>> octoGrid = new ArrayList<>();
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
            log.error("Something went horribly wrong: {}", e.getMessage());
        }
        return octoGrid;
    }

    private List<Point> getNeighbours(int x, int y, int width, int height) {
        List<Point> neighbours = new ArrayList<>();
        for (int nx = Math.max(0, x - 1); nx < Math.min(width, x + 2); ++nx) {
            for (int ny = Math.max(0, y - 1); ny < Math.min(height, y + 2); ++ny) {
                if (nx != x || ny != y) {
                    neighbours.add(new Point(nx, ny));
                }
            }
        }
        return neighbours;
    }

    private void increaseAll(List<List<Integer>> octoGrid) {
        for (List<Integer> octoRow : octoGrid) {
            for (int i = 0; i < octoRow.size(); i++) {
                octoRow.set(i, octoRow.get(i) + 1);
            }
        }
    }

    private void increaseUnflashed(List<List<Integer>> octoGrid,
                                   int x, int y) {
        if (octoGrid.get(y).get(x) != 0) {
            octoGrid.get(y).set(x, octoGrid.get(y).get(x) + 1);
        }
    }

    private void processFlashes(List<List<Integer>> octoGrid) {
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

    private boolean isFlashReady(List<List<Integer>> octoGrid) {
        for (List<Integer> octoRow : octoGrid) {
            for (Integer octopus : octoRow) {
                if (octopus > 9) {
                    return true;
                }
            }
        }
        return false;
    }

    private int countFlashes(List<List<Integer>> octoGrid) {
        int counter = 0;
        for (List<Integer> octoRow : octoGrid) {
            for (Integer octopus : octoRow) {
                if (octopus == 0) {
                    counter++;
                }
            }
        }
        return counter;
    }

    private boolean areAllFlashing(List<List<Integer>> octoGrid) {
        for (List<Integer> octoRow : octoGrid) {
            for (Integer octopus : octoRow) {
                if (octopus != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private int processStep(List<List<Integer>> octoGrid) {
        increaseAll(octoGrid);
        while (isFlashReady(octoGrid)) {
            processFlashes(octoGrid);
        }
        return countFlashes(octoGrid);
    }

    private int countAllFlashes(int steps, String filename) {
        List<List<Integer>> octoGrid = readInput(filename);
        int flashes = 0;
        for (int i = 0; i < steps; i++) {
            flashes += processStep(octoGrid);
        }
        return flashes;
    }

    private int findSyncStep(int steps, String filename) {
        List<List<Integer>> octoGrid = readInput(filename);
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