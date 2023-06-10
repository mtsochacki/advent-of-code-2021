package com.github.mtsochacki.advent_of_code;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Day02 {
    public static class Command {
        String direction;
        int value;

        Command(String d, int v) {
            direction = d;
            value = v;
        }
    }

    public static ArrayList<Command> readCommands(String filename) {
        Scanner sc;
        ArrayList<Command> listOfCommands = new ArrayList<>();
        try {
            sc = new Scanner(new File(filename));
            while (sc.hasNextLine()) {
                listOfCommands.add(new Command(sc.next(), sc.nextInt()));
            }
            sc.close();
        } catch (Exception e) {
            System.out.println("Something went horribly wrong: " + e);
        }
        return listOfCommands;
    }

    public static int calculatePosition() {
        ArrayList<Command> commands = readCommands("data.txt");
        int forward = 0;
        int vertical = 0;

        for (Command command : commands) {
            switch (command.direction) {
                case "forward" -> forward += command.value;
                case "down" -> vertical += command.value;
                case "up" -> vertical -= command.value;
                default -> {
                }
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
                case "forward" -> {
                    forward += command.value;
                    depth += aim * command.value;
                }
                case "down" -> aim += command.value;
                case "up" -> aim -= command.value;
                default -> {}
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