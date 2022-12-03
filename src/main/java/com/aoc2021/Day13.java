package com.aoc2021;

import java.io.File;
import java.util.*;

public class Day13 implements Day {
    private enum Axis {
        X, Y
    }

    private static class Fold {
        int line;
        Axis axis;
    }

    private static class Point {
        int x;
        int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            Point newPoint = (Point) o;
            return x == newPoint.x && y == newPoint.y;
        }

        @Override
        public int hashCode() {
            int result = 17;
            result = 31 * result + x;
            result = 31 * result + y;
            return result;
        }
    }

    private Set<Point> readCoordinates(String filename) {
        Set<Point> coordinates = new HashSet<>();
        try (Scanner sc = new Scanner(new File(filename))) {
            sc.useDelimiter(",|\n");
            while (sc.hasNextInt()) {
                Point newPoint = new Point(sc.nextInt(), sc.nextInt());
                coordinates.add(newPoint);
            }
        } catch (Exception e) {
            System.out.println("Something went wrong " + e);
        }
        return coordinates;
    }

    private List<Fold> readFolds(String filename) {
        ArrayList<Fold> folds = new ArrayList<>();
        try (Scanner sc = new Scanner(new File(filename))) {
            while (!sc.nextLine().isEmpty()) {
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
            System.out.println("Something went horribly wrong " + e);
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

    public String part1(String filename) {
        Set<Point> points = readCoordinates(filename);
        List<Fold> folds = readFolds(filename);
        points = foldOnce(points, folds.get(0));
        return String.valueOf(points.size());
    }

    public String part2(String filename) {
        Set<Point> points = readCoordinates(filename);
        List<Fold> folds = readFolds(filename);
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
        return "Unique quality of part2 makes it unable to return anything that makes sense";
    }
}