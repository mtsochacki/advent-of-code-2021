package com.github.mtsochacki.adventofcode;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class Day13 implements Day {
    @Override
    public String part1(List<String> input) {
        Set<Point> points = readCoordinates(input);
        List<Fold> folds = readFolds(input);
        points = foldOnce(points, folds.get(0));
        return String.valueOf(points.size());
    }

    @Override
    public String part2(List<String> input) {
        Set<Point> points = readCoordinates(input);
        List<Fold> folds = readFolds(input);
        for (Fold fold : folds) {
            points = foldOnce(points, fold);
        }
        System.out.println("\033[2J"); // clears screen
        for (Point point : points) {
            int row = point.y + 1;
            int column = point.x + 1;
            System.out.print(String.format("%c[%d;%dH#", 0x1B, row, column));
        }
        System.out.print(String.format("%c[%d;%dH", 0x1B, 8, 0));
        return "The answer to this puzzle can only be printed to the terminal";
    }

    private Set<Point> readCoordinates(List<String> input) {
        return input.stream()
                .filter(line -> line.contains(","))
                .map(line -> {
                    String[] tokens = line.split(",");
                    return new Point(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]));
                })
                .collect(Collectors.toSet());
    }

    private List<Fold> readFolds(List<String> input) {
        return input.stream()
                .skip(Math.max(0, input.size() - 2))
                .map(line -> {
                    String[] tokens = line.replace("fold along ", "").split("=");
                    Fold fold = new Fold();
                    fold.axis = tokens[0].equals("x") ? Axis.X : Axis.Y;
                    fold.line = Integer.parseInt(tokens[1]);
                    return fold;
                })
                .toList();
    }

    private Set<Point> foldOnce(Set<Point> points, Fold fold) {
        Set<Point> output = new HashSet<>();
        for (Point point : points) {
            if (fold.axis.equals(Axis.X)) {
                if (point.x > fold.line) {
                    int distance = point.x - fold.line;
                    point.x = fold.line - distance;
                    output.add(point);
                } else {
                    output.add(point);
                }
            } else {
                if (point.y > fold.line) {
                    int distance = point.y - fold.line;
                    point.y = fold.line - distance;
                    output.add(point);
                } else {
                    output.add(point);
                }
            }
        }
        return output;
    }

    public enum Axis {
        X, Y
    }

    public static class Fold {
        int line;
        Axis axis;
    }

    @EqualsAndHashCode
    @AllArgsConstructor
    public static class Point {
        int x;
        int y;
    }
}