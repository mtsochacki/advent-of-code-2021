package com.github.mtsochacki.adventofcode;

import java.io.File;
import java.util.*;

public class Day15 {
    public static class Point {
        int x;
        int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            Point c = (Point) o;
            return this.x == c.x && this.y == c.y;
        }

        @Override
        public int hashCode() {
            return 10000 * x + y;
        }
    }

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

    public static ArrayList<ArrayList<Integer>> readInput(String filename) {
        ArrayList<ArrayList<Integer>> mapOfRisks = new ArrayList<>();
        Scanner sc;
        try {
            sc = new Scanner(new File(filename));
            while (sc.hasNextLine()) {
                mapOfRisks.add(splitRow(sc.nextLine()));
            }
            sc.close();
        } catch (Exception e) {
            System.out.println("Error handling input " + e);
        }
        return mapOfRisks;
    }

    public static ArrayList<Integer> splitRow(String row) {
        ArrayList<Integer> rowOfRisks = new ArrayList<>();
        for (String s : new ArrayList<String>(Arrays.asList(row.split("")))) {
            rowOfRisks.add(Integer.parseInt(s));
        }
        return rowOfRisks;
    }

    public static int getRisk(ArrayList<ArrayList<Integer>> mapOfRisks, int x, int y) {
        int width = mapOfRisks.get(0).size();
        int height = mapOfRisks.size();
        int tmpRisk = mapOfRisks.get(y % height).get(x % width) + x / width + y / height;
        if (tmpRisk < 10) {
            return tmpRisk;
        } else {
            return (tmpRisk % 10) + 1;
        }
    }

    public static ArrayList<Point> getNeighbours(int x, int y, int width, int height) {
        ArrayList<Point> neighbours = new ArrayList<>();
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

    public static int calculateRisk(boolean isPart2) {
        ArrayList<ArrayList<Integer>> mapOfRisks = readInput("data.txt");
        int height = mapOfRisks.size();
        int width = mapOfRisks.get(0).size();
        if (isPart2) {
            height *= 5;
            width *= 5;
        }
        int x = 0;
        int y = 0;
        int risk = 0;
        HashSet<Point> visited = new HashSet<>();
        PriorityQueue<Position> positionQueue = new PriorityQueue<>();
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

    public static void main(String[] args) {
        System.out.println(calculateRisk(false));
        System.out.println(calculateRisk(true));
    }
}