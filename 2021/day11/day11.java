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
        ArrayList<ArrayList<Integer>> input = new ArrayList<>();
        ArrayList<Integer> emptyLine = new ArrayList<>();
        Scanner sc = null;
        try {
            sc = new Scanner(new File(filename));
            sc.useDelimiter("");
            input.add(emptyLine);
            while (sc.hasNextLine()) {
                ArrayList<Integer> line = new ArrayList<>();
                line.add(9);
                while (sc.hasNextInt()) {
                    line.add(sc.nextInt());
                }
                line.add(9);
                input.add(line);
                if (sc.hasNextLine())
                    sc.nextLine();
            }

            input.add(emptyLine);
            for (int i = 0; i < input.get(1).size(); i++) {
                input.get(0).add(9);
                input.get(input.size() - 1).add(9);
            }

        } catch (Exception e) {
            System.out.println("Something went wrong " + e);
        } finally {
            sc.close();
        }
        return input;
    }

    public static void initialIncrease(ArrayList<ArrayList<Integer>> input) {
        for (int j = 1; j < input.size() - 1; j++) {
            for (int k = 1; k < input.get(j).size() - 1; k++) {
                input.get(j).set(k, input.get(j).get(k) + 1);
            }
        }
    }

    public static void increaseOctopus(ArrayList<ArrayList<Integer>> input,
            int x, int y) {
        if (input.get(y).get(x) != 0)
            input.get(y).set(x, input.get(y).get(x) + 1);
    }

    public static Boolean processFlashes(ArrayList<ArrayList<Integer>> input) {
        boolean explosionStatus = false;
        for (int j = 1; j < input.size() - 1; j++) {
            for (int k = 1; k < input.get(j).size() - 1; k++) {
                if (input.get(j).get(k) > 9) {
                    input.get(j).set(k, 0);
                    increaseOctopus(input, k - 1, j);
                    increaseOctopus(input, k + 1, j);
                    increaseOctopus(input, k + 1, j - 1);
                    increaseOctopus(input, k + 1, j + 1);
                    increaseOctopus(input, k - 1, j - 1);
                    increaseOctopus(input, k - 1, j + 1);
                    increaseOctopus(input, k, j - 1);
                    increaseOctopus(input, k, j + 1);
                }
                if (input.get(j).get(k + 1) > 9
                        || input.get(j + 1).get(k + 1) > 9
                        || input.get(j + 1).get(k) > 9
                        || input.get(j + 1).get(k - 1) > 9
                        || input.get(j - 1).get(k + 1) > 9
                        || input.get(j - 1).get(k) > 9
                        || input.get(j - 1).get(k - 1) > 9
                        || input.get(j).get(k - 1) > 9) {
                    explosionStatus = true;
                }
            }
        }
        return explosionStatus;
    }

    public static boolean isFlashReady(ArrayList<ArrayList<Integer>> input) {
        for (int j = 1; j < input.size() - 1; j++) {
            for (int k = 1; k < input.get(j).size() - 1; k++) {
                if (input.get(j).get(k) > 9) {
                    return true;
                }
            }
        }
        return false;
    }

    public static int countFlashes(ArrayList<ArrayList<Integer>> input) {
        int counter = 0;
        for (int j = 1; j < input.size() - 1; j++) {
            for (int k = 1; k < input.get(j).size() - 1; k++) {
                if (input.get(j).get(k) == 0)
                    counter++;
            }
        }
        return counter;
    }

    public static boolean areAllFlashing(ArrayList<ArrayList<Integer>> input) {
        for (int j = 1; j < input.size() - 1; j++) {
            for (int k = 1; k < input.get(j).size() - 1; k++) {
                if (input.get(j).get(k) != 0)
                    return false;
            }
        }
        return true;
    }

    public static void inspectOctopuses(int days) {
        int flashCounter = 0;
        boolean isAnyReadyToFlash = true;
        ArrayList<ArrayList<Integer>> input = readInput("data.txt");
        for (int i = 0; i < days; i++) {
            initialIncrease(input);
            isAnyReadyToFlash = true;
            while (isAnyReadyToFlash) {
                processFlashes(input);
                isAnyReadyToFlash = isFlashReady(input);
            }
            flashCounter += countFlashes(input);
            if (areAllFlashing(input)) {
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