package com.github.mtsochacki.advent_of_code;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Day07 {
    public static ArrayList<Integer> readInput(String filename) {
        ArrayList<Integer> input = new ArrayList<>();
        Scanner sc;
        try {
            sc = new Scanner(new File(filename)).useDelimiter(",");
            while (sc.hasNextInt()) {
                input.add(sc.nextInt());
            }
            sc.close();
        } catch (Exception e) {
            System.out.println("Something went horribly wrong" + e);
        }
        return input;
    }

    public static double arithmeticSequence(double start, double end) {
        return (Math.abs(end - start)) * (Math.abs(end - start) + 1) / 2;
    }

    public static int part1() {
        ArrayList<Integer> listOfPositions = readInput("data.txt");
        Collections.sort(listOfPositions);
        // calculate median
        int median;
        if (listOfPositions.size() % 2 == 0)
            median = (listOfPositions.get((listOfPositions.size()) / 2)
                    + listOfPositions.get(listOfPositions.size() / 2 - 1)) / 2;
        else
            median = listOfPositions.get(listOfPositions.size() / 2);
        // calculate fuel
        int totalFuel = 0;
        for (Integer position : listOfPositions)
            totalFuel += Math.abs(position - median);
        return totalFuel;
    }

    public static int part2() {
        ArrayList<Integer> listOfPositions = readInput("data.txt");
        int fuel = 100000000;
        // Check each point between min and max of all the positions
        int max = Collections.max(listOfPositions);
        int min = Collections.min(listOfPositions);
        for (int testedAlignment = min; testedAlignment <= max; testedAlignment++) {
            int currentFuel = 0;
            for (Integer position : listOfPositions)
                currentFuel += arithmeticSequence(position, testedAlignment);
            if (currentFuel < fuel)
                fuel = currentFuel;
        }
        return fuel;
    }

    public static void main(String[] args) {
        System.out.println(part1());
        System.out.println(part2());
    }
}