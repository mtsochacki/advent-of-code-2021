package com.github.mtsochacki.adventofcode;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.IntStream;

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
        Integer finalSumMagnitude = input.stream()
                .map(this::transformIntoSnailfishNumber)
                .reduce(this::reduceSnailfishNumber)
                .map(this::calculateMagnitude)
                .orElseThrow(() -> new RuntimeException("Error processing SnailFish"));
        return String.valueOf(finalSumMagnitude);
    }

    @Override
    public String part2(List<String> input) {
        List<List<RegularNumber>> snailfishNumbers = input.stream()
                .map(this::transformIntoSnailfishNumber)
                .toList();
        int highestMagnitude = findHighestMagnitude(snailfishNumbers);
        return String.valueOf(highestMagnitude);
    }

    private int findHighestMagnitude(List<List<RegularNumber>> snailfishNumbers) {
        int max = 0;
        for (int i = 0; i < snailfishNumbers.size(); i++) {
            for (int j = 0; j < snailfishNumbers.size(); j++) {
                if (i == j) {
                    continue;
                }
                List<RegularNumber> firstSnailfish = copySnailfishNumber(snailfishNumbers.get(i));
                List<RegularNumber> secondSnailfish = copySnailfishNumber(snailfishNumbers.get(j));
                List<RegularNumber> result = reduceSnailfishNumber(firstSnailfish, secondSnailfish);
                max = Math.max(calculateMagnitude(result), max);
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

        OptionalInt indexToExplode = findIndexToExplode(reducedSnailfish);
        OptionalInt indexToSplit = findIndexToSplit(reducedSnailfish);

        while (true) {
            if (indexToExplode.isPresent()) {
                explode(reducedSnailfish, indexToExplode.getAsInt());
            } else if (indexToSplit.isPresent()) {
                split(reducedSnailfish, indexToSplit.getAsInt());
            } else {
                break;
            }
            indexToExplode = findIndexToExplode(reducedSnailfish);
            indexToSplit = findIndexToSplit(reducedSnailfish);
        }

        return reducedSnailfish;
    }

    private OptionalInt findIndexToExplode(List<RegularNumber> snailfishNumber) {
        return IntStream.range(0, snailfishNumber.size())
                .filter(i -> snailfishNumber.get(i).getLevel() > 4)
                .findFirst();
    }

    private OptionalInt findIndexToSplit(List<RegularNumber> snailfishNumber) {
        return IntStream.range(0, snailfishNumber.size())
                .filter(i -> snailfishNumber.get(i).getValue() > 9)
                .findFirst();
    }

    private void explode(List<RegularNumber> snailfishNumber, int indexToExplode) {
        if (indexToExplode == 0) {
            snailfishNumber.get(indexToExplode).setValue(0);
            snailfishNumber.get(indexToExplode).decrementLevel();
            snailfishNumber.get(indexToExplode + 2).increaseValue(snailfishNumber.get(indexToExplode + 1).getValue());
            snailfishNumber.remove(indexToExplode + 1);
        } else if (indexToExplode + 2 >= snailfishNumber.size()) {
            snailfishNumber.get(indexToExplode - 1).increaseValue(snailfishNumber.get(indexToExplode).getValue());
            snailfishNumber.get(indexToExplode).setValue(0);
            snailfishNumber.get(indexToExplode).decrementLevel();
            snailfishNumber.remove(indexToExplode + 1);
        } else {
            snailfishNumber.get(indexToExplode - 1).increaseValue(snailfishNumber.get(indexToExplode).getValue());
            snailfishNumber.get(indexToExplode + 2).increaseValue(snailfishNumber.get(indexToExplode + 1).getValue());
            snailfishNumber.set(indexToExplode, new RegularNumber(4, 0));
            snailfishNumber.remove(indexToExplode + 1);
        }
    }

    private void split(List<RegularNumber> snailfishNumber, int indexToSplit) {
        int value = snailfishNumber.get(indexToSplit).getValue();
        int level = snailfishNumber.get(indexToSplit).getLevel() + 1;
        snailfishNumber.set(indexToSplit, new RegularNumber(level, value / 2));
        snailfishNumber.add(indexToSplit + 1, new RegularNumber(level, (int) Math.ceil((double) value / 2)));
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
