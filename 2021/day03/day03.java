package day03;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class day03 {
    public static ArrayList<String> readInput(String filename) {
        ArrayList<String> diagnosticReport = new ArrayList<>();

        try {
            Scanner sc = new Scanner(new File(filename));
            while (sc.hasNext()) {
                diagnosticReport.add(sc.next());
            }
        } catch (Exception e) {
            System.out.println("Something went horribly wrong: " + e);
        }

        return diagnosticReport;
    }

    public static int binToDec(int[] number) {
        int dec = 0;
        for (int i = 0; i < number.length; i++) {
            dec += number[i] * (1 << (number.length - i - 1));
        }
        return dec;
    }

    public static int part1() {
        ArrayList<String> input = readInput("data.txt");
        int N = input.get(0).length();
        int[] gammaRate = new int[N];
        int[] epsilonRate = new int[N];

        for (String value: input) {
            for (int j = 0; j < gammaRate.length; j++) {
                gammaRate[j] += value.charAt(j) - (int) '0';
            }
        }

        for (int i = 0; i < gammaRate.length; i++) {
            if (gammaRate[i] >= input.size() / 2) {
                gammaRate[i] = 1;
                epsilonRate[i] = 0;
            } else {
                gammaRate[i] = 0;
                epsilonRate[i] = 1;
            }
        }

        return binToDec(gammaRate) * binToDec(epsilonRate);
    }

    public static int calculateValue(boolean isOxygen) {
        ArrayList<String> input = readInput("data.txt");
        int amountOfOnes = 0;
        int amountOfZeros = 0;
        char dominantNumber;

        for (int i = 0; i < 12; i++) {
            for (String value: input) {
                if (value.charAt(i) == '1')
                    amountOfOnes++;
                else
                    amountOfZeros++;
            }

            if (amountOfOnes >= amountOfZeros)
                dominantNumber = isOxygen ? '1' : '0';
            else
                dominantNumber = isOxygen ? '0' : '1';

            ArrayList<String> new_input = new ArrayList<>();
            for (String value: input) {
                if (value.charAt(i) == dominantNumber) {
                    new_input.add(value);
                }
            }

            amountOfOnes = 0;
            amountOfZeros = 0;
            input = new_input;
            if (input.size() == 1)
                break;
        }

        return Integer.parseInt(input.get(0), 2);
    }

    public static void main(String[] args) {
        System.out.println(part1());
        System.out.println(calculateValue(true) * calculateValue(false));
    }
}