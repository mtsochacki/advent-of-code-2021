import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class day06 {
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
    // Part2 is also a solution to part1, but this is going
    // to stay here as a testimony on how not to solve
    // such problems
    public static int part1() {
        ArrayList<Integer> input = readInput("data.txt");
        int size = 0;
        // for each day...
        for (int i = 0; i < 80; i++) {
            // ... and for each fish...
            size = input.size();
            for (int j = 0; j < size; j++) {
                // ...decrease the timer...
                input.set(j, (input.get(j)) - 1);
                // ...and check if new fish is born...
                if (input.get(j) == -1) {
                    input.set(j, 6);
                    input.add(8);
                }
            }
        }
        return input.size();
    }

    public static long part2() {
        ArrayList<Integer> listOfFish = readInput("data.txt");
        long[] popNum = new long[9];
        for (Integer fish : listOfFish) {
            popNum[fish] += 1;
        }
        long tmp = 0;
        for (int i = 0; i < 256; i++) {
            tmp = popNum[0];
            popNum[0] = popNum[1];
            popNum[1] = popNum[2];
            popNum[2] = popNum[3];
            popNum[3] = popNum[4];
            popNum[4] = popNum[5];
            popNum[5] = popNum[6];
            popNum[6] = popNum[7] + tmp;
            popNum[7] = popNum[8];
            popNum[8] = tmp;
        }
        return popNum[0] + popNum[1] + popNum[2] + popNum[3]
                + popNum[4] + popNum[5] + popNum[6] + popNum[7]
                + popNum[8];
    }

    public static void main(String[] args) {
        System.out.println(part1());
        System.out.println(part2());
    }
}