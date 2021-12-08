import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class day08 {
    public static ArrayList<String[]> readInput(String filename) {
        ArrayList<String[]> input = new ArrayList<>();

        Scanner sc = null;
        try {
            sc = new Scanner(new File(filename));
            while (sc.hasNextLine()) {
                String[] line = new String[14];
                line = sc.nextLine().split(" \\Q|\\E | ");
                input.add(line);
            }
        } catch (Exception e) {
            System.out.println("Something went wrong" + e);
        } finally {
            sc.close();
        }
        return input;
    }

    public static int part1() {
        int counter = 0;
        ArrayList<String[]> input = readInput("data.txt");
        for (int i = 0; i < input.size(); i++) {
            for (int j = 10; j < input.get(0).length; j++) {
                if (input.get(i)[j].length() == 2 || input.get(i)[j].length() == 4 || input.get(i)[j].length() == 3
                        || input.get(i)[j].length() == 7)
                    counter++;
            }
        }
        return counter;
    }

    public static void main(String[] args) {
        System.out.println(part1());
    }
}