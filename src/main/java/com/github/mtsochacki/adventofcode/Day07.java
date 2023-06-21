package com.github.mtsochacki.adventofcode;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
public class Day07 implements Day {
    private List<Integer> readInput(List<String> input) {
        String[] numbers = input.get(0).split(",");
        return Arrays.stream(numbers).map(Integer::valueOf).sorted().toList();
    }

    private double arithmeticSequence(double start, double end) {
        return (Math.abs(end - start)) * (Math.abs(end - start) + 1) / 2;
    }

    @Override
    public String part1(List<String> input) {
        List<Integer> listOfPositions = readInput(input);
        int median;
        if (listOfPositions.size() % 2 == 0) {
            median = (listOfPositions.get((listOfPositions.size()) / 2) + listOfPositions.get(listOfPositions.size() / 2 - 1)) / 2;
        } else {
            median = listOfPositions.get(listOfPositions.size() / 2);
        }
        int totalFuel = 0;
        for (Integer position : listOfPositions) {
            totalFuel += Math.abs(position - median);
        }
        return String.valueOf(totalFuel);
    }

    @Override
    public String part2(List<String> input) {
        List<Integer> listOfPositions = readInput(input);
        int fuel = 100000000;
        int max = Collections.max(listOfPositions);
        int min = Collections.min(listOfPositions);
        for (int testedAlignment = min; testedAlignment <= max; testedAlignment++) {
            int currentFuel = 0;
            for (Integer position : listOfPositions) {
                currentFuel += arithmeticSequence(position, testedAlignment);
            }
            if (currentFuel < fuel) {
                fuel = currentFuel;
            }
        }
        return String.valueOf(fuel);
    }
}