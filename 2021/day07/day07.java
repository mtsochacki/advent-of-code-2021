import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class day07 {
    public static ArrayList<Integer> readInput(String filename) {
        ArrayList<Integer> input = new ArrayList<>();
        Scanner sc = null;
        try {
            sc = new Scanner(new File(filename)).useDelimiter(",");
            while (sc.hasNextInt()) {
                input.add(sc.nextInt());
            }
        } catch (Exception e) {
            System.out.println("Something went horribly wrong" + e);
        } finally {
            sc.close();
        }
        return input;
    }

    public static void part1() {
        ArrayList<Integer> input = readInput("data.txt");
        Collections.sort(input);
        int median = 0;
        int fuel = 0;
        // calculate median
        if (input.size() % 2 == 0)
            median = (input.get((input.size()) / 2) + input.get(input.size() / 2 - 1)) / 2;
        else
            median = input.get(input.size() / 2);
        // calculate fuel
        for (int i = 0; i < input.size(); i++) {
            fuel += Math.abs((double) input.get(i) - median);
        }
        System.out.println("We need " + fuel + " fuel to move all the crabs to position " + median);
    }

    public static void main(String[] args) {
        part1();
    }
}