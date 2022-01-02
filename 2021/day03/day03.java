import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class day03 {
    public static ArrayList<String> readReport(String filename) {
        Scanner sc;
        ArrayList<String> diagnosticReport = new ArrayList<>();

        try {
            sc = new Scanner(new File(filename));
            while (sc.hasNext()) {
                diagnosticReport.add(sc.next());
            }
            sc.close();
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

    public static int calculatePower() {
        ArrayList<String> report = readReport("data.txt");
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

    public static int calculateSupport(boolean isOxygen) {
        ArrayList<String> report = readReport("data.txt");
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

    public static void main(String[] args) {
        System.out.println(calculatePower());
        System.out.println(calculateSupport(true) * calculateSupport(false));
    }
}