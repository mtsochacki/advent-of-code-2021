package day01;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class day01 {
    public static ArrayList<Integer> readInput(String filename) {
        BufferedReader in;
        ArrayList<Integer> nums = new ArrayList<>();

        try {
            in = new BufferedReader(new FileReader(filename));
            nums = in.lines()
                    .mapToInt(Integer::parseInt)
                    .boxed()
                    .collect(Collectors.toCollection(ArrayList::new));
        } catch (IOException e) {
            System.out.println("Something went horribly wrong: " + e);
        }

        return nums;
    }

    public static int part1() {
        ArrayList<Integer> nums = readInput("data.txt");

        int prev = nums.get(0);
        int count = 0;
        for (int val: nums) {
            if (prev < val) {
                count++;
            }
            prev = val;
        }

        return count;
    }

    public static int part2() {
        int counter = 0;
        ArrayList<Integer> nums = readInput("data.txt");

        for (int i = 0; i + 3 < nums.size(); ++i) {
            if (nums.get(i) < nums.get(i + 3)) {
                counter++;
            }
        }

        return counter;
    }

    public static void main(String[] args) {
        System.out.println(part1());
        System.out.println(part2());
    }
}