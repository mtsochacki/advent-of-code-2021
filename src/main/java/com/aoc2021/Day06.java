package com.aoc2021;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Day06 implements Day {
    private List<Integer> readInput(String filename) {
        ArrayList<Integer> input = new ArrayList<>();
        try (Scanner sc = new Scanner(new File(filename))) {
            sc.useDelimiter(",");
            while (sc.hasNextInt()) {
                input.add(sc.nextInt());
            }
        } catch (Exception e) {
            System.out.println("Something went horribly wrong" + e);
        }
        return input;
    }

    private long calculatePopulation(int days, String filename) {
        List<Integer> listOfFish = readInput(filename);
        ArrayList<Long> popNumbers = new ArrayList<>(Collections.nCopies(9, 0L));

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
    public String part1(String filename) {
        return String.valueOf(calculatePopulation(80, filename));
    }

    @Override
    public String part2(String filename) {
        return String.valueOf(calculatePopulation(256, filename));
    }
}