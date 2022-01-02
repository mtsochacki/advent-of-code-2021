import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

public class day10 {
    public static class Chunk {
        long score;
        boolean isCorrupted;
        private String lineStr;

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

        Chunk(String line) {
            this.lineStr = line;
            this.parseLine();
        }

        void parseLine() {
            LinkedList<Character> stack = new LinkedList<>();
            for (char c : lineStr.toCharArray()) {
                if (openingScores.containsKey(c)) {
                    stack.addFirst(c);
                }
                /*
                 * ASCII codes for opening and closing pairs differ
                 * either by 1 ('(' and ')') or 2 (the others)
                 */
                else if (stack.peek() == c - 2 || stack.peek() == c - 1) {
                    stack.removeFirst();
                } else {
                    isCorrupted = true;
                    score += closingScores.get(c);
                    break;
                }
            }
            if (!isCorrupted) {
                for (Character element : stack) {
                    score = score * 5 + openingScores.get(element);
                }
            }
        }
    }

    public static ArrayList<String> readLines(String filename) {
        ArrayList<String> lines = new ArrayList<>();
        Scanner sc;
        try {
            sc = new Scanner(new File(filename));
            while (sc.hasNextLine()) {
                lines.add(sc.nextLine());
            }
            sc.close();
        } catch (Exception e) {
            System.out.println("Something went wrong " + e);
        }
        return lines;
    }

    public static void main(String[] args) {
        long score = 0;
        ArrayList<Long> incompleteLineScores = new ArrayList<>();
        for (String line : readLines("data.txt")) {
            Chunk chunk = new Chunk(line);
            if (chunk.isCorrupted) {
                score += chunk.score;
            } else {
                incompleteLineScores.add(chunk.score);
            }
        }
        Collections.sort(incompleteLineScores);
        System.out.println(score);
        System.out.println(incompleteLineScores.get(incompleteLineScores.size() / 2));
    }
}