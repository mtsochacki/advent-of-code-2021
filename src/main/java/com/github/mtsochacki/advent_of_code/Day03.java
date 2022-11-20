package com.github.mtsochacki.advent_of_code;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day03 implements Day {
    private List<String> readReport(String filename) {
        ArrayList<String> diagnosticReport = new ArrayList<>();

        try (Scanner sc = new Scanner(new File(filename))) {
            while (sc.hasNext()) {
                diagnosticReport.add(sc.next());
            }
        } catch (Exception e) {
            System.out.println("Something went horribly wrong: " + e);
        }
        return diagnosticReport;
    }

    private int binToDec(int[] number) {
        int dec = 0;
        for (int i = 0; i < number.length; i++) {
            dec += number[i] * Math.pow(2, number.length - i - 1);
        }
        return dec;
    }

    private int calculatePower(String filename) {
        List<String> report = readReport(filename);
        int[] gammaRate = new int[report.get(0).length()];
        int[] epsilonRate = new int[report.get(0).length()];

        for (String number : report) {
            for (int i = 0; i < epsilonRate.length; i++) {
                gammaRate[i] += number.charAt(i) - 48;
            }
        }

        for (int i = 0; i < gammaRate.length; i++) {
            if (gammaRate[i] >= report.size() / 2) {
                gammaRate[i] = 1;
                epsilonRate[i] = 0;
            } else {
                gammaRate[i] = 0;
                epsilonRate[i] = 1;
            }
        }
        return binToDec(gammaRate) * binToDec(epsilonRate);
    }

    private int calculateSupport(String filename, boolean isOxygen) {
        List<String> report = readReport(filename);
        char dominantNumber;

        for (int i = 0; i < report.get(0).length(); i++) {
            int amountOfOnes = 0;
            int amountOfZeros = 0;
            for (String number : report) {
                if (number.charAt(i) == '1') {
                    amountOfOnes++;
                } else {
                    amountOfZeros++;
                }
            }

            if (amountOfOnes >= amountOfZeros) {
                dominantNumber = isOxygen ? '1' : '0';
            } else {
                dominantNumber = isOxygen ? '0' : '1';
            }

            ArrayList<String> tmpReport = new ArrayList<>();
            for (String number : report) {
                if (number.charAt(i) == dominantNumber) {
                    tmpReport.add(number);
                }
            }
            report = tmpReport;
            if (report.size() == 1) {
                break;
            }
        }
        return Integer.parseInt(report.get(0), 2);
    }

    public String part1(String filename) {
        return String.valueOf(calculatePower(filename));
    }

    public String part2(String filename) {
        return String.valueOf(calculateSupport(filename, true)
                * calculateSupport(filename, false));
    }
}