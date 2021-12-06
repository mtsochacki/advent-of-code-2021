import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class day02 {
    public static class Command {
        String direction = null;
        int value = 0;

        Command(String d, int v) {
            direction = d;
            value = v;
        }
    }

    public static ArrayList<Command> readInput(String filename) {
        Scanner sc = null;
        ArrayList<Command> listOfCommands = new ArrayList<>();

        try {
            sc = new Scanner(new File(filename));
            while (sc.hasNextLine()) {
                listOfCommands.add(new Command(sc.next(), sc.nextInt()));
            }
        } catch (Exception e) {
            System.out.println("Something went horribly wrong: " + e);
        }

        return listOfCommands;
    }

    public static int part1() {
        ArrayList<Command> input = readInput("data.txt");
        int forward = 0;
        int vertical = 0;

        for (int i = 0; i < input.size(); i++) {
            switch (input.get(i).direction) {
                case "forward":
                    forward += input.get(i).value;
                    break;
                case "down":
                    vertical += input.get(i).value;
                    break;
                case "up":
                    vertical -= input.get(i).value;
                    break;
                default:
                    break;
            }
        }

        return forward * vertical;
    }

    public static int part2() {
        int result = 0;
        int forward = 0;
        int aim = 0;
        int depth = 0;
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader("data.txt"));
            String l;
            while ((l = in.readLine()) != null) {
                String[] parts = l.split(" ");
                switch (parts[0]) {
                    case "forward":
                        forward = forward + Integer.parseInt(parts[1]);
                        depth = depth + aim * Integer.parseInt(parts[1]);
                        break;
                    case "down":
                        aim = aim + Integer.parseInt(parts[1]);
                        break;
                    case "up":
                        aim = aim - Integer.parseInt(parts[1]);
                        break;
                    default:
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println("Something went horribly wrong");
        }
        result = forward * depth;
        return result;
    }

    public static void main(String[] args) {
        System.out.println(part1());
        System.out.println(part2());
    }
}