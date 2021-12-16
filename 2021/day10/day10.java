import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

public class day10 {
    public static class Scores {
        int totalSyntaxScore;
        Long middleScore;

        public Scores(int x, Long y) {
            totalSyntaxScore = x;
            middleScore = y;
        }
    }

    public static ArrayList<ArrayList<Character>> readInput(String filename) {
        ArrayList<ArrayList<Character>> input = new ArrayList<>();
        Scanner sc = null;
        try {
            sc = new Scanner(new File(filename));
            while (sc.hasNextLine()) {
                ArrayList<Character> line = new ArrayList<>();
                String s = sc.next();
                for (int i = 0; i < s.length(); i++) {
                    line.add(s.charAt(i));
                }
                input.add(line);
            }
        } catch (Exception e) {
            System.out.println("Something went wrong" + e);
        } finally {
            sc.close();
        }
        return input;
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

    public static Scores syntaxScoring() {
        ArrayList<ArrayList<Character>> input = readInput("data.txt");
        ArrayList<Long> autocompleteScores = new ArrayList<>();
        ArrayList<Character> openingChars =
        new ArrayList<Character>(Arrays.asList('<', '(', '[', '{'));
        ArrayList<Character> closingChars =
        new ArrayList<Character>(Arrays.asList('>', ')', ']', '}'));
        int syntaxScore = 0;
        for (ArrayList<Character> line : input) {
            LinkedList<Character> stack = new LinkedList<>();
            Boolean isCorrupted = false;
            long autoTmpScore = 0;
            for (Character syntaxChar : line) {
                if (openingChars.contains(syntaxChar)) {
                    stack.addFirst(syntaxChar);
                } else if (stack.peek() == syntaxChar - 2
                        || stack.peek() == syntaxChar - 1) {
                    stack.removeFirst();
                } else {
                    isCorrupted = true;
                    if (closingChars.contains(syntaxChar)) {
                        syntaxScore += closingScores.get(syntaxChar);
                        break;
                    }
                }
            }
            if (!isCorrupted) {
                for (Character element : stack) {
                    autoTmpScore = autoTmpScore * 5 + openingScores.get(element);
                }
                autocompleteScores.add(autoTmpScore);
            }
        }
        Collections.sort(autocompleteScores);
        Scores result = new Scores(syntaxScore, autocompleteScores.get(autocompleteScores.size() / 2));
        return result;
    }

    public static void main(String[] args) {
        System.out.println("Part1 = " + syntaxScoring().totalSyntaxScore + " Part2 = " + syntaxScoring().middleScore);
    }
}