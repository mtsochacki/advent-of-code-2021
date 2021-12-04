import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class day2 {
    static class Instruction {
        String command;
        int value;

        Instruction(String c, int v) {
            command = c;
            value = v;
        }
    }

    public static ArrayList<Instruction> readInput(String filename) {
        BufferedReader in;
        ArrayList<Instruction> instructions = new ArrayList<>();
        try {
            in = new BufferedReader(new FileReader(filename));
            String l;
            while ((l = in.readLine()) != null) {
                String[] parts = l.split(" ");
                instructions.add(new Instruction(parts[0], Integer.parseInt(parts[1])));
            }
        } catch (IOException e) {
            System.out.println("Something went horribly wrong " + e);
        }

        return instructions;
    }

    public static int part1() {
        int forward = 0;
        int vertical = 0;
        ArrayList<Instruction> instructions = readInput("data.txt");

        for (Instruction instruction: instructions) {
            switch (instruction.command) {
                case "forward":
                    forward += instruction.value;
                    break;
                case "down":
                    vertical -= instruction.value;
                    break;
                case "up":
                    vertical += instruction.value;
                    break;
                default:
                    break;
            }
        }

        return forward * Math.abs(vertical);
    }

    public static int part2() {
        int forward = 0;
        int aim = 0;
        int depth = 0;
        ArrayList<Instruction> instructions = readInput("data.txt");

        for (Instruction instruction: instructions) {
            switch (instruction.command) {
                case "forward":
                    forward += instruction.value;
                    depth += aim * instruction.value;
                    break;
                case "down":
                    aim -= instruction.value;
                    break;
                case "up":
                    aim += instruction.value;
                    break;
                default:
                    break;
            }
        }

        return forward * Math.abs(depth);
    }

    public static void main(String[] args) {
        System.out.println(part1());
        System.out.println(part2());
    }
}