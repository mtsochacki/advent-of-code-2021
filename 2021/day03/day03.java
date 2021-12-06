import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class day03 {
    public static ArrayList<String> readInput(String filename) {
        Scanner sc = null;
        ArrayList<String> diagnosticReport = new ArrayList<>();

        try {
            sc = new Scanner(new File(filename));
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
            dec += number[i] * Math.pow(2, number.length - i - 1);
        }
        return dec;
    }

    public static int part1() {
        ArrayList<String> input = readInput("data.txt");
        int[] gammaRate = new int[12];
        int[] epsilonRate = new int[12];

        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < gammaRate.length; j++) {
                gammaRate[j] += input.get(i).charAt(j) - 48;
            }
        }
        for (int i = 0; i < gammaRate.length; i++) {
            if (gammaRate[i] >= 500) {
                gammaRate[i] = 1;
                epsilonRate[i] = 0;
            } else {
                gammaRate[i] = 0;
                epsilonRate[i] = 1;
            }
        }

        return binToDec(gammaRate) * binToDec(epsilonRate);
    }

    public static int calculateOxygen() {
        ArrayList<String> input = readInput("data.txt");
        int amountOfOnes = 0;
        int amountOfZeros = 0;
        char dominantNumber = '0';

        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < input.size(); j++) {
                if (input.get(j).charAt(i) == '1')
                    amountOfOnes++;
                else
                    amountOfZeros++;
            }
            if (amountOfOnes >= amountOfZeros)
                dominantNumber = '1';
            else
                dominantNumber = '0';
            for (int j = input.size() - 1; j >= 0; j--) {
                if (input.get(j).charAt(i) != dominantNumber)
                    input.remove(j);
            }
            amountOfOnes = 0;
            amountOfZeros = 0;
            if (input.size() == 1)
                break;
        }
        return Integer.parseInt(input.get(0), 2);
    }

    public static int calculateCO2() {
        ArrayList<String> input = readInput("data.txt");
        int amountOfOnes = 0;
        int amountOfZeros = 0;
        char dominantNumber = '0';

        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < input.size(); j++) {
                if (input.get(j).charAt(i) == '1')
                    amountOfOnes++;
                else
                    amountOfZeros++;
            }
            if (amountOfOnes >= amountOfZeros)
                dominantNumber = '0';
            else
                dominantNumber = '1';
            for (int j = input.size() - 1; j >= 0; j--) {
                if (input.get(j).charAt(i) != dominantNumber)
                    input.remove(j);
            }
            amountOfOnes = 0;
            amountOfZeros = 0;
            if (input.size() == 1)
                break;
        }
        return Integer.parseInt(input.get(0), 2);
    }

    public static void main(String[] args) {
        System.out.println(part1());
        System.out.println(calculateOxygen() * calculateCO2());
    }
}