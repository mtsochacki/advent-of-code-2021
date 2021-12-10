import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class day07 {
    public static ArrayList<Integer> readInput(String filename) {
        ArrayList<Integer> input = new ArrayList<>();
        Scanner sc = null;
        try {
            sc = new Scanner(new File(filename)).useDelimiter(",");
            while (sc.hasNextInt()) {
                input.add(sc.nextInt());
            }
        } catch (Exception e) {
            System.out.println("Something went horribly wrong" + e);
        } finally {
            sc.close();
        }
        return input;
    }

    public static double arithmeticSequence(double start, double end) {
        return (Math.abs(end - start)) * (Math.abs(end - start) + 1) / 2;
    }

    public static void part1() {
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

        System.out.println("We need " + totalFuel + " fuel to move all the crabs to position " + median + ".");
    }

    public static void part2() {
        ArrayList<Integer> listOfPositions = readInput("data.txt");
        int fuel = 100000000;
        // Chceck each point between min and max of all the positions
        int max = Collections.max(listOfPositions);
        int min = Collections.min(listOfPositions);
        for (int testedAlignment = min; testedAlignment <= max; testedAlignment++) {
            int currentFuel = 0;
            for (Integer position : listOfPositions)
                currentFuel += arithmeticSequence(position, testedAlignment);
            if (currentFuel < fuel)
                fuel = currentFuel;
        }
        System.out.println("We need " + fuel + " fuel to move the crabs where they need to go.");
    }

    public static void main(String[] args) {
        part1();
        part2();
    }
}