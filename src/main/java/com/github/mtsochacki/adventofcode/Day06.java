package com.github.mtsochacki.adventofcode;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
public class Day06 implements Day {
    @Override
    public String part1(List<String> input) {
        return String.valueOf(calculatePopulation(80, input));
    }

    @Override
    public String part2(List<String> input) {
        return String.valueOf(calculatePopulation(256, input));
    }

    public long calculatePopulation(int days, List<String> input) {
        List<Integer> listOfFish = readInput(input);
        List<Long> popNumbers = new ArrayList<>(Collections.nCopies(9, 0L));

        for (Integer fish : listOfFish) {
            popNumbers.set(fish, popNumbers.get(fish) + 1);
        }

        for (int i = 0; i < days; i++) {
            long tmp = popNumbers.get(0);
            for (int j = 0; j < popNumbers.size() - 1; j++) {
                popNumbers.set(j, popNumbers.get(j + 1));
            }

            popNumbers.set(6, popNumbers.get(6) + tmp);
            popNumbers.set(8, tmp);
        }

        long totalPopulation = 0;
        for (Long number : popNumbers) {
            totalPopulation += number;
        }

        return totalPopulation;
    }

    private List<Integer> readInput(List<String> input) {
        String[] numbers = input.get(0).split(",");
        return Arrays.stream(numbers)
                .map(Integer::valueOf)
                .toList();
    }
}