package com.github.mtsochacki.advent_of_code;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Day13 {
    public enum Axis {
        X, Y
    }

    public static class Fold {
        int line;
        Axis axis;
    }

    public static class Point {
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

    public static Set<Point> readCoordinates(String filename) {
        Set<Point> coordinates = new HashSet<>();
        Scanner sc;
        try {
            sc = new Scanner(new File(filename));
            sc.useDelimiter(",|\n");
            while (sc.hasNextInt()) {
                Point newPoint = new Point(sc.nextInt(), sc.nextInt());
                coordinates.add(newPoint);
            }
            sc.close();
        } catch (Exception e) {
            System.out.println("Something went wrong " + e);
        }
        return coordinates;
    }

    public static ArrayList<Fold> readFolds(String filename) {
        ArrayList<Fold> folds = new ArrayList<>();
        Scanner sc;
        try {
            sc = new Scanner(new File(filename));
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
            sc.close();
        } catch (Exception e) {
            System.out.println("Something went horribly wrong " + e);
        }
        return folds;
    }

    public static Set<Point> foldOnce(Set<Point> points, Fold fold) {
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

    public static int part1() {
        Set<Point> points = readCoordinates("data.txt");
        ArrayList<Fold> folds = readFolds("data.txt");
        points = foldOnce(points, folds.get(0));
        return points.size();
    }

    public static void part2() {
        Set<Point> points = readCoordinates("data.txt");
        ArrayList<Fold> folds = readFolds("data.txt");
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
    }

    public static void main(String[] args) {
        part2();
        System.out.println(part1());
    }
}