package com.github.mtsochacki.adventofcode;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

@Slf4j
public class Day07 implements Day {
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

    private double arithmeticSequence(double start, double end) {
        return (Math.abs(end - start)) * (Math.abs(end - start) + 1) / 2;
    }

    @Override
    public String part1(List<String> input) {
        List<Integer> listOfPositions = readInput("data.txt");
        Collections.sort(listOfPositions);
        int median;
        if (listOfPositions.size() % 2 == 0)
            median = (listOfPositions.get((listOfPositions.size()) / 2)
                    + listOfPositions.get(listOfPositions.size() / 2 - 1)) / 2;
        else
            median = listOfPositions.get(listOfPositions.size() / 2);
        int totalFuel = 0;
        for (Integer position : listOfPositions)
            totalFuel += Math.abs(position - median);
        return String.valueOf(totalFuel);
    }

    @Override
    public String part2(List<String> input) {
        List<Integer> listOfPositions = readInput("data.txt");
        int fuel = 100000000;
        int max = Collections.max(listOfPositions);
        int min = Collections.min(listOfPositions);
        for (int testedAlignment = min; testedAlignment <= max; testedAlignment++) {
            int currentFuel = 0;
            for (Integer position : listOfPositions)
                currentFuel += arithmeticSequence(position, testedAlignment);
            if (currentFuel < fuel)
                fuel = currentFuel;
        }
        return String.valueOf(fuel);
    }
}