package com.aoc2021;

import java.io.File;
import java.util.*;

public class Day10 implements Day {
    private static class Chunk {
        private long score;
        private boolean isCorrupted;
        private final String lineStr;

        private static final HashMap<Character, Integer> openingScores = new HashMap<>();

        static {
            openingScores.put('(', 1);
            openingScores.put('[', 2);
            openingScores.put('{', 3);
            openingScores.put('<', 4);
        }

        private static final HashMap<Character, Integer> closingScores = new HashMap<>();

        static {
            closingScores.put(')', 3);
            closingScores.put(']', 57);
            closingScores.put('}', 1197);
            closingScores.put('>', 25137);
        }

        Chunk(String line) {
            this.lineStr = line;
            this.parseLine();
        }

        private void parseLine() {
            LinkedList<Character> stack = new LinkedList<>();
            for (char c : lineStr.toCharArray()) {
                if (openingScores.containsKey(c)) {
                    stack.addFirst(c);
                }
                /*
                 * ASCII codes for opening and closing pairs differ
                 * either by 1 ('(' and ')') or 2 (the others)
                 */
                else if (stack.peek() == c - 2 || stack.peek() == c - 1) {
                    stack.removeFirst();
                } else {
                    isCorrupted = true;
                    score += closingScores.get(c);
                    break;
                }
            }
            if (!isCorrupted) {
                for (Character element : stack) {
                    score = score * 5 + openingScores.get(element);
                }
            }
        }
    }

    private List<String> readLines(String filename) {
        ArrayList<String> lines = new ArrayList<>();
        try (Scanner sc = new Scanner(new File(filename))) {
            while (sc.hasNextLine()) {
                lines.add(sc.nextLine());
            }
        } catch (Exception e) {
            System.out.println("Something went wrong " + e);
        }
        return lines;
    }

    public String part1(String filename) {
        long score = 0;
        for (String line : readLines(filename)) {
            Chunk chunk = new Chunk(line);
            if (chunk.isCorrupted) {
                score += chunk.score;
            }
        }
        return String.valueOf(score);
    }

    public String part2(String filename) {
        ArrayList<Long> incompleteLineScores = new ArrayList<>();
        for (String line : readLines(filename)) {
            Chunk chunk = new Chunk(line);
            if (!chunk.isCorrupted) {
                incompleteLineScores.add(chunk.score);
            }
        }
        Collections.sort(incompleteLineScores);
        return String.valueOf(incompleteLineScores.get(incompleteLineScores.size() / 2));
    }
}