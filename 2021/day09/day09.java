import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import java.io.File;

public class day09 {
    /*  Creates array of arrays surrounded by a border of nines
                             9999999
                             9123459
                             9234569
                             9123459
                             9999999                            */
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
            // Add enough nines to the first and last empty lines
            for (int i = 0; i < input.get(1).size(); i++) {
                input.get(0).add(9);
                input.get(input.size() - 1).add(9);
            }
        } catch (Exception e) {
            System.out.println("Something went wrong" + e);
        } finally {
            sc.close();
        }
        return input;
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

    // 9 is a border of basin, -1 means we already visited this tile
    public static int calculateBasin(ArrayList<ArrayList<Integer>> map, int y, int x) {
        int size = 0;
        if (map.get(y).get(x) == 9 || map.get(y).get(x) == -1) {
            return 0;
        } else {
            size++;
            map.get(y).set(x, -1);
        }
        size += calculateBasin(map, y, x - 1); // north
        size += calculateBasin(map, y + 1, x); // east
        size += calculateBasin(map, y, x + 1); // south
        size += calculateBasin(map, y - 1, x); // west
        return size;
    }

    public static int part2() {
        ArrayList<ArrayList<Integer>> input = readInput("data.txt");
        ArrayList<Integer> result = new ArrayList<>();
        for (int y = 1; y < input.size() - 1; y++) {
            for (int x = 1; x < input.get(1).size() - 1; x++) {
                if (input.get(y).get(x) != -1 && input.get(y).get(x) != 9)
                    result.add(calculateBasin(input, y, x));
            }
        }
        Collections.sort(result);
        return result.get(result.size() - 1) * result.get(result.size() - 2) * result.get(result.size() - 3);
    }

    public static void main(String[] args) {
        part1();
        System.out.println(part2());
    }
}