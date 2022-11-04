package com.github.mtsochacki.advent_of_code;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Day06 {
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

    public static long calculatePopulation(int days) {
        ArrayList<Integer> listOfFish = readInput("data.txt");
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

    public static void main(String[] args) {
        System.out.println(calculatePopulation(80));
        System.out.println(calculatePopulation(256));
    }
}