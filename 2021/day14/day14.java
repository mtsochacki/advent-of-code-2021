import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.io.File;

public class day14 {
    public static class Rules {
        String pair;
        String single;
    }

    public static String readTemplate(String filename) {
        String template = null;
        Scanner sc = null;
        try {
            sc = new Scanner(new File(filename));
            template = sc.next();
        } catch (Exception e) {
            System.out.println("Something went wrong" + e);
        } finally {
            sc.close();
        }
        return template;
    }

    public static ArrayList<Rules> readRules(String filename) {
        Scanner sc = null;
        ArrayList<Rules> input = new ArrayList<>();
        try {
            sc = new Scanner(new File(filename));
            sc.useDelimiter("\\n| -> ");
            while (sc.hasNext()) {
                Rules line = new Rules();
                line.pair = sc.next();
                line.single = sc.next();
                input.add(line);
            }
        } catch (Exception e) {
            System.out.println("Something went wrong" + e);
        } finally {
            sc.close();
        }
        return input;
    }

    public static HashMap<String, Long> splitToChunks(String template) {
        HashMap<String, Long> chunks = new HashMap<>();
        for (int i = 0; i < template.length() - 1; i++) {
            chunks.merge(String.valueOf(template.charAt(i))
                    + String.valueOf(template.charAt(i + 1)), 1L, Long::sum);
        }
        return chunks;
    }

    public static HashMap<String, Long> countLetters(String template) {
        HashMap<String, Long> lettersCount = new HashMap<>();
        for (char letter : template.toCharArray()) {
            lettersCount.merge(String.valueOf(letter), 1L, Long::sum);
        }
        return lettersCount;
    }

    public static Long calculatePolymers(int steps){
        String template = readTemplate("data.txt");
        ArrayList<Rules> input = readRules("data.txt");
        HashMap<String, Long> chunks = splitToChunks(template);
        HashMap<String, Long> letters = countLetters(template);
        for (int i = 0; i < steps; i++) {
            HashMap<String, Long> newChunks = new HashMap<>();
            for (Rules rule : input) {
                if (chunks.containsKey(rule.pair)) {
                    letters.merge(rule.single, chunks.get(rule.pair), Long::sum);
                    newChunks.merge(String.valueOf(rule.pair.charAt(0))
                            + rule.single, chunks.get(rule.pair), Long::sum);
                    newChunks.merge(String.valueOf(rule.single
                            + rule.pair.charAt(1)), chunks.get(rule.pair), Long::sum);
                }
            }
            chunks = newChunks;
        }
        return Collections.max(letters.values()) - Collections.min(letters.values());
    }

    public static void main(String[] args) {
        System.out.println(calculatePolymers(10));
        System.out.println(calculatePolymers(40));
    }
}
