package com.github.mtsochacki.adventofcode;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

@Slf4j
public class Day06 implements Day {

    private List<Integer> readInput(String filename) {
        List<Integer> input = new ArrayList<>();
        try (Scanner sc = new Scanner(new File(filename)).useDelimiter(",")) {
            while (sc.hasNextInt()) {
                input.add(sc.nextInt());
            }
        } catch (Exception e) {
            log.error("Something went horribly wrong: {}", e.getMessage());
        }
        return input;
    }

    public long calculatePopulation(int days) {
        List<Integer> listOfFish = readInput("data.txt");
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

    @Override
    public String part1(List<String> input) {
        return String.valueOf(calculatePopulation(80));
    }

    @Override
    public String part2(List<String> input) {
        return String.valueOf(calculatePopulation(256));
    }
}