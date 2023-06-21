package com.github.mtsochacki.adventofcode;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
public class Day12 implements Day {
    private static final String START = "start";
    private static Map<String, ArrayList<String>> neighbourCaves = new HashMap<>();
    private static Set<String> paths = new HashSet<>();
    private static Set<String> pathsPartTwo = new HashSet<>();
    private static Set<String> visitedCaves = new HashSet<>();
    private static Set<String> visitedCavesPartTwo = new HashSet<>();

    private boolean isSmallCave(String cave) {
        return cave.equals(cave.toLowerCase());
    }

    private void readInput(List<String> input) {
        input.forEach(line -> {
            String[] caves = line.split("-");
            String firstCave = caves[0];
            String secondCave = caves[1];

            if (neighbourCaves.containsKey(firstCave)) {
                neighbourCaves.get(firstCave).add(secondCave);
            } else {
                neighbourCaves.put(firstCave, new ArrayList<>(List.of(secondCave)));
            }

            if (neighbourCaves.containsKey(secondCave)) {
                neighbourCaves.get(secondCave).add(firstCave);
            } else {
                neighbourCaves.put(secondCave, new ArrayList<>(List.of(firstCave)));
            }
        });
    }

    private void findPath(String currentCave, String currentPath, Set<String> visitedCaves) {
        currentPath += "," + currentCave;

        if (currentCave.equals("end")) {
            paths.add(currentPath);
            return;
        }

        if (isSmallCave(currentCave)) {
            visitedCaves.add(currentCave);
        }

        for (String neighbourCave : neighbourCaves.get(currentCave)) {
            if (!neighbourCave.equals(START) && !visitedCaves.contains(neighbourCave)) {
                findPath(neighbourCave, currentPath, new HashSet<>(visitedCaves));
            }
        }
    }

    private void findPathPartTwo(String currentCave, String currentPath, Set<String> visitedCavesPartTwo, boolean wasAnyCaveVisitedTwice) {
        currentPath += "," + currentCave;

        if (currentCave.equals("end")) {
            pathsPartTwo.add(currentPath);
            return;
        }

        if (isSmallCave(currentCave)) {
            if (visitedCavesPartTwo.contains(currentCave) && !wasAnyCaveVisitedTwice) {
                wasAnyCaveVisitedTwice = true;
            } else if (visitedCavesPartTwo.contains(currentCave) && wasAnyCaveVisitedTwice) {
                return;
            } else {
                visitedCavesPartTwo.add(currentCave);
            }
        }

        for (String neighbourNode : neighbourCaves.get(currentCave)) {
            if (!neighbourNode.equals(START) && (!visitedCavesPartTwo.contains(neighbourNode)
                    || visitedCavesPartTwo.contains(neighbourNode) && !wasAnyCaveVisitedTwice)) {
                findPathPartTwo(neighbourNode, currentPath, new HashSet<>(visitedCavesPartTwo), wasAnyCaveVisitedTwice);
            }
        }
    }

    @Override
    public String part1(List<String> input) {
        readInput(input);
        findPath(START, "", visitedCaves);
        return String.valueOf(paths.size());
    }

    @Override
    public String part2(List<String> input) {
        readInput(input);
        findPathPartTwo(START, "", visitedCavesPartTwo, false);
        return String.valueOf(pathsPartTwo.size());
    }
}