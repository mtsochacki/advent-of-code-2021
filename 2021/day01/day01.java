import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class day01 {

    public static ArrayList<Integer> readInput(String filename) {
        Scanner sc = null;
        ArrayList<Integer> input = new ArrayList<>();

        try {
            sc = new Scanner(new File(filename));
            while (sc.hasNextInt())
                input.add(sc.nextInt());
        } catch (Exception e) {
            System.out.println("Something went horribly wrong: " + e);
        }
        return input;
    }
    // Counts the number of times a depth measurement
    // increases from the previous measurement
    public static void part1() {
        ArrayList<Integer> report = readInput("data.txt");
        int currentDepth = 0;
        int counter = 0;
        int previousDepth = report.get(0);

        for (int i = 1; i < report.size(); i++) {
            currentDepth = report.get(i);
            if (currentDepth > previousDepth)
                counter++;
            previousDepth = currentDepth;
        }

        System.out.println(counter);
    }

    public static int part2() {

        int counter = 0;
        int total = 0;
        int previous = 0;
        int x = 0;
        Queue<Integer> q = new LinkedList<Integer>();
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader("data.txt"));
            String l;
            x = Integer.parseInt(in.readLine());
            q.add(x);
            total = total + x;
            x = Integer.parseInt(in.readLine());
            q.add(x);
            total = total + x;
            x = Integer.parseInt(in.readLine());
            q.add(x);
            total = total + x;
            previous = total;
            while ((l = in.readLine()) != null) {
                x = Integer.parseInt(l);
                q.add(x);
                total = total + x - q.remove();
                if (total > previous) {
                    counter++;
                }
                previous = total;
            }
        } catch (IOException e) {
            System.out.println("Something went horribly wrong");
        }
        return counter;
    }

    public static void main(String[] args) {
        part1();
        System.out.println(part2());
    }
}