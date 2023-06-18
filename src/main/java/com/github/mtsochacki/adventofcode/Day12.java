package com.github.mtsochacki.adventofcode;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.*;

@Slf4j
public class Day12 implements Day {
    public static final String START = "start";
    static Map<String, ArrayList<String>> neighbourCaves = new HashMap<>();
    static Set<String> paths = new HashSet<>();
    static Set<String> pathsPartTwo = new HashSet<>();
    static Set<String> visitedCaves = new HashSet<>();
    static Set<String> visitedCavesPartTwo = new HashSet<>();

    static boolean isSmallCave(String cave) {
        return cave.equals(cave.toLowerCase());
    }

    static void readInput() {
        try (Scanner sc = new Scanner(new File("data.txt"))) {
            while (sc.hasNextLine()) {
                String[] caves = sc.nextLine().split("-");
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
            }
        } catch (Exception e) {
            log.error("Something went horribly wrong: {}", e.getMessage());
        }
    }

    static void findPath(String currentCave, String currentPath, Set<String> visitedCaves) {
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

    static void findPathPartTwo(String currentCave, String currentPath, Set<String> visitedCavesPartTwo, boolean wasAnyCaveVisitedTwice) {
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
        findPath(START, "", visitedCaves);
        return String.valueOf(paths.size());
    }

    @Override
    public String part2(List<String> input) {
        findPathPartTwo(START, "", visitedCavesPartTwo, false);
        return String.valueOf(pathsPartTwo.size());
    }
}