import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day25 {
    static ArrayList<ArrayList<String>> deepCopyArrayList(ArrayList<ArrayList<String>> source){
        ArrayList<ArrayList<String>> destination = new ArrayList<>();
        for (ArrayList<String> strings : source) {
            destination.add((ArrayList<String>) strings.clone());
        }
        return destination;
    }
    static ArrayList<ArrayList<String>> readCucumberMap(String filename) {
        Scanner sc;
        try {
            sc = new Scanner(new File(filename));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        ArrayList<String> cucumberLines = new ArrayList<>();
        while (sc.hasNextLine()) {
            cucumberLines.add(sc.nextLine());
        }
        sc.close();
        ArrayList<ArrayList<String>> cucumberMap = new ArrayList<>();
        for (String line : cucumberLines) {
            ArrayList<String> splitCucumbers = new ArrayList<>(Arrays.asList(line.split("")));
            cucumberMap.add(splitCucumbers);
        }
        return cucumberMap;
    }

    public static void main(String[] args) {
        ArrayList<ArrayList<String>> firstMap = readCucumberMap("/Users/mateusz/Java/advent-of-code/2021/day25/data.txt");
        int mapLength = firstMap.size();
        int lineLength = firstMap.get(0).size();
        int steps = 0;
        boolean hasMoved = true;
        while (hasMoved) {
            ArrayList<ArrayList<String>> secondMap = deepCopyArrayList(firstMap);
            hasMoved = false;
            steps++;
            for (int r = 0; r < mapLength; r++) {
                for (int c = 0; c < lineLength; c++) {
                    if (firstMap.get(r).get(c).equals(">") && firstMap.get(r).get((c + 1) % lineLength).equals(".")) {
                        secondMap.get(r).set(c, ".");
                        secondMap.get(r).set((c + 1) % lineLength, ">");
                        hasMoved = true;
                    }
                }
            }
            ArrayList<ArrayList<String>> thirdMap = deepCopyArrayList(secondMap);
            for (int r = 0; r < mapLength; r++) {
                for (int c = 0; c < lineLength; c++) {
                    if (secondMap.get(r).get(c).equals("v") && secondMap.get((r + 1) % mapLength).get(c).equals(".")) {
                        thirdMap.get((r + 1) % mapLength).set(c, "v");
                        thirdMap.get(r).set(c, ".");
                        hasMoved = true;
                    }
                }
            }
            firstMap = thirdMap;
        }
        System.out.println(steps);
    }
}