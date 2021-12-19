import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class day01 {
    // Takes report file and converts it to ArrayList
    public static ArrayList<Integer> readInput(String filename) {
        Scanner sc = null;
        ArrayList<Integer> input = new ArrayList<>();
        try {
            sc = new Scanner(new File(filename));
            while (sc.hasNextInt())
                input.add(sc.nextInt());
        } catch (Exception e) {
            System.out.println("Something went horribly wrong: " + e);
        } finally {
            sc.close();
        }
        return input;
    }
    // Counts the number of times a depth measurement
    // increases from the previous measurement
    public static void part1() {
        ArrayList<Integer> report = readInput("data.txt");
        int counter = 0;
        int currentDepth = 0;
        int previousDepth = report.get(0);
        for (int i = 1; i < report.size(); i++) {
            currentDepth = report.get(i);
            if (currentDepth > previousDepth)
                counter++;
            previousDepth = currentDepth;
        }
        System.out.println(counter);
    }
    // Counts the number of times a depth measurement
    // increases from the previous measurement in a
    // three-measurement sliding window
    public static void part2() {
        ArrayList<Integer> report = readInput("data.txt");
        int counter = 0;
        int currentDepth = 0;
        int previousDepth = report.get(0);
        for (int i = 0; i < report.size() - 3; i++) {
            currentDepth = report.get(i + 3);
            if (currentDepth > previousDepth)
                counter++;
            previousDepth = report.get(i + 1);
        }
        System.out.println(counter);
    }
    
    public static void main(String[] args) {
        part1();
        part2();
    }
}