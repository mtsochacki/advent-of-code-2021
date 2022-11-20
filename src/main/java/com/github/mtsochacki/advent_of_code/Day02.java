package com.github.mtsochacki.advent_of_code;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Day02 implements Day {
    private static class Command {
        String direction;
        int value;

        Command(String d, int v) {
            direction = d;
            value = v;
        }
    }

    private static ArrayList<Command> readCommands(String filename) {
        ArrayList<Command> listOfCommands = new ArrayList<>();
        try (Scanner sc = new Scanner(new File(filename))){
            while (sc.hasNextLine()) {
                listOfCommands.add(new Command(sc.next(), sc.nextInt()));
            }
        } catch (Exception e) {
            System.out.println("Something went horribly wrong: " + e);
        }
        return listOfCommands;
    }

    public String part1(String filename) {
        ArrayList<Command> commands = readCommands(filename);
        int forward = 0;
        int vertical = 0;

        for (Command command : commands) {
            switch (command.direction) {
                case "forward" -> forward += command.value;
                case "down" -> vertical += command.value;
                case "up" -> vertical -= command.value;
                default -> {}
            }
        }
        return String.valueOf(forward * vertical);
    }

    public String part2(String filename) {
        ArrayList<Command> commands = readCommands(filename);
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
        return String.valueOf(forward * depth);
    }
}