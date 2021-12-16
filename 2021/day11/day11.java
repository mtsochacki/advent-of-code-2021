import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class day11 {
    public static ArrayList<ArrayList<Integer>> readInput(String filename) {
        ArrayList<ArrayList<Integer>> input = new ArrayList<>();
        ArrayList<Integer> lineOfNines = new ArrayList<>();
        for (int index = 0; index < 12; index++) {
            // for (int index = 0; index < 12; index++) {
            lineOfNines.add(9);
        }
        Scanner sc = null;
        try {
            sc = new Scanner(new File(filename));
            sc.useDelimiter("");
            input.add(lineOfNines);
            while (sc.hasNextLine()) {
                ArrayList<Integer> line = new ArrayList<>();
                line.add(-1);
                while (sc.hasNextInt()) {
                    line.add(sc.nextInt());
                }
                line.add(-1);
                input.add(line);
                if (sc.hasNextLine())
                    sc.nextLine();
            }
            input.add(lineOfNines);
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

    public static void printAll(ArrayList<ArrayList<Integer>> input) {
        for (int i = 1; i < input.size() - 1; i++) {
            for (int j = 1; j < input.get(0).size() - 1; j++) {
                System.out.print(input.get(i).get(j));
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void increaseOctopus(ArrayList<ArrayList<Integer>> input, int x, int y) {
        if (input.get(y).get(x) != 0)
            input.get(y).set(x, input.get(y).get(x) + 1);
    }

    public static Boolean processExplosions(ArrayList<ArrayList<Integer>> input) {
        boolean explosionStatus = false;
        for (int j = 1; j < input.size() - 1; j++) {
            for (int k = 1; k < input.get(j).size() - 1; k++) {
                if (input.get(j).get(k) > 9) {
                    input.get(j).set(k, 0);
                    increaseOctopus(input, k - 1, j); // left
                    increaseOctopus(input, k + 1, j); // right
                    increaseOctopus(input, k + 1, j - 1);
                    increaseOctopus(input, k + 1, j + 1);
                    increaseOctopus(input, k - 1, j - 1);
                    increaseOctopus(input, k - 1, j + 1);
                    increaseOctopus(input, k, j - 1);
                    increaseOctopus(input, k, j + 1);
                }
                if (input.get(j).get(k + 1) > 9 || input.get(j + 1).get(k + 1) > 9 || input.get(j + 1).get(k) > 9
                        || input.get(j + 1).get(k - 1) > 9 || input.get(j - 1).get(k + 1) > 9
                        || input.get(j - 1).get(k) > 9
                        || input.get(j - 1).get(k - 1) > 9 || input.get(j).get(k - 1) > 9) {
                    explosionStatus = true;
                }
            }
        }
        return explosionStatus;
    }

    public static boolean isExplosionReady(ArrayList<ArrayList<Integer>> input) {

        for (int j = 1; j < input.size() - 1; j++) {
            for (int k = 1; k < input.get(j).size() - 1; k++) {
                if (input.get(j).get(k) > 9) {

                    return true;
                }
            }
        }
        return false;
    }

    public static int countExplosions(ArrayList<ArrayList<Integer>> input) {
        int counter = 0;
        for (int j = 1; j < input.size() - 1; j++) {
            for (int k = 1; k < input.get(j).size() - 1; k++) {
                if (input.get(j).get(k) == 0)
                    counter++;
            }
        }
        return counter;
    }

    public static boolean allFlash(ArrayList<ArrayList<Integer>> input) {
        for (int j = 1; j < input.size() - 1; j++) {
            for (int k = 1; k < input.get(j).size() - 1; k++) {
                if (input.get(j).get(k) != 0)
                    return false;
            }
        }
        return true;
    }

    public static void part1() {
        int days = 500;
        int counterExplosions = 0;
        boolean explosionReady = true;
        ArrayList<ArrayList<Integer>> input = readInput("data.txt");
        for (int i = 0; i < days && !allFlash(input); i++) {
            initialIncrease(input);
            explosionReady = true;
            while (explosionReady) {
                processExplosions(input);
                explosionReady = isExplosionReady(input);
            }
            counterExplosions += countExplosions(input);
            if (allFlash(input)) {
                System.out.println(i);
                break;
            }
        }
        printAll(input);
        System.out.println(counterExplosions);
    }

    public static void main(String[] args) {
        part1();
    }
}