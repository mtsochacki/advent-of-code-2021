package com.aoc2021;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Day09 implements Day {
    private static class Point {
        int x;
        int y;

        Point(int hor, int vert) {
            x = hor;
            y = vert;
        }
    }

    private List<ArrayList<Integer>> readInput(String filename) {
        ArrayList<ArrayList<Integer>> map = new ArrayList<>();
        try (Scanner sc = new Scanner(new File(filename))) {
            sc.useDelimiter("");
            while (sc.hasNextLine()) {
                ArrayList<Integer> line = new ArrayList<>();
                while (sc.hasNextInt()) {
                    line.add(sc.nextInt());
                }
                map.add(line);
                if (sc.hasNextLine())
                    sc.nextLine();
            }
        } catch (Exception e) {
            System.out.println("Something went wrong" + e);
        }
        return map;
    }

    private List<Point> getNeighbours(int x, int y, int width, int height) {
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

    private boolean isLowPoint(List<ArrayList<Integer>> map, int x, int y) {
        for (Point point : getNeighbours(x, y, map.get(y).size(), map.size())) {
            if (map.get(y).get(x) >= map.get(point.y).get(point.x)) {
                return false;
            }
        }
        return true;
    }

    public String part1(String filename) {
        List<ArrayList<Integer>> map = readInput(filename);
        int riskLevel = 0;
        for (int y = 0; y < map.size(); y++) {
            for (int x = 0; x < map.get(y).size(); x++) {
                if (isLowPoint(map, x, y))
                    riskLevel += (map.get(y).get(x) + 1);
            }
        }
        return String.valueOf(riskLevel);
    }

    private int calculateBasin(List<ArrayList<Integer>> map, int y, int x) {
        int size = 0;
        if (map.get(y).get(x) == 9 || map.get(y).get(x) == -1) {
            return 0;
        } else {
            size++;
            map.get(y).set(x, -1);
        }
        for (Point point : getNeighbours(x, y, map.get(y).size(), map.size())) {
            size += calculateBasin(map, point.y, point.x);
        }
        return size;
    }

    public String part2(String filename) {
        List<ArrayList<Integer>> map = readInput(filename);
        ArrayList<Integer> result = new ArrayList<>();
        for (int y = 0; y < map.size(); y++) {
            for (int x = 0; x < map.get(1).size(); x++) {
                if (map.get(y).get(x) != -1 && map.get(y).get(x) != 9)
                    result.add(calculateBasin(map, y, x));
            }
        }
        Collections.sort(result);
        return String.valueOf(result.get(result.size() - 1) * result.get(result.size() - 2) * result.get(result.size() - 3));
    }
}