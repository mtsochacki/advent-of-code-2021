import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class day13 {
    public static ArrayList<ArrayList<Integer>> readCoordinates(String filename) {
        ArrayList<ArrayList<Integer>> coordinates = new ArrayList<>();
        Scanner sc = null;
        try {
            sc = new Scanner(new File(filename));
            sc.useDelimiter(",|\n");
            while (sc.hasNext()) {
                ArrayList<Integer> line = new ArrayList<>();
                for (int j = 0; j < 2; j++) {
                    line.add(sc.nextInt());
                }
                coordinates.add(line);
            }
        } catch (Exception e) {
            System.out.println("Something went wrong " + e);
        } finally {
            sc.close();
        }
        return coordinates;
    }

    public static ArrayList<ArrayList<Integer>> foldPaper(ArrayList<ArrayList<Integer>> input, int x, boolean isX) {
        ArrayList<ArrayList<Integer>> output = new ArrayList<>();
        int z = isX ? 0 : 1;
        for (ArrayList<Integer> line : input) {
            if (line.get(z) > x) {
                int distance = line.get(z) - x;
                line.set(z, x - distance);
                output.add(line);
            } else if (line.get(z) < x) {
                output.add(line);
            }
        }
        Set<ArrayList<Integer>> set = new HashSet<>(output);
        output.clear();
        output.addAll(set);
        return output;
    }

    public static void part1() {
        ArrayList<ArrayList<Integer>> input = readCoordinates("data.txt");
        input = foldPaper(input, 655, true);
        input = foldPaper(input, 447, false);
        input = foldPaper(input, 327, true);
        input = foldPaper(input, 223, false);
        input = foldPaper(input, 163, true);
        input = foldPaper(input, 111, false);
        input = foldPaper(input, 81, true);
        input = foldPaper(input, 55, false);
        input = foldPaper(input, 40, true);
        input = foldPaper(input, 27, false);
        input = foldPaper(input, 13, false);
        input = foldPaper(input, 6, false);
        System.out.println("\033[2J"); // clears screen
        for (ArrayList<Integer> line : input) {
            int row = line.get(1) + 1;
            int column = line.get(0) + 1;
            System.out.print(String.format("%c[%d;%dH#", 0x1B, row, column));
        }
        System.out.print(String.format("%c[%d;H", 0x1B, 7));
        System.out.println(input.size());
    }

    public static void main(String[] args) {
        part1();
    }
}