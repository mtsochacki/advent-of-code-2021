package com.github.mtsochacki.adventofcode;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class Day25 implements Day {

    public static final String EMPTY_SPACE = ".";

    @Override
    public String part1(List<String> input) {
        List<List<String>> cucumberMap = readCucumberMap(input);
        int mapLength = cucumberMap.size();
        int lineLength = cucumberMap.get(0).size();
        int steps = 0;
        boolean hasMoved;
        do {
            List<List<String>> mapAfterMovingEase = deepCopyArrayList(cucumberMap);
            hasMoved = false;
            steps++;
            for (int y = 0; y < mapLength; y++) {
                for (int x = 0; x < lineLength; x++) {
                    if (canMoveEast(cucumberMap, lineLength, y, x)) {
                        mapAfterMovingEase.get(y).set(x, EMPTY_SPACE);
                        mapAfterMovingEase.get(y).set((x + 1) % lineLength, ">");
                        hasMoved = true;
                    }
                }
            }
            List<List<String>> mapAfterMovingSouth = deepCopyArrayList(mapAfterMovingEase);
            for (int y = 0; y < mapLength; y++) {
                for (int x = 0; x < lineLength; x++) {
                    if (canMoveSouth(mapLength, mapAfterMovingEase, y, x)) {
                        mapAfterMovingSouth.get((y + 1) % mapLength).set(x, "v");
                        mapAfterMovingSouth.get(y).set(x, EMPTY_SPACE);
                        hasMoved = true;
                    }
                }
            }
            cucumberMap = mapAfterMovingSouth;
        } while (hasMoved);
        return String.valueOf(steps);
    }

    private boolean canMoveSouth(int mapLength, List<List<String>> mapAfterMovingEase, int y, int x) {
        return "v".equals(mapAfterMovingEase.get(y).get(x)) && EMPTY_SPACE.equals(mapAfterMovingEase.get((y + 1) % mapLength).get(x));
    }

    private boolean canMoveEast(List<List<String>> firstMap, int lineLength, int y, int x) {
        return ">".equals(firstMap.get(y).get(x)) && EMPTY_SPACE.equals(firstMap.get(y).get((x + 1) % lineLength));
    }

    @Override
    public String part2(List<String> input) {
        return "There is no part2 for day 25.";
    }

    private List<List<String>> readCucumberMap(List<String> input) {
        return input.stream().map(line -> Arrays.stream(line.split("")).toList()).toList();
    }

    private List<List<String>> deepCopyArrayList(List<List<String>> source) {
        List<List<String>> destination = new ArrayList<>();
        for (List<String> sourceLine : source) {
            destination.add(new ArrayList<>(sourceLine));
        }
        return destination;
    }
}