package com.github.mtsochacki.adventofcode;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Slf4j
public class Day10 implements Day {
    @Override
    public String part1(List<String> input) {
        long score = 0;
        for (String line : input) {
            Chunk chunk = new Chunk(line);
            if (chunk.isCorrupted) {
                score += chunk.score;
            }
        }
        return String.valueOf(score);
    }

    @Override
    public String part2(List<String> input) {
        List<Long> incompleteLineScores = new ArrayList<>();
        for (String line : input) {
            Chunk chunk = new Chunk(line);
            if (!chunk.isCorrupted) {
                incompleteLineScores.add(chunk.score);
            }
        }
        Collections.sort(incompleteLineScores);
        return String.valueOf(incompleteLineScores.get(incompleteLineScores.size() / 2));
    }

    private static class Chunk {
        private static final Map<Character, Integer> openingScores = new HashMap<>();
        private static final Map<Character, Integer> closingScores = new HashMap<>();

        static {
            openingScores.put('(', 1);
            openingScores.put('[', 2);
            openingScores.put('{', 3);
            openingScores.put('<', 4);
        }

        static {
            closingScores.put(')', 3);
            closingScores.put(']', 57);
            closingScores.put('}', 1197);
            closingScores.put('>', 25137);
        }

        private final String lineStr;
        long score;
        boolean isCorrupted;

        Chunk(String line) {
            this.lineStr = line;
            this.parseLine();
        }

        void parseLine() {
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
}