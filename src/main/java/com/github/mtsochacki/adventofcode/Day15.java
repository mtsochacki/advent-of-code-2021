package com.github.mtsochacki.adventofcode;

import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.*;

@Slf4j
public class Day15 implements Day {
    @EqualsAndHashCode
    private static class Point {
        int x;
        int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    @EqualsAndHashCode(callSuper = true)
    public static class Position extends Point implements Comparable<Position> {
        int risk;

        Position(int x, int y, int risk) {
            super(x, y);
            this.risk = risk;
        }

        @Override
        public int compareTo(Position other) {
            return Integer.compare(this.risk, other.risk);
        }
    }


    @Override
    public String part1(List<String> input) {
        return String.valueOf(calculateRisk(false));
    }

    @Override
    public String part2(List<String> input) {
        return String.valueOf(calculateRisk(true));
    }

    private List<List<Integer>> readInput(String filename) {
        List<List<Integer>> mapOfRisks = new ArrayList<>();
        try (Scanner sc = new Scanner(new File(filename))) {
            while (sc.hasNextLine()) {
                mapOfRisks.add(splitRow(sc.nextLine()));
            }
        } catch (Exception e) {
            log.error("Something went horribly wrong: {}", e.getMessage());
        }
        return mapOfRisks;
    }

    private List<Integer> splitRow(String row) {
        List<Integer> rowOfRisks = new ArrayList<>();
        for (String s : new ArrayList<String>(Arrays.asList(row.split("")))) {
            rowOfRisks.add(Integer.parseInt(s));
        }
        return rowOfRisks;
    }

    private int getRisk(List<List<Integer>> mapOfRisks, int x, int y) {
        int width = mapOfRisks.get(0).size();
        int height = mapOfRisks.size();
        int tmpRisk = mapOfRisks.get(y % height).get(x % width) + x / width + y / height;
        if (tmpRisk < 10) {
            return tmpRisk;
        } else {
            return (tmpRisk % 10) + 1;
        }
    }

    private List<Point> getNeighbours(int x, int y, int width, int height) {
        List<Point> neighbours = new ArrayList<>();
        if (x > 0) {
            neighbours.add(new Point(x - 1, y));
        }
        if (x < width - 1) {
            neighbours.add(new Point(x + 1, y));
        }
        if (y > 0) {
            neighbours.add(new Point(x, y - 1));
        }
        if (y < height - 1) {
            neighbours.add(new Point(x, y + 1));
        }
        return neighbours;
    }

    private int calculateRisk(boolean isPart2) {
        List<List<Integer>> mapOfRisks = readInput("data.txt");
        int height = mapOfRisks.size();
        int width = mapOfRisks.get(0).size();
        if (isPart2) {
            height *= 5;
            width *= 5;
        }
        int x = 0;
        int y = 0;
        int risk = 0;
        Set<Point> visited = new HashSet<>();
        AbstractQueue<Position> positionQueue = new PriorityQueue<>();
        while (x != width - 1 || y != height - 1) {
            for (Point point : getNeighbours(x, y, width, height)) {
                if (visited.contains(point)) {
                    continue;
                }
                Position position = new Position(point.x, point.y, risk + getRisk(mapOfRisks, point.x, point.y));
                positionQueue.add(position);
                visited.add(point);
            }
            Position currentPosition = positionQueue.poll();
            x = currentPosition.x;
            y = currentPosition.y;
            risk = currentPosition.risk;
        }
        return risk;
    }
}