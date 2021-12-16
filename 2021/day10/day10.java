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

    public static HashMap<Character, Integer> initiateScores() {
        HashMap<Character, Integer> map = new HashMap<>();
        map.put(')', 3);
        map.put(']', 57);
        map.put('}', 1197);
        map.put('>', 25137);
        map.put('(', 1);
        map.put('[', 2);
        map.put('{', 3);
        map.put('<', 4);
        return map;
    }

    public static Scores syntaxScoring() {
        ArrayList<ArrayList<Character>> input = readInput("data.txt");
        ArrayList<Long> autocompleteScores = new ArrayList<>();
        ArrayList<Character> openingChar = new ArrayList<Character>(Arrays.asList('<', '(', '[', '{'));
        ArrayList<Character> closingChar = new ArrayList<Character>(Arrays.asList('>', ')', ']', '}'));
        HashMap<Character, Integer> charScore = initiateScores();
        int syntaxScore = 0;
        for (ArrayList<Character> line : input) {
            LinkedList<Character> stack = new LinkedList<>();
            Boolean isCorrupted = false;
            long autoTmpScore = 0;
            for (Character syntaxChar : line) {
                if (openingChar.contains(syntaxChar)) {
                    stack.addFirst(syntaxChar);
                } else if (stack.peek() == syntaxChar - 2 || stack.peek() == syntaxChar - 1) {
                    stack.removeFirst();
                } else {
                    isCorrupted = true;
                    if (closingChar.contains(syntaxChar)) {
                        syntaxScore += charScore.get(syntaxChar);
                        break;
                    }
                }
            }
            if (!isCorrupted) {
                for (Character element : stack) {
                    autoTmpScore = autoTmpScore * 5 + charScore.get(element);
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