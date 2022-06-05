import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day25 {
    static ArrayList<ArrayList<String>> readCucumberMap(String filename) {
        Scanner sc;
        try {
            sc = new Scanner(new File(filename));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        ArrayList<String> list = new ArrayList<>();
        while (sc.hasNextLine()) {
            list.add(sc.nextLine());
        }
        sc.close();
        ArrayList<ArrayList<String>> cucumberMap = new ArrayList<>();
        for (String line : list) {
            ArrayList<String> splitLine2 = new ArrayList<>(Arrays.asList(line.split("")));
            cucumberMap.add(splitLine2);
        }
        return cucumberMap;
    }

    public static void main(String[] args) {
        ArrayList<ArrayList<String>> input1 = readCucumberMap("/Users/mateusz/Java/advent-of-code/2021/day25/data.txt");
        int mapLength = input1.size();
        int lineLength = input1.get(0).size();
        int steps = 0;
        while (true) {
            ArrayList<ArrayList<String>> newInput = new ArrayList<>();

            for (ArrayList<String> strings : input1) {
                newInput.add((ArrayList<String>) strings.clone());
            }
            boolean hasMoved = false;
            steps++;
            for (int r = 0; r < mapLength; r++) {
                for (int c = 0; c < lineLength; c++) {
                    if (input1.get(r).get(c).equals(">") && input1.get(r).get((c + 1) % lineLength).equals(".")) {
                        newInput.get(r).set(c, ".");
                        newInput.get(r).set((c + 1) % lineLength, ">");
                        hasMoved = true;
                    }
                }
            }
            ArrayList<ArrayList<String>> lastInput = new ArrayList<>();
            for (ArrayList<String> strings : newInput) {
                lastInput.add((ArrayList<String>) strings.clone());
            }
            for (int r = 0; r < mapLength; r++) {
                for (int c = 0; c < lineLength; c++) {
                    if (newInput.get(r).get(c).equals("v") && newInput.get((r + 1) % mapLength).get(c).equals(".")) {
                        lastInput.get((r + 1) % mapLength).set(c, "v");
                        lastInput.get(r).set(c, ".");
                        hasMoved = true;
                    }
                }
            }
            if (!hasMoved) {
                System.out.println(steps);
                break;
            }
            input1 = lastInput;
        }
    }
}
