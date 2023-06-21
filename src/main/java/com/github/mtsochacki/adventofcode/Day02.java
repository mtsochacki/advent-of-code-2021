package com.github.mtsochacki.adventofcode;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Day02 implements Day {
    private class Command {
        private final String direction;
        private final int value;

        Command(String direction, int value) {
            this.direction = direction;
            this.value = value;
        }
    }

    private List<Command> readCommands(List<String> input) {
        List<Command> listOfCommands = new ArrayList<>();
        for (String line : input) {
            String[] tokens = line.split(" ");
            listOfCommands.add(new Command(tokens[0], Integer.parseInt(tokens[1])));
        }
        return listOfCommands;
    }

    @Override
    public String part1(List<String> input) {
        List<Command> commands = readCommands(input);
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

    @Override
    public String part2(List<String> input) {
        List<Command> commands = readCommands(input);
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