package com.github.mtsochacki.adventofcode;

import java.io.File;
import java.util.*;

public class Day12 {
    static Map<String, ArrayList<String>> neighbourCaves = new HashMap<>();
    static Set<String> paths = new HashSet<>();
    static Set<String> pathsPartTwo = new HashSet<>();
    static Set<String> visitedCaves = new HashSet<>();
    static Set<String> visitedCavesPartTwo = new HashSet<>();

    static boolean isSmallCave(String cave) {
        return cave.equals(cave.toLowerCase());
    }

    static void readInput() {
        Scanner sc;
        try {
            sc = new Scanner(new File("data.txt"));
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
            sc.close();
        } catch (Exception e) {
            System.out.println("Something went horribly wrong: " + e);
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
            if (!neighbourCave.equals("start") && !visitedCaves.contains(neighbourCave)) {
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
            if (!neighbourNode.equals("start") && (!visitedCavesPartTwo.contains(neighbourNode)
                    || visitedCavesPartTwo.contains(neighbourNode) && !wasAnyCaveVisitedTwice)) {
                findPathPartTwo(neighbourNode, currentPath, new HashSet<>(visitedCavesPartTwo), wasAnyCaveVisitedTwice);
            }
        }
    }

    static int partOne() {
        findPath("start", "", visitedCaves);
        return paths.size();
    }

    static int partTwo() {
        findPathPartTwo("start", "", visitedCavesPartTwo, false);
        return pathsPartTwo.size();
    }

    public static void main(String[] args) {
        readInput();
        System.out.println(partOne());
        System.out.println(partTwo());
    }
}