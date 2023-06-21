package com.github.mtsochacki.adventofcode;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class Day14 implements Day {
    @Override
    public String part1(List<String> input) {
        return String.valueOf(calculatePolymers(10, input));
    }

    @Override
    public String part2(List<String> input) {
        return String.valueOf(calculatePolymers(40, input));
    }

    private Long calculatePolymers(int steps, List<String> input) {
        String template = readTemplate(input);
        List<Rule> ruleList = readRules(input);
        Map<String, Long> chunks = splitToChunks(template);
        Map<String, Long> letters = countLetters(template);
        for (int i = 0; i < steps; i++) {
            Map<String, Long> newChunks = new HashMap<>();
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

    private String readTemplate(List<String> input) {
        return input.get(0);
    }

    /*
     * Given the name of a file containing a list of rules for creating
     * new elements in the form of arrow-delimited strings, e.g. "CH -> B",
     * read and transform them into a list of `Rule`s
     */
    private List<Rule> readRules(List<String> input) {
        return input.stream()
                .skip(2)
                .map(line -> {
                    String[] tokens = line.split(" -> ");
                    return Rule.builder()
                            .pair(tokens[0])
                            .outcome(tokens[1])
                            .build();
                })
                .toList();
    }

    /*
     * Split the template into two-letter chunks and count
     * the occurrences of them in the template.
     *
     * E.g. given a template
     * NCCNC
     * Transform it into a map
     * {"NC": 2, "CN": 1, "CC": 1}
     */
    private Map<String, Long> splitToChunks(String template) {
        Map<String, Long> chunks = new HashMap<>();
        for (int i = 0; i < template.length() - 1; i++) {
            chunks.merge(template.substring(i, i + 2), 1L, Long::sum);
        }
        return chunks;
    }

    /*
     * Split the template into individual letters and count the occurrences
     * of them in the template.
     *
     * E.g. given a template
     * NCNBCHB
     * Transform it into a map
     * {"N":2, "C":2, "B":2, "H":1}
     */
    private Map<String, Long> countLetters(String template) {
        Map<String, Long> lettersCount = new HashMap<>();
        for (char letter : template.toCharArray()) {
            lettersCount.merge(String.valueOf(letter), 1L, Long::sum);
        }
        return lettersCount;
    }

    @Builder
    private static class Rule {
        private String pair;
        private String outcome;
    }
}
