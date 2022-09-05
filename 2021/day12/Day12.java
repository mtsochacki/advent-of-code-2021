import java.io.File;
import java.util.*;

public class Day12 {
    static Map<String, ArrayList<String>> neighbourCaves = new HashMap<>();
    static Set<String> paths = new HashSet<>();
    static Set<String> pathsPartTwo = new HashSet<>();
    static HashMap<String, Integer> visitedCaves = new HashMap<>();
    static HashMap<String, Integer> visitedCavesPartTwo = new HashMap<>();

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

    static void findPath(String currentCave, String currentPath, HashMap<String, Integer> visitedCaves) {
        currentPath += "," + currentCave;

        if (currentCave.equals("end")) {
            paths.add(currentPath);
            return;
        }

        if (isSmallCave(currentCave)) {
            visitedCaves.put(currentCave, 1);
        }

        for (String neighbourCave : neighbourCaves.get(currentCave)) {
            if (!neighbourCave.equals("start") && !visitedCaves.containsKey(neighbourCave)) {
                findPath(neighbourCave, currentPath, new HashMap<>(visitedCaves));
            }
        }
    }

    static void findPathPartTwo(String currentCave, String currentPath, HashMap<String, Integer> visitedCavesPartTwo, boolean wasAnyCaveVisitedTwice) {
        currentPath += "," + currentCave;

        if (currentCave.equals("end")) {
            pathsPartTwo.add(currentPath);
            return;
        }

        if (isSmallCave(currentCave)) {
            if (visitedCavesPartTwo.containsKey(currentCave)) {
                visitedCavesPartTwo.put(currentCave, visitedCavesPartTwo.get(currentCave) + 1);
            } else {
                visitedCavesPartTwo.put(currentCave, 1);
            }

            if (visitedCavesPartTwo.get(currentCave) >= 2 && wasAnyCaveVisitedTwice) {
                return;
            }

            if (visitedCavesPartTwo.get(currentCave) == 2 && !wasAnyCaveVisitedTwice) {
                wasAnyCaveVisitedTwice = true;
            }
        }

        for (String neighbourNode : neighbourCaves.get(currentCave)) {
            if (!neighbourNode.equals("start") && visitedCavesPartTwo.getOrDefault(neighbourNode, 0) == 0
                    || visitedCavesPartTwo.getOrDefault(neighbourNode, 1) == 1 && !wasAnyCaveVisitedTwice
                    && !neighbourNode.equals("start")) {
                findPathPartTwo(neighbourNode, currentPath, new HashMap<>(visitedCavesPartTwo), wasAnyCaveVisitedTwice);
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
