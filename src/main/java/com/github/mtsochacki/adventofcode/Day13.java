package com.github.mtsochacki.adventofcode;

import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.*;

@Slf4j
public class Day13 implements Day {
    public enum Axis {
        X, Y
    }

    public static class Fold {
        int line;
        Axis axis;
    }

    @EqualsAndHashCode
    public static class Point {
        int x;
        int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private Set<Point> readCoordinates(List<String> input) {
        String filename = "";
        Set<Point> coordinates = new HashSet<>();
        try (Scanner sc = new Scanner(new File(filename))) {
            sc.useDelimiter(",|\n");
            while (sc.hasNextInt()) {
                Point newPoint = new Point(sc.nextInt(), sc.nextInt());
                coordinates.add(newPoint);
            }
        } catch (Exception e) {
            log.error("Something went horribly wrong: {}", e.getMessage());
        }
        return coordinates;
    }

    private List<Fold> readFolds(List<String> input) {
        String filename = "";
        List<Fold> folds = new ArrayList<>();
        try (Scanner sc = new Scanner(new File(filename))) {
            while (!sc.nextLine().isEmpty()) {
                // todo check if this is still needed
            }
            sc.useDelimiter("fold along |=|\\n");
            while (sc.hasNextLine()) {
                Fold fold = new Fold();
                fold.axis = sc.next().equals("x") ? Axis.X : Axis.Y;
                fold.line = sc.nextInt();
                if (sc.hasNextLine()) {
                    sc.nextLine();
                }
                folds.add(fold);
            }
        } catch (Exception e) {
            log.error("Something went horribly wrong: {}", e.getMessage());
        }
        return folds;
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
        return "Prints message";
    }
}