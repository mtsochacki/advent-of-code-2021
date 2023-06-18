package com.github.mtsochacki.adventofcode;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

@Slf4j
public class Day09 implements Day {
    private class Point {
        int x;
        int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private List<List<Integer>> readInput(String filename) {
        List<List<Integer>> map = new ArrayList<>();
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
            log.error("Something went horribly wrong: {}", e.getMessage());
        }
        return map;
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

    public boolean isLowPoint(List<List<Integer>> map, int x, int y) {
        for (Point point : getNeighbours(x, y, map.get(y).size(), map.size())) {
            if (map.get(x).get(y) >= map.get(point.x).get(point.y)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String part1(List<String> input) {
        List<List<Integer>> map = readInput("data.txt");
        int riskLevel = 0;
        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.get(i).size(); j++) {
                if (isLowPoint(map, i, j))
                    riskLevel += (map.get(i).get(j) + 1);
            }
        }
        return String.valueOf(riskLevel);
    }

    private int calculateBasin(List<List<Integer>> map, int y, int x) {
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

    @Override
    public String part2(List<String> input) {
        List<List<Integer>> map = readInput("data.txt");
        List<Integer> result = new ArrayList<>();
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