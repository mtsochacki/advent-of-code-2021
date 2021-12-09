import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.io.File;

public class day09 {
    // create array of arrays surrounded by border of 99999999
    public static ArrayList<ArrayList<Integer>> readInput(String filename) {
        ArrayList<ArrayList<Integer>> input = new ArrayList<>();
        ArrayList<Integer> lineOfNines = new ArrayList<>();
        for (int index = 0; index < 102; index++) {
            lineOfNines.add(9);
        }
        Scanner sc = null;

        try {
            sc = new Scanner(new File(filename));
            sc.useDelimiter("");
            input.add(lineOfNines);
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
            input.add(lineOfNines);

        } catch (Exception e) {
            System.out.println("Something went wrong" + e);
        } finally {
            sc.close();
        }
        return input;
    }

    public static ArrayList<ArrayList<Boolean>> generateState() {
        ArrayList<ArrayList<Boolean>> state = new ArrayList<>();
        for (int i = 0; i < 102; i++) {
            ArrayList<Boolean> line = new ArrayList<>();
            for (int j = 0; j < 102; j++) {
                line.add(false);
            }
            state.add(line);
        }
        return state;
    }

    public static void part1() {
        ArrayList<ArrayList<Integer>> input = readInput("data.txt");
        int riskLevel = 0;
        for (int i = 1; i < input.size() - 1; i++) {
            for (int j = 1; j < input.get(i).size() - 1; j++) {
                if (input.get(i).get(j) < input.get(i).get(j - 1) &&
                        input.get(i).get(j) < input.get(i).get(j + 1) &&
                        input.get(i).get(j) < input.get(i - 1).get(j) &&
                        input.get(i).get(j) < input.get(i + 1).get(j)) {
                    riskLevel += (input.get(i).get(j) + 1);
                }
            }
        }
        System.out.println(riskLevel);
    }

    public static void floodFill(ArrayList<ArrayList<Boolean>> state, ArrayList<ArrayList<Integer>> input, int x,
            int y) {
        if (input.get(x).get(y) == 9)
            return;
        state.get(x).set(y, true);
        floodFill(state, input, x + 1, y);
        floodFill(state, input, x - 1, y);
        floodFill(state, input, x, y + 1);
        floodFill(state, input, x, y - 1);
        return;
    }

    public static void part2() {
        ArrayList<ArrayList<Integer>> input = readInput("data.txt");
        ArrayList<ArrayList<Boolean>> state = generateState();
        for (int i = 1; i < input.size() - 1; i++) {
            for (int j = 1; j < input.get(i).size() - 1; j++) {
                if (state.get(i).get(j) == false)
                    floodFill(state, input, i, j);
            }
        }
    }

    public static void main(String[] args) {
        part1();
        part2();
    }

}
