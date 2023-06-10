package com.github.mtsochacki.advent_of_code;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Day08 {
    public static class SignalPattern {
        String zero;
        String one;
        String two;
        String three;
        String four;
        String five;
        String six;
        String seven;
        String eight;
        String nine;
    }

    public static String[] sortWithinEachWord(String[] line) {
        for (int i = 0; i < line.length; i++) {
            char[] ar = line[i].toCharArray();
            Arrays.sort(ar);
            line[i] = String.valueOf(ar);
        }
        return line;
    }

    public static ArrayList<String[]> readInput(String filename) {
        ArrayList<String[]> input = new ArrayList<>();

        Scanner sc;
        try {
            sc = new Scanner(new File(filename));
            while (sc.hasNextLine()) {
                String[] line = sc.nextLine().split(" \\Q|\\E | ");
                input.add(sortWithinEachWord(line));
            }
            sc.close();
        } catch (Exception e) {
            System.out.println("Something went wrong" + e);
        }
        return input;
    }

    public static int part1() {
        int counter = 0;
        ArrayList<String[]> input = readInput("data.txt");
        for (String[] strings : input) {
            for (int j = 10; j < input.get(0).length; j++) {
                if (strings[j].length() == 2 || strings[j].length() == 4 ||
                    strings[j].length() == 3 || strings[j].length() == 7)
                    counter++;
            }
        }
        return counter;
    }

    public static void deduceOneFourSevenEight(String[] signals, SignalPattern pattern) {
        for (String signal : signals) {
            switch (signal.length()) {
                case 2 -> pattern.one = signal;
                case 4 -> pattern.four = signal;
                case 3 -> pattern.seven = signal;
                case 7 -> pattern.eight = signal;
                default -> {
                }
            }
        }
    }
    // Zero i 6 segments long and is not a 6 or a 9
    public static void deduceZero(String[] signals, SignalPattern pattern) {
        for (int i = 0; i < 10; i++)
            if (signals[i].length() == 6 && signals[i] != pattern.six
                                         && signals[i] != pattern.nine)
                pattern.zero = signals[i];
    }
    // Two is 5 segments long and is neither 3 nor 5
    public static void deduceTwo(String[] signals, SignalPattern pattern) {
        for (int i = 0; i < 10; i++) {
            if (signals[i].length() == 5 && !signals[i].equals(pattern.three)
                                         && !signals[i].equals(pattern.five))
                pattern.two = signals[i];
        }
    }
    // Three is 5 segments long and contains both segments of 1
    public static void deduceThree(String[] signals, SignalPattern pattern) {
        char[] tokens = pattern.one.toCharArray();
        for (int i = 0; i < 10; i++) {
            if (signals[i].length() == 5 &&signals[i].contains(String.valueOf(tokens[0]))
                    && signals[i].contains(String.valueOf(tokens[1])))
                pattern.three = signals[i];
        }
    }
    // Five is 5 segments long and all its segments are part of 9 and is not 3
    public static void deduceFive(String[] signals, SignalPattern pattern) {
        for (int j = 0; j < 10; j++) {
            char[] tokens = signals[j].toCharArray();
            if (signals[j].length() == 5 && pattern.nine.contains(String.valueOf(tokens[0]))
                    && pattern.nine.contains(String.valueOf(tokens[1]))
                    && pattern.nine.contains(String.valueOf(tokens[2]))
                    && pattern.nine.contains(String.valueOf(tokens[3]))
                    && pattern.nine.contains(String.valueOf(tokens[4])) &&
                    signals[j] != pattern.three)
                pattern.five = signals[j];  
        }
    }
    // Six is 6 segments long and contains only one segment of 1
    public static void deduceSix(String[] signals, SignalPattern pattern) {
        char[] tokens = pattern.one.toCharArray();
        for (int i = 0; i < 10; i++) {
            if (signals[i].length() == 6 && (!signals[i].contains(String.valueOf(tokens[0]))
                    || !signals[i].contains(String.valueOf(tokens[1]))))
                pattern.six = signals[i];
        }
    }
    // Nine is 6 segments long and contains all segments of 3
    public static void deduceNine(String[] signals, SignalPattern pattern) {
        char[] tokens = pattern.three.toCharArray();
        for (int i = 0; i < 10; i++) {
            if (signals[i].length() == 6 && signals[i].contains(String.valueOf(tokens[0]))
                    && signals[i].contains(String.valueOf(tokens[1]))
                    && signals[i].contains(String.valueOf(tokens[2]))
                    && signals[i].contains(String.valueOf(tokens[3]))
                    && signals[i].contains(String.valueOf(tokens[4])))
                pattern.nine = signals[i];
        }
    }

    public static int calculateOutput(String[] signals, SignalPattern pattern) {
        int output = 0;
        int[] digits = new int[4];

        for (int i = 10; i < signals.length; i++) {
            if (signals[i].equals(pattern.zero))
                digits[i - 10] = 0;
            else if (signals[i].equals(pattern.one))
                digits[i - 10] = 1;
            else if (signals[i].equals(pattern.two))
                digits[i - 10] = 2;
            else if (signals[i].equals(pattern.three))
                digits[i - 10] = 3;
            else if (signals[i].equals(pattern.four))
                digits[i - 10] = 4;
            else if (signals[i].equals(pattern.five))
                digits[i - 10] = 5;
            else if (signals[i].equals(pattern.six))
                digits[i - 10] = 6;
            else if (signals[i].equals(pattern.seven))
                digits[i - 10] = 7;
            else if (signals[i].equals(pattern.eight))
                digits[i - 10] = 8;
            else if (signals[i].equals(pattern.nine))
                digits[i - 10] = 9;
        }

        for (int digit : digits) {
            output = output * 10 + digit;
        }
        return output;
    }

    public static int part2() {
        int result = 0;
        ArrayList<String[]> input = readInput("data.txt");
        for (String[] strings : input) {
            SignalPattern pattern = new SignalPattern();
            deduceOneFourSevenEight(strings, pattern);
            deduceSix(strings, pattern);
            deduceThree(strings, pattern);
            deduceNine(strings, pattern);
            deduceZero(strings, pattern);
            deduceFive(strings, pattern);
            deduceTwo(strings, pattern);
            result += calculateOutput(strings, pattern);
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(part1());
        System.out.println(part2());
    }
}