package com.github.mtsochacki.adventofcode;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Day09 {
    public static class Point {
        int x;
        int y;

        Point(int hor, int vert) {
            x = hor;
            y = vert;
        }
    }

    public static ArrayList<ArrayList<Integer>> readInput(String filename) {
        ArrayList<ArrayList<Integer>> map = new ArrayList<>();
        Scanner sc;
        try {
            sc = new Scanner(new File(filename));
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
            sc.close();
        } catch (Exception e) {
            System.out.println("Something went wrong" + e);
        }
        return map;
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

    public static boolean isLowPoint(ArrayList<ArrayList<Integer>> map, int x, int y) {
        for (Point point : getNeighbours(x, y, map.get(y).size(), map.size())) {
            if (map.get(x).get(y) >= map.get(point.x).get(point.y)) {
                return false;
            }
        }
        return true;
    }

    public static int part1() {
        ArrayList<ArrayList<Integer>> map = readInput("data.txt");
        int riskLevel = 0;
        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.get(i).size(); j++) {
                if (isLowPoint(map, i, j))
                    riskLevel += (map.get(i).get(j) + 1);
            }
        }
        return riskLevel;
    }

    public static int calculateBasin(ArrayList<ArrayList<Integer>> map, int y, int x) {
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

    public static int part2() {
        ArrayList<ArrayList<Integer>> map = readInput("data.txt");
        ArrayList<Integer> result = new ArrayList<>();
        for (int y = 0; y < map.size(); y++) {
            for (int x = 0; x < map.get(1).size(); x++) {
                if (map.get(y).get(x) != -1 && map.get(y).get(x) != 9)
                    result.add(calculateBasin(map, y, x));
            }
        }
        Collections.sort(result);
        return result.get(result.size() - 1) * result.get(result.size() - 2) * result.get(result.size() - 3);
    }

    public static void main(String[] args) {
        System.out.println(part1());
        System.out.println(part2());
    }
}