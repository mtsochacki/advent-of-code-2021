import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

public class day10 {
    public static class Line {
        long score;
        Boolean isCorrupted = false;

        Line(String line) {
            LinkedList<Character> stack = new LinkedList<>();
            for (char c : line.toCharArray()) {
                if (openingScores.containsKey(c)) {
                    stack.addFirst(c);
                    /*
                     * ASCII codes for opening and closing pairs differ
                     * either by 1 ('(' and ')') or 2 (the others)
                     */
                } else if (stack.peek() == c - 2 || stack.peek() == c - 1) {
                    stack.removeFirst();
                } else {
                    isCorrupted = true;
                    score += closingScores.get(c);
                }
            }
            if (!isCorrupted) {
                for (Character element : stack) {
                    score = score * 5 + openingScores.get(element);
                }
            }
        }
    }

    public static ArrayList<Line> readChunks(String filename) {
        ArrayList<Line> listOfChunks = new ArrayList<>();
        Scanner sc;
        try {
            sc = new Scanner(new File(filename));
            while (sc.hasNextLine()) {
                listOfChunks.add(new Line(sc.nextLine()));
            }
            sc.close();
        } catch (Exception e) {
            System.out.println("Something went wrong " + e);
        }
        return listOfChunks;
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

    public static void main(String[] args) {
        long score = 0;
        ArrayList<Long> allScores = new ArrayList<>();
        for (Line line : readChunks("data.txt")) {
            if (line.isCorrupted) {
                score += line.score;
            } else {
                allScores.add(line.score);
            }
        }
        Collections.sort(allScores);
        System.out.println(score);
        System.out.println(allScores.get(allScores.size() / 2));
    }
}