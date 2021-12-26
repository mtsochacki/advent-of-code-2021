import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class day11 {
    /*
     * Creates a grid surrounded by a border of nines, e.g.:
     * 999999
     * 1234 912349
     * 5678 --> 956789
     * 9999 999999
     * 999999
     */
    public static ArrayList<ArrayList<Integer>> readInput(String filename) {
        ArrayList<ArrayList<Integer>> octoGrid = new ArrayList<>();
        ArrayList<Integer> emptyLine = new ArrayList<>();
        Scanner sc = null;
        try {
            sc = new Scanner(new File(filename));
            sc.useDelimiter("");
            octoGrid.add(emptyLine);
            while (sc.hasNextInt()) {
                ArrayList<Integer> octoRow = new ArrayList<>();
                octoRow.add(9);
                while (sc.hasNextInt()) {
                    octoRow.add(sc.nextInt());
                }
                octoRow.add(9);
                octoGrid.add(octoRow);
                if (sc.hasNextLine()) {
                    sc.nextLine();
                }
            }

            octoGrid.add(emptyLine);
            for (int i = 0; i < octoGrid.get(1).size(); i++) {
                octoGrid.get(0).add(9);
                octoGrid.get(octoGrid.size() - 1).add(9);
            }

        } catch (Exception e) {
            System.out.println("Something went wrong " + e);
        } finally {
            sc.close();
        }
        return octoGrid;
    }

    public static void increaseAll(ArrayList<ArrayList<Integer>> octoGrid) {
        for (int j = 1; j < octoGrid.size() - 1; j++) {
            for (int k = 1; k < octoGrid.get(j).size() - 1; k++) {
                octoGrid.get(j).set(k, octoGrid.get(j).get(k) + 1);
            }
        }
    }

    public static void increaseOne(ArrayList<ArrayList<Integer>> octoGrid,
            int x, int y) {
        if (octoGrid.get(y).get(x) != 0) {
            octoGrid.get(y).set(x, octoGrid.get(y).get(x) + 1);
        }
    }

    public static void processFlashes(ArrayList<ArrayList<Integer>> octoGrid) {
        for (int j = 1; j < octoGrid.size() - 1; j++) {
            for (int k = 1; k < octoGrid.get(j).size() - 1; k++) {
                if (octoGrid.get(j).get(k) > 9) {
                    octoGrid.get(j).set(k, 0);
                    increaseOne(octoGrid, k - 1, j);
                    increaseOne(octoGrid, k + 1, j);
                    increaseOne(octoGrid, k + 1, j - 1);
                    increaseOne(octoGrid, k + 1, j + 1);
                    increaseOne(octoGrid, k - 1, j - 1);
                    increaseOne(octoGrid, k - 1, j + 1);
                    increaseOne(octoGrid, k, j - 1);
                    increaseOne(octoGrid, k, j + 1);
                }
            }
        }
    }

    public static boolean isFlashReady(ArrayList<ArrayList<Integer>> octoGrid) {
        for (int j = 1; j < octoGrid.size() - 1; j++) {
            for (int k = 1; k < octoGrid.get(j).size() - 1; k++) {
                if (octoGrid.get(j).get(k) > 9) {
                    return true;
                }
            }
        }
        return false;
    }

    public static int countFlashes(ArrayList<ArrayList<Integer>> octoGrid) {
        int counter = 0;
        for (int j = 1; j < octoGrid.size() - 1; j++) {
            for (int k = 1; k < octoGrid.get(j).size() - 1; k++) {
                if (octoGrid.get(j).get(k) == 0)
                    counter++;
            }
        }
        return counter;
    }

    public static boolean areAllFlashing(ArrayList<ArrayList<Integer>> octoGrid) {
        for (int j = 1; j < octoGrid.size() - 1; j++) {
            for (int k = 1; k < octoGrid.get(j).size() - 1; k++) {
                if (octoGrid.get(j).get(k) != 0)
                    return false;
            }
        }
        return true;
    }

    public static void inspectOctopuses(int days) {
        int flashCounter = 0;
        ArrayList<ArrayList<Integer>> octoGrid = readInput("data.txt");
        for (int i = 0; i < days; i++) {
            increaseAll(octoGrid);
            boolean isAnyReadyToFlash = isFlashReady(octoGrid);
            while (isAnyReadyToFlash) {
                processFlashes(octoGrid);
                isAnyReadyToFlash = isFlashReady(octoGrid);
            }
            flashCounter += countFlashes(octoGrid);
            if (areAllFlashing(octoGrid)) {
                System.out.println(i + 1);
                break;
            }
        }
        System.out.println(flashCounter);
    }

    public static void main(String[] args) {
        inspectOctopuses(100);
        inspectOctopuses(500);
    }
}