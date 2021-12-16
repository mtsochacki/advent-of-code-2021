import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class day13 {
    public static ArrayList<ArrayList<Integer>> readInput(String filename) {
        ArrayList<ArrayList<Integer>> input = new ArrayList<>();
        Scanner sc = null;

        try {
            sc = new Scanner(new File(filename));
            sc.useDelimiter(",|\n");
            for (int i = 0; i < 916; i++) {
                ArrayList<Integer> line = new ArrayList<>();
                for (int j = 0; j < 2; j++) {
                    line.add(sc.nextInt());
                }
                input.add(line);
            }
        } catch (Exception e) {
            System.out.println("Something went wrong " + e);
        } finally {
            sc.close();
        }
       // printAll(input);
        return input;
    }

    public static void printAll(ArrayList<ArrayList<Integer>> input) {
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(0).size(); j++) {
                System.out.println(input.get(i).get(j));
            }
            System.out.println();
        }
    }

    public static ArrayList<ArrayList<Integer>> foldX(ArrayList<ArrayList<Integer>> input, int x) {
        ArrayList<ArrayList<Integer>> output = new ArrayList<>();
        for (ArrayList<Integer> line : input) {
            if (line.get(0) > x) {
                int distance = line.get(0) - x;
                line.set(0, Math.abs(x - distance));
                output.add(line);
            }
            else if (line.get(0) < x){
                output.add(line);
            }
        }
        Set<ArrayList<Integer>> set = new HashSet<>(output);
        output.clear();
        output.addAll(set);
        return output;
    }

    public static ArrayList<ArrayList<Integer>> foldY(ArrayList<ArrayList<Integer>> input, int y) {
        ArrayList<ArrayList<Integer>> output = new ArrayList<>();
        for (ArrayList<Integer> line : input) {
            if (line.get(1) > y) {
                int distance = line.get(1) - y;
                line.set(1, y - distance);
                output.add(line);
            }
            else if (line.get(1) < y){
                output.add(line);
            }
        }
        Set<ArrayList<Integer>> set = new HashSet<>(output);
        output.clear();
        output.addAll(set);
        return output;
    }

    public static void part1() {
        ArrayList<ArrayList<Integer>> input = readInput("data.txt");
        input = foldX(input, 655);
        input = foldY(input, 447);
        input = foldX(input, 327);
        input = foldY(input, 223);
        input = foldX(input, 163);
        input = foldY(input, 111);
        input = foldX(input, 81);
        input = foldY(input, 55);
        input = foldX(input, 40);
        input = foldY(input, 27);
        input = foldY(input, 13);
        input = foldY(input, 6);
        System.out.println("");
       // printAll(input);
       char escCode = 0x1B;
       for (ArrayList<Integer> line : input) {
           int row = line.get(1)+1;
           int column = line.get(0)+1;
           System.out.print(String.format("%c[%d;%dH#", escCode, row, column));
       }
       System.out.println(input.size());

    }

    public static void main(String[] args) {
        part1();
    }
}