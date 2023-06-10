package com.github.mtsochacki.advent_of_code;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Slf4j
public class Day02 implements Day {
    private class Command {
        String direction;
        int value;

        Command(String direction, int value) {
            this.direction = direction;
            this.value = value;
        }
    }

    private List<Command> readCommands(String filename) {
        List<Command> listOfCommands = new ArrayList<>();
        try (Scanner sc = new Scanner(new File(filename))) {
            while (sc.hasNextLine()) {
                listOfCommands.add(new Command(sc.next(), sc.nextInt()));
            }
        } catch (Exception e) {
            log.error("Something went horribly wrong: {}", e.getMessage());
        }
        return listOfCommands;
    }

    public String part1() {
        List<Command> commands = readCommands("data.txt");
        int forward = 0;
        int vertical = 0;

        for (Command command : commands) {
            switch (command.direction) {
                case "forward" -> forward += command.value;
                case "down" -> vertical += command.value;
                case "up" -> vertical -= command.value;
                default -> log.error("Unsupported direction: {}", command.direction);
            }
        }
        return String.valueOf(forward * vertical);
    }

    public String part2() {
        List<Command> commands = readCommands("data.txt");
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
                default -> log.error("Unsupported direction: {}", command.direction);
            }
        }
        return String.valueOf(forward * depth);
    }
}