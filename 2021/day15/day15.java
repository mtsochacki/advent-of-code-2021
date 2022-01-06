import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.lang.Integer;

public class day15 {
    public static class Point {
        int x;
        int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
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
        ArrayList<ArrayList<Integer>> mapOfRisk = new ArrayList<>();
        Scanner sc;
        try {
            sc = new Scanner(new File(filename));
            while (sc.hasNextLine()) {
                ArrayList<Integer> riskRow = new ArrayList<>();
                sc.useDelimiter("");
                while (sc.hasNextInt()) {
                    riskRow.add(sc.nextInt());
                }
                mapOfRisk.add(riskRow);
                if (sc.hasNextLine()) {
                    sc.nextLine();
                }
            }
            sc.close();
        } catch (Exception e) {
            System.out.println("Error handling input " + e);
        }
        return mapOfRisk;
    }

    public static ArrayList<ArrayList<Integer>> extendMap(
            ArrayList<ArrayList<Integer>> mapOfRisks, int sizeMultiplier) {
        for (ArrayList<Integer> existingLine : mapOfRisks) {
            int startingLineSize = existingLine.size();
            for (int i = 1; i < sizeMultiplier; i++) {
                for (int y = 0; y < startingLineSize; y++) {
                    int tmpRisk = existingLine.get(y) + i;
                    if (tmpRisk > 9)
                        tmpRisk = tmpRisk % 10 + 1;
                    existingLine.add(tmpRisk);
                }
            }
        }
        int startingMapSize = mapOfRisks.size();
        for (int i = 1; i < sizeMultiplier; i++) {
            for (int y = 0; y < startingMapSize; y++) {
                ArrayList<Integer> newLine = new ArrayList<>();
                for (Integer risk : mapOfRisks.get(y)) {
                    int tmpRisk = risk + i;
                    if (tmpRisk > 9)
                        tmpRisk = tmpRisk % 10 + 1;
                    newLine.add(tmpRisk);
                }
                mapOfRisks.add(newLine);
            }
        }
        return mapOfRisks;
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
        ArrayList<ArrayList<Integer>> mapOfRisk = readInput("data.txt");
        if (isPart2) {
            mapOfRisk = extendMap(mapOfRisk, 5);
        }
        ArrayList<ArrayList<Boolean>> isVisited = new ArrayList<>();
        for (ArrayList<Integer> element : mapOfRisk) {
            ArrayList<Boolean> line = new ArrayList<>(Collections.nCopies(mapOfRisk.size(), false));
            isVisited.add(line);
        }
        PriorityQueue<Position> positionQueue = new PriorityQueue<>();
        int x = 0;
        int y = 0;
        int risk = 0;
        while (x != mapOfRisk.get(0).size() - 1 || y != mapOfRisk.size() - 1) {
            for (Point point : getNeighbours(x, y, mapOfRisk.get(0).size(), mapOfRisk.size())) {
                Position position = new Position(point.x, point.y, risk + mapOfRisk.get(point.y).get(point.x));
                if (!isVisited.get(point.y).get(point.x)) {
                    positionQueue.add(position);
                }
                isVisited.get(point.y).set(point.x, true);
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