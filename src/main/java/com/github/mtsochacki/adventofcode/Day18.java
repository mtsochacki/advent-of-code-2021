package com.github.mtsochacki.adventofcode;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public class Day18 implements Day {
    @Data
    @AllArgsConstructor
    private static class RegularNumber {
        private int level;
        private int value;

        public RegularNumber(RegularNumber regularNumber) {
            this.level = regularNumber.level;
            this.value = regularNumber.value;
        }

        public void incrementLevel() {
            this.level++;
        }

        public void decrementLevel() {
            this.level--;
        }

        public void increaseValue(int value) {
            this.value += value;
        }
    }

    @Override
    public String part1(List<String> input) {
        Integer slugMagnitude = input.stream()
                .map(this::transformIntoSnailfishNumber)
                .reduce(this::reduceSnailfishNumber)
                .map(this::calculateMagnitude)
                .orElseThrow(() -> new RuntimeException("Error processing SnailFish"));
        return String.valueOf(slugMagnitude);
    }

    @Override
    public String part2(List<String> input) {
        List<List<RegularNumber>> snailfishNumbers = input.stream()
                .map(this::transformIntoSnailfishNumber)
                .toList();
        int max = findHighestMagnitude(snailfishNumbers);
        return String.valueOf(max);
    }

    private int findHighestMagnitude(List<List<RegularNumber>> snailfishNumbers) {
        int max = 0;
        for (int i = 0; i < snailfishNumbers.size(); i++) {
            for (int j = 0; j < snailfishNumbers.size(); j++) {
                if (i == j) {
                    continue;
                }
                List<RegularNumber> firstSlug = copySnailfishNumber(snailfishNumbers.get(i));
                List<RegularNumber> copyFirstSlug = copySnailfishNumber(snailfishNumbers.get(i));
                List<RegularNumber> secondSlug = copySnailfishNumber(snailfishNumbers.get(j));
                List<RegularNumber> copySecondSlug = copySnailfishNumber(snailfishNumbers.get(j));
                List<RegularNumber> firstResult = reduceSnailfishNumber(firstSlug, secondSlug);
                List<RegularNumber> secondResult = reduceSnailfishNumber(copySecondSlug, copyFirstSlug);
                int newMax = Math.max(calculateMagnitude(firstResult), calculateMagnitude(secondResult));
                max = Math.max(newMax, max);
            }
        }
        return max;
    }

    private List<RegularNumber> copySnailfishNumber(List<RegularNumber> snailfishNumber) {
        List<RegularNumber> newSnailfishNumber = new ArrayList<>();
        for (RegularNumber regularNumber : snailfishNumber) {
            newSnailfishNumber.add(new RegularNumber(regularNumber));
        }
        return newSnailfishNumber;
    }

    private List<RegularNumber> transformIntoSnailfishNumber(String line) {
        List<RegularNumber> snailfishNumber = new ArrayList<>();
        int level = 0;
        for (char character : line.toCharArray()) {
            if (character == '[') {
                level++;
            } else if (character == ']') {
                level--;
            } else if (Character.isDigit(character)) {
                snailfishNumber.add(new RegularNumber(level, Character.getNumericValue(character)));
            }
        }
        return snailfishNumber;
    }

    private List<RegularNumber> reduceSnailfishNumber(List<RegularNumber> firstSnailfish, List<RegularNumber> secondSnailfish) {
        List<RegularNumber> resultSlug = addSnailfish(firstSnailfish, secondSnailfish);
        return reduceSlug(resultSlug);
    }

    private List<RegularNumber> addSnailfish(List<RegularNumber> firstSnailfish, List<RegularNumber> secondSnailfish) {
        firstSnailfish.addAll(secondSnailfish);
        for (RegularNumber slug : firstSnailfish) {
            slug.incrementLevel();
        }
        return firstSnailfish;
    }

    private List<RegularNumber> reduceSlug(List<RegularNumber> snailfishNumber) {
        List<RegularNumber> reducedSnailfish = copySnailfishNumber(snailfishNumber);
        while (isInNeedOfExplosion(reducedSnailfish) || isInNeedOfSplit(reducedSnailfish)) {
            if (isInNeedOfExplosion(reducedSnailfish)) {
                explode(reducedSnailfish);
            } else if (isInNeedOfSplit(reducedSnailfish)) {
                split(reducedSnailfish);
            }
        }
        return reducedSnailfish;
    }

    private boolean isInNeedOfExplosion(List<RegularNumber> snailfishNumber) {
        return snailfishNumber.stream()
                .anyMatch(regularNumber -> regularNumber.getLevel() > 4);
    }

    private boolean isInNeedOfSplit(List<RegularNumber> snailfishNumber) {
        return snailfishNumber.stream()
                .anyMatch(regularNumber -> regularNumber.getValue() > 9);
    }

    private void explode(List<RegularNumber> snailfishNumber) {
        for (int i = 0; i < snailfishNumber.size(); i++) {
            if (snailfishNumber.get(i).getLevel() >= 5) {
                if (i == 0) {
                    snailfishNumber.get(i).setValue(0);
                    snailfishNumber.get(i).decrementLevel();
                    snailfishNumber.get(i + 2).increaseValue(snailfishNumber.get(i + 1).getValue());
                    snailfishNumber.remove(i + 1);
                } else if (i + 2 >= snailfishNumber.size()) {
                    snailfishNumber.get(i - 1).increaseValue(snailfishNumber.get(i).getValue());
                    snailfishNumber.get(i).setValue(0);
                    snailfishNumber.get(i).decrementLevel();
                    snailfishNumber.remove(i + 1);
                } else {
                    snailfishNumber.get(i - 1).increaseValue(snailfishNumber.get(i).getValue());
                    snailfishNumber.get(i + 2).increaseValue(snailfishNumber.get(i + 1).getValue());
                    snailfishNumber.get(i + 1).setValue(0);
                    snailfishNumber.get(i + 1).decrementLevel();
                    snailfishNumber.remove(i);
                }
                break;
            }
        }
    }

    private void split(List<RegularNumber> snailfishNumber) {
        for (int i = 0; i < snailfishNumber.size(); i++) {
            if (snailfishNumber.get(i).getValue() > 9) {
                int value = snailfishNumber.get(i).getValue();
                int level = snailfishNumber.get(i).getLevel() + 1;
                snailfishNumber.remove(i);
                snailfishNumber.add(i, new RegularNumber(level, value / 2));
                snailfishNumber.add(i + 1, new RegularNumber(level, (int) Math.ceil((double) value / 2)));
                break;
            }
        }
    }


    private Integer calculateMagnitude(List<RegularNumber> snailfishNumber) {
        List<Integer> snailfishNumberValues = snailfishNumber.stream()
                .map(RegularNumber::getValue)
                .toList();
        while (snailfishNumberValues.size() > 1) {
            List<Integer> newList = new ArrayList<>();
            for (int i = 0; i < snailfishNumberValues.size() - 1; i += 2) {
                newList.add(3 * snailfishNumberValues.get(i) + 2 * snailfishNumberValues.get(i + 1));
            }
            snailfishNumberValues = newList;
        }
        return snailfishNumberValues.get(0);
    }
}
