package com.github.mtsochacki.adventofcode;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

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
        return String.valueOf(calculateSupport(input, true) * calculateSupport(input, false));
    }

    private int calculateSupport(List<String> report, boolean isOxygen) {
        StringBuilder beginningSequence = new StringBuilder();

        for (int i = 0; report.size() > 1; i++) {
            int amountOfOnes = 0;
            int amountOfZeros = 0;

            for (String number : report) {
                if (number.charAt(i) == '1') {
                    amountOfOnes++;
                } else {
                    amountOfZeros++;
                }
            }

            if (isOxygen) {
                beginningSequence.append(amountOfOnes >= amountOfZeros ? "1" : "0");
            } else {
                beginningSequence.append(amountOfOnes >= amountOfZeros ? "0" : "1");
            }

            report = report.stream()
                    .filter(line -> line.startsWith(beginningSequence.toString()))
                    .collect(Collectors.toList());
        }
        return Integer.parseInt(report.get(0), 2);
    }

    private int binToDec(int[] number) {
        int dec = 0;
        for (int i = 0; i < number.length; i++) {
            dec += number[i] << number.length - i - 1;
        }
        return dec;
    }
}