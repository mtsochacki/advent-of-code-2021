package com.aoc2021;

import java.io.File;
import java.util.*;

public class Day12 implements Day {
    private static Map<String, ArrayList<String>> neighbourCaves = new HashMap<>();
    private static Set<String> paths = new HashSet<>();
    private static Set<String> pathsPartTwo = new HashSet<>();
    private static Set<String> visitedCaves = new HashSet<>();
    private static Set<String> visitedCavesPartTwo = new HashSet<>();

    private boolean isSmallCave(String cave) {
        return cave.equals(cave.toLowerCase());
    }

    private void readInput(String filename) {
        try (Scanner sc = new Scanner(new File(filename))) {
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
            System.out.println("Something went horribly wrong: " + e);
        }
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
            if (!neighbourCave.equals("start") && !visitedCaves.contains(neighbourCave)) {
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
            if (!neighbourNode.equals("start") && (!visitedCavesPartTwo.contains(neighbourNode)
                    || visitedCavesPartTwo.contains(neighbourNode) && !wasAnyCaveVisitedTwice)) {
                findPathPartTwo(neighbourNode, currentPath, new HashSet<>(visitedCavesPartTwo), wasAnyCaveVisitedTwice);
            }
        }
    }

    public String part1(String filename) {
        readInput(filename);
        findPath("start", "", visitedCaves);
        return String.valueOf(paths.size());
    }

    public String part2(String filename) {
        readInput(filename);
        findPathPartTwo("start", "", visitedCavesPartTwo, false);
        return String.valueOf(pathsPartTwo.size());
    }
}