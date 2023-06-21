package com.github.mtsochacki.adventofcode;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class Day08 implements Day {
    @Override
    public String part1(List<String> input) {
        int counter = 0;
        List<String[]> input1 = readInput(input);
        for (String[] strings : input1) {
            for (int j = 10; j < input1.get(0).length; j++) {
                if (strings[j].length() == 2 || strings[j].length() == 4 ||
                        strings[j].length() == 3 || strings[j].length() == 7)
                    counter++;
            }
        }
        return String.valueOf(counter);
    }

    @Override
    public String part2(List<String> input) {
        int result = 0;
        List<String[]> readInp = readInput(input);
        for (String[] strings : readInp) {
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
        return String.valueOf(result);
    }

    private List<String[]> readInput(List<String> input) {
        return input.stream().map(line -> {
            String[] splittedLine = line.split(" \\Q|\\E | ");
            return sortWithinEachWord(splittedLine);
        }).toList();

    }

    private String[] sortWithinEachWord(String[] line) {
        for (int i = 0; i < line.length; i++) {
            char[] ar = line[i].toCharArray();
            Arrays.sort(ar);
            line[i] = String.valueOf(ar);
        }
        return line;
    }

    private void deduceOneFourSevenEight(String[] signals, SignalPattern pattern) {
        for (String signal : signals) {
            if (signal.length() == 2) {
                pattern.one = signal;
            } else if (signal.length() == 4) {
                pattern.four = signal;
            } else if (signal.length() == 3) {
                pattern.seven = signal;
            } else if (signal.length() == 7) {
                pattern.eight = signal;
            }
        }
    }

    // Six is 6 segments long and contains only one segment of 1
    private void deduceSix(String[] signals, SignalPattern pattern) {
        char[] tokens = pattern.one.toCharArray();
        for (int i = 0; i < 10; i++) {
            if (signals[i].length() == 6 && (!signals[i].contains(String.valueOf(tokens[0]))
                    || !signals[i].contains(String.valueOf(tokens[1]))))
                pattern.six = signals[i];
        }
    }

    // Three is 5 segments long and contains both segments of 1
    private void deduceThree(String[] signals, SignalPattern pattern) {
        char[] tokens = pattern.one.toCharArray();
        for (int i = 0; i < 10; i++) {
            if (signals[i].length() == 5 && signals[i].contains(String.valueOf(tokens[0]))
                    && signals[i].contains(String.valueOf(tokens[1])))
                pattern.three = signals[i];
        }
    }

    // Nine is 6 segments long and contains all segments of 3
    private void deduceNine(String[] signals, SignalPattern pattern) {
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

    // Zero i 6 segments long and is not a 6 or a 9
    private void deduceZero(String[] signals, SignalPattern pattern) {
        for (int i = 0; i < 10; i++)
            if (signals[i].length() == 6 && !signals[i].equals(pattern.six)
                    && !signals[i].equals(pattern.nine))
                pattern.zero = signals[i];
    }

    // Five is 5 segments long and all its segments are part of 9 and is not 3
    private void deduceFive(String[] signals, SignalPattern pattern) {
        for (int j = 0; j < 10; j++) {
            char[] tokens = signals[j].toCharArray();
            if (signals[j].length() == 5 && pattern.nine.contains(String.valueOf(tokens[0]))
                    && pattern.nine.contains(String.valueOf(tokens[1]))
                    && pattern.nine.contains(String.valueOf(tokens[2]))
                    && pattern.nine.contains(String.valueOf(tokens[3]))
                    && pattern.nine.contains(String.valueOf(tokens[4])) &&
                    !signals[j].equals(pattern.three))
                pattern.five = signals[j];
        }
    }

    // Two is 5 segments long and is neither 3 nor 5
    private void deduceTwo(String[] signals, SignalPattern pattern) {
        for (int i = 0; i < 10; i++) {
            if (signals[i].length() == 5 && !signals[i].equals(pattern.three)
                    && !signals[i].equals(pattern.five))
                pattern.two = signals[i];
        }
    }

    private int calculateOutput(String[] signals, SignalPattern pattern) {
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

    private static class SignalPattern {
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
}