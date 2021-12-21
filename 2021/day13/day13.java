import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class day13 {
    public static class Instruction {
        int line;
        String dimension;
    }

    public static ArrayList<ArrayList<Integer>> readCoordinates(String filename) {
        ArrayList<ArrayList<Integer>> coordinates = new ArrayList<>();
        Scanner sc = null;
        try {
            sc = new Scanner(new File(filename));
            sc.useDelimiter(",|\n");
            while (sc.hasNextInt()) {
                ArrayList<Integer> line = new ArrayList<>();
                for (int j = 0; j < 2; j++) {
                    line.add(sc.nextInt());
                }
                coordinates.add(line);
            }
        } catch (Exception e) {
            System.out.println("Something went wrong " + e);
        } finally {
            sc.close();
        }
        return coordinates;
    }

    public static ArrayList<Instruction> readInstructions(String filename) {
        ArrayList<Instruction> instructions = new ArrayList<>();
        Scanner sc = null;
        try {
            sc = new Scanner(new File(filename));
            while (sc.nextLine().length() != 0) {
            }
            sc.useDelimiter("fold along |=|\\n");
            while (sc.hasNextLine()) {
                Instruction instruction = new Instruction();
                instruction.dimension = sc.next();
                instruction.line = sc.nextInt();
                if (sc.hasNextLine()) {
                    sc.nextLine();
                }
                instructions.add(instruction);
            }
        } catch (Exception e) {
            System.out.println("Something went horribly wrong " + e);
        } finally {
            sc.close();
        }
        return instructions;
    }

    public static ArrayList<ArrayList<Integer>> foldOnce(
            ArrayList<ArrayList<Integer>> input, int x, String isX) {
        ArrayList<ArrayList<Integer>> output = new ArrayList<>();
        int z = (isX.equals("x")) ? 0 : 1;
        for (ArrayList<Integer> line : input) {
            if (line.get(z) > x) {
                int distance = line.get(z) - x;
                line.set(z, x - distance);
                output.add(line);
            } else if (line.get(z) < x) {
                output.add(line);
            }
        }

        Set<ArrayList<Integer>> set = new HashSet<>(output);
        output.clear();
        output.addAll(set);
        return output;
    }

    public static void part1() {
        ArrayList<ArrayList<Integer>> input = readCoordinates("data.txt");
        ArrayList<Instruction> instructions = readInstructions("data.txt");
        input = foldOnce(input, instructions.get(0).line,
                            instructions.get(0).dimension);
        System.out.println("There are " + input.size()
                            + " dots visible after the first fold");
    }

    public static void part2() {
        ArrayList<ArrayList<Integer>> input = readCoordinates("data.txt");
        ArrayList<Instruction> instructions = readInstructions("data.txt");
        for (Instruction instruction : instructions) {
            input = foldOnce(input, instruction.line, instruction.dimension);
        }
        System.out.println("\033[2J"); // clears screen
        for (ArrayList<Integer> line : input) {
            int row = line.get(1) + 1;
            int column = line.get(0) + 1;
            System.out.print(String.format("%c[%d;%dH#", 0x1B, row, column));
        }
        System.out.print(String.format("%c[%d;%dH", 0x1B, 8, 0));
    }

    public static void main(String[] args) {
        part2();
        part1();
    }
}