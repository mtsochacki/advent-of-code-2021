package com.github.mtsochacki.adventofcode;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class Day02 implements Day {
    private record Command(String direction, int value) {
    }

    @Override
    public String part1(List<String> input) {
        List<Command> commands = transformIntoCommands(input);
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
        List<Command> commands = transformIntoCommands(input);
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

    private List<Command> transformIntoCommands(List<String> input) {
        return input.stream().map(line -> {
            String[] tokens = line.split(" ");
            return new Command(tokens[0], Integer.parseInt(tokens[1]));
        }).toList();
    }
}