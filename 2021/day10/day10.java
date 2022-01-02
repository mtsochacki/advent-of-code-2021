import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

public class day10 {
    public static class Scores {
        int corruptedScore;
        Long incompleteScore;

        public Scores(int x, Long y) {
            corruptedScore = x;
            incompleteScore = y;
        }
    }

    public static ArrayList<ArrayList<Character>> readInput(String filename) {
        ArrayList<ArrayList<Character>> linesOfChunks = new ArrayList<>();
        Scanner sc = null;
        try {
            sc = new Scanner(new File(filename));
            while (sc.hasNextLine()) {
                ArrayList<Character> line = new ArrayList<>();
                String s = sc.next();
                for (int i = 0; i < s.length(); i++) {
                    line.add(s.charAt(i));
                }
                linesOfChunks.add(line);
            }
            sc.close();
        } catch (Exception e) {
            System.out.println("Something went wrong" + e);
        }
        return linesOfChunks;
    }

    private static final HashMap<Character, Integer> openingScores = new HashMap<>();
    static {
        openingScores.put('(', 1);
        openingScores.put('[', 2);
        openingScores.put('{', 3);
        openingScores.put('<', 4);
    }

    private static final HashMap<Character, Integer> closingScores = new HashMap<>();
    static {
        closingScores.put(')', 3);
        closingScores.put(']', 57);
        closingScores.put('}', 1197);
        closingScores.put('>', 25137);
    }

    public static Scores scoreLine(ArrayList<Character> line) {
        int corruptedScore = 0;
        Long incompleteScore = 0L;
        Boolean isCorrupted = false;
        LinkedList<Character> stack = new LinkedList<>();
        for (Character syntaxChar : line) {
            if (openingScores.containsKey(syntaxChar)) {
                stack.addFirst(syntaxChar);
                /*
                 * ASCII codes for opening and closing pairs differ
                 * either by 1 ('(' and ')') or 2 (the others)
                 */
            } else if (stack.peek() == syntaxChar - 2
                    || stack.peek() == syntaxChar - 1) {
                stack.removeFirst();
            } else {
                isCorrupted = true;
                if (closingScores.containsKey(syntaxChar)) {
                    corruptedScore += closingScores.get(syntaxChar);
                    break;
                }
            }
        }
        if (!isCorrupted) {
            for (Character element : stack) {
                incompleteScore = incompleteScore * 5 + openingScores.get(element);
            }
        }
        Scores score = new Scores(corruptedScore, incompleteScore);
        return score;
    }

    public static Scores syntaxScoring() {
        ArrayList<ArrayList<Character>> linesOfChunks = readInput("data.txt");
        int totalCorruptedScore = 0;
        ArrayList<Long> allScores = new ArrayList<>();
        for (ArrayList<Character> line : linesOfChunks) {
            Scores score = scoreLine(line);
            totalCorruptedScore += score.corruptedScore;
            if (!score.incompleteScore.equals(0L)) {
                allScores.add(score.incompleteScore);
            }
        }
        Collections.sort(allScores);
        Scores result = new Scores(totalCorruptedScore, allScores.get(allScores.size() / 2));
        return result;
    }

    public static void main(String[] args) {
        System.out.println("Part1 = " + syntaxScoring().corruptedScore +
                " Part2 = " + syntaxScoring().incompleteScore);
    }
}