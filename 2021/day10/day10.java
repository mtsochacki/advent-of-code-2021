import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;
import java.util.LinkedList;

public class day10 {
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

    public static void syntaxScoring() {
        ArrayList<ArrayList<Character>> input = readInput("data.txt");
        ArrayList<Long> autocompleteScores = new ArrayList<>();
        int syntaxScore = 0;

        for (ArrayList<Character> line : input) {

            LinkedList<Character> stack = new LinkedList<>();
            Boolean isCorrupted = false;
            long autoTmpScore = 0;

            for (int i = 0; i < line.size(); i++) {
                if (line.get(i) == '(' || line.get(i) == '<' ||
                    line.get(i) == '[' || line.get(i) == '{')
                    stack.addFirst(line.get(i));

                else if (stack.peek() == line.get(i) - 2 || stack.peek() == line.get(i) - 1)
                    stack.removeFirst();

                else {  // mark line as corrupted and add to syntax score
                    isCorrupted = true;
                    if (line.get(i) == ')')
                        syntaxScore += 3;
                    else if (line.get(i) == ']')
                        syntaxScore += 57;
                    else if (line.get(i) == '}')
                        syntaxScore += 1197;
                    else if (line.get(i) == '>')
                        syntaxScore += 25137;
                    break;
                }
            }  
            if (isCorrupted == false) {
                for (Character element : stack) {
                    if (element == '(')
                        autoTmpScore = autoTmpScore * 5 + 1;
                    else if (element == '[')
                        autoTmpScore = autoTmpScore * 5 + 2;
                    else if (element == '{')
                        autoTmpScore = autoTmpScore * 5 + 3;
                    else if (element == '<')
                        autoTmpScore = autoTmpScore * 5 + 4;
                }
                autocompleteScores.add(autoTmpScore);
            }
        }
        System.out.println(syntaxScore);
        Collections.sort(autocompleteScores);
        System.out.println(autocompleteScores.get(autocompleteScores.size() / 2));
    }

    public static void main(String[] args) {
        syntaxScoring();
    }
}