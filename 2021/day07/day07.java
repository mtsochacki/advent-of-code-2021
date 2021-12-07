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

    public static double arithmeticSequence(double start, double end) {
        return (Math.abs(end - start)) / 2 * (Math.abs(end - start) + 1);
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
        System.out.println("We need " + fuel + " fuel to move all the crabs to position " + median + ".");
    }

    public static void part2() {
        ArrayList<Integer> input = readInput("data.txt");
        double fuel = 100000000;
        double currentFuel = 0;
        int max = Collections.max(input);
        for (int i = 0; i < max; i++) {
            for (int j = 0; j < input.size(); j++) {
                currentFuel += arithmeticSequence(input.get(j), i);
            }
            if (currentFuel < fuel)
            {
                fuel = currentFuel;
            }
            currentFuel = 0;
        }
        System.out.println("We need " + fuel + " fuel to move all the crabs to the place that we need to move them to.");
    }

    public static void main(String[] args) {
        part1();
        part2();
    }
}