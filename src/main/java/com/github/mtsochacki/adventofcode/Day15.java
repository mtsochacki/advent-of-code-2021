package com.github.mtsochacki.adventofcode;

import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

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
        return String.valueOf(calculateRisk(false, input));
    }

    @Override
    public String part2(List<String> input) {
        return String.valueOf(calculateRisk(true, input));
    }

    private List<List<Integer>> readInput(List<String> input) {
        return input.stream()
                .map(line -> {
                    String[] strings = line.split("");
                    return Arrays.stream(strings).map(Integer::parseInt).toList();
                })
                .toList();
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

    private int calculateRisk(boolean isPart2, List<String> input) {
        List<List<Integer>> mapOfRisks = readInput(input);
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