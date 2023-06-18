package com.github.mtsochacki.adventofcode;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Day03 implements Day {
    @Override
    public String part1(List<String> report) {
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
        return String.valueOf(binToDec(gammaRate) * binToDec(epsilonRate));
    }

    @Override
    public String part2(List<String> input) {
        return String.valueOf(calculateSupport(true, input) * calculateSupport(false, input));
    }

    private int calculateSupport(boolean isOxygen, List<String> report) {
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

    private int binToDec(int[] number) {
        int dec = 0;
        for (int i = 0; i < number.length; i++) {
            dec += number[i] * Math.pow(2, number.length - i - 1);
        }
        return dec;
    }
}