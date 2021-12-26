import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class day02 {
    public static class Command {
        String direction;
        int value;

        Command(String d, int v) {
            direction = d;
            value = v;
        }
    }

    public static ArrayList<Command> readCommands(String filename) {
        Scanner sc = null;
        ArrayList<Command> listOfCommands = new ArrayList<>();
        try {
            sc = new Scanner(new File(filename));
            while (sc.hasNextLine()) {
                listOfCommands.add(new Command(sc.next(), sc.nextInt()));
            }
        } catch (Exception e) {
            System.out.println("Something went horribly wrong: " + e);
        } finally {
            sc.close();
        }
        return listOfCommands;
    }

    public static int calculatePosition() {
        ArrayList<Command> commands = readCommands("data.txt");
        int forward = 0;
        int vertical = 0;

        for (Command command : commands) {
            switch (command.direction) {
                case "forward":
                    forward += command.value;
                    break;
                case "down":
                    vertical += command.value;
                    break;
                case "up":
                    vertical -= command.value;
                    break;
                default:
                    break;
            }
        }
        return forward * vertical;
    }

    public static int calculateAdvancedPosition() {
        ArrayList<Command> commands = readCommands("data.txt");
        int forward = 0;
        int aim = 0;
        int depth = 0;

        for (Command command : commands) {
            switch (command.direction) {
                case "forward":
                    forward += command.value;
                    depth += aim * command.value;
                    break;
                case "down":
                    aim += command.value;
                    break;
                case "up":
                    aim -= command.value;
                    break;
                default:
                    break;
            }
        }
        return forward * depth;
    }

    public static void main(String[] args) {
        System.out.println("Position before reading the submarine manual: "
                + calculatePosition());
        System.out.println("Position after reading the submarine manual: "
                + calculateAdvancedPosition());
    }
}