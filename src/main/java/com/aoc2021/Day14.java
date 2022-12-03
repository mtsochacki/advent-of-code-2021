package com.aoc2021;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class Day14 implements Day {
    private static class Rule {
        private String pair;
        private String outcome;
    }

    private String readTemplate(String filename) {
        String template = null;
        try (Scanner sc = new Scanner(new File(filename))) {
            template = sc.next();
        } catch (Exception e) {
            System.out.println("Something went wrong" + e);
        }
        return template;
    }

    private ArrayList<Rule> readRules(String filename) {
        ArrayList<Rule> ruleList = new ArrayList<>();
        try (Scanner sc = new Scanner(new File(filename))) {
            sc.useDelimiter("\\n| -> ");
            while (sc.hasNext()) {
                Rule rule = new Rule();
                rule.pair = sc.next();
                rule.outcome = sc.next();
                ruleList.add(rule);
            }
        } catch (Exception e) {
            System.out.println("Something went wrong" + e);
        }
        return ruleList;
    }

    private HashMap<String, Long> splitToChunks(String template) {
        HashMap<String, Long> chunks = new HashMap<>();
        for (int i = 0; i < template.length() - 1; i++) {
            chunks.merge(template.substring(i, i + 2), 1L, Long::sum);
        }
        return chunks;
    }

    private HashMap<String, Long> countLetters(String template) {
        HashMap<String, Long> lettersCount = new HashMap<>();
        for (char letter : template.toCharArray()) {
            lettersCount.merge(String.valueOf(letter), 1L, Long::sum);
        }
        return lettersCount;
    }

    private Long calculatePolymers(int steps, String filename) {
        String template = readTemplate(filename);
        ArrayList<Rule> ruleList = readRules(filename);
        HashMap<String, Long> chunks = splitToChunks(template);
        HashMap<String, Long> letters = countLetters(template);
        for (int i = 0; i < steps; i++) {
            HashMap<String, Long> newChunks = new HashMap<>();
            for (Rule rule : ruleList) {
                if (chunks.containsKey(rule.pair)) {
                    letters.merge(rule.outcome, chunks.get(rule.pair), Long::sum);
                    String leftPair = rule.pair.charAt(0) + rule.outcome;
                    String rightPair = rule.outcome + rule.pair.charAt(1);
                    newChunks.merge(leftPair, chunks.get(rule.pair), Long::sum);
                    newChunks.merge(rightPair, chunks.get(rule.pair), Long::sum);
                }
            }
            chunks = newChunks;
        }
        return Collections.max(letters.values()) - Collections.min(letters.values());
    }

    public String part1(String filename) {
        return String.valueOf(calculatePolymers(10, filename));
    }

    public String part2(String filename) {
        return String.valueOf(calculatePolymers(40, filename));
    }
}
