import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.lang.Integer;

public class day15 {
    public static class Position implements Comparable<Position> {
        int x;
        int y;
        int risk;

        Position(int vertical, int horizontal, int currentRisk) {
            x = vertical;
            y = horizontal;
            risk = currentRisk;
        }

        @Override
        public int compareTo(Position other) {
            return Integer.compare(this.risk, other.risk);
        }
    }

    public static ArrayList<ArrayList<Integer>> readInput(String filename) {
        ArrayList<ArrayList<Integer>> input = new ArrayList<>();
        Scanner sc = null;
        try {
            sc = new Scanner(new File(filename));
            while (sc.hasNextLine()) {
                ArrayList<Integer> line = new ArrayList<>();
                sc.useDelimiter("");
                while (sc.hasNextInt()) {
                    line.add(sc.nextInt());
                }
                input.add(line);
                if (sc.hasNextLine())
                    sc.nextLine();
            }
        } catch (Exception e) {
            System.out.println("Error handling input " + e);
        } finally {
            sc.close();
        }
        return input;
    }

    public static ArrayList<ArrayList<Integer>> modifyInput
                (ArrayList<ArrayList<Integer>> mapOfRisks, int sizeMultiplier) {
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

    /*
     * Implementation of Dijkstra's algorithm for finding shortest path between
     * two nodes. It goes like this:
     * Do the following as long as we did not reach destination:
     * 1. Visit all neighbour nodes (that are not in state "already visited")
     * of the current node. Check their distance (risk) from the starting
     * position and add them to priority queue. Set their position to
     * "already visited".
     * 2. From the priority queue take node that is the closest to starting
     * position and move there.
     * 3. Update current position and distance (risk).
     */
    public static int calculateRisk(boolean isPart2) {
        ArrayList<ArrayList<Integer>> mapOfRisk = readInput("data.txt");
        if (isPart2) {
            mapOfRisk = modifyInput(mapOfRisk, 5);
        }
        ArrayList<ArrayList<Boolean>> isVisited = new ArrayList<>();
        for (ArrayList<Integer> element : mapOfRisk) {
            ArrayList<Boolean> line = new ArrayList<>
                                (Collections.nCopies(mapOfRisk.size(), false));
            isVisited.add(line);
        }
        PriorityQueue<Position> queOfPositions = new PriorityQueue<>();
        int x = 0;
        int y = 0;
        int risk = 0;
        while (x != mapOfRisk.size() - 1 || y != mapOfRisk.size() - 1) {
            // visit right neighbour
            if (x < mapOfRisk.size() - 1 && !isVisited.get(y).get(x + 1)) {
                Position position = new Position(x + 1, y,
                        risk + mapOfRisk.get(y).get(x + 1));
                queOfPositions.add(position);
                isVisited.get(y).set(x + 1, true);
            }
            // visit left neighbour
            if (x > 0 && !isVisited.get(y).get(x - 1)) {
                Position position = new Position(x - 1, y,
                        risk + mapOfRisk.get(y).get(x - 1));
                queOfPositions.add(position);
                isVisited.get(y).set(x - 1, true);
            }
            // visit top neighbour
            if (y > 0 && !isVisited.get(y - 1).get(x)) {
                Position position = new Position(x, y - 1,
                        risk + mapOfRisk.get(y - 1).get(x));
                queOfPositions.add(position);
                isVisited.get(y - 1).set(x, true);
            }
            // visit bottom neigbour
            if (y < mapOfRisk.size() - 1 && !isVisited.get(y + 1).get(x)) {
                Position position = new Position(x, y + 1,
                        risk + mapOfRisk.get(y + 1).get(x));
                queOfPositions.add(position);
                isVisited.get(y + 1).set(x, true);
            }
            Position currentPosition = queOfPositions.poll();
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