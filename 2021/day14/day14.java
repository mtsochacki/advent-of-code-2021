import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.io.File;

public class day14 {
    public static class Rule {
        String pair;
        String outcome;
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

        /*
         * Given the name of a file containing a list of rules for creating
         * new elements in the form of arrow-delimited strings, e.g. "CH -> B",
         * read and transform them into a list of `Rule`s
         */
    public static ArrayList<Rule> readRules(String filename) {
        Scanner sc = null;
        ArrayList<Rule> input = new ArrayList<>();
        try {
            sc = new Scanner(new File(filename));
            sc.useDelimiter("\\n| -> ");
            while (sc.hasNext()) {
                Rule rule = new Rule();
                rule.pair = sc.next();
                rule.outcome = sc.next();
                input.add(rule);
            }
        } catch (Exception e) {
            System.out.println("Something went wrong" + e);
        } finally {
            sc.close();
        }
        return input;
    }

    /*
     * Split the template into two-letter chunks and count
     * the occurrences of them in the template.
     *
     * E.g. given a template
     * NCCNC
     * Transform it into a map
     * {"NC": 2, "CN": 1, "CC": 1}
     */
    public static HashMap<String, Long> splitToChunks(String template) {
        HashMap<String, Long> chunks = new HashMap<>();
        for (int i = 0; i < template.length() - 1; i++) {
            chunks.merge(template.substring(i, i + 2), 1L, Long::sum);
        }
        return chunks;
    }

    /*
     * Split the template into individual letters and count the occurrences
     * of them in the template.
     *
     * E.g. given a template
     * NCNBCHB
     * Transform it into a map
     * {"N":2, "C":2, "B":2, "H":1}
     */
    public static HashMap<String, Long> countLetters(String template) {
        HashMap<String, Long> lettersCount = new HashMap<>();
        for (char letter : template.toCharArray()) {
            lettersCount.merge(String.valueOf(letter), 1L, Long::sum);
        }
        return lettersCount;
    }

    public static Long calculatePolymers(int steps) {
        String template = readTemplate("data.txt");
        ArrayList<Rule> input = readRules("data.txt");
        HashMap<String, Long> chunks = splitToChunks(template);
        HashMap<String, Long> letters = countLetters(template);
        for (int i = 0; i < steps; i++) {
            HashMap<String, Long> newChunks = new HashMap<>();
            for (Rule rule : input) {
                if (chunks.containsKey(rule.pair)) {
                    letters.merge(rule.outcome, chunks.get(rule.pair), Long::sum);
                    String leftPair = rule.pair.substring(0, 1) + rule.outcome;
                    String rightPair = rule.outcome + rule.pair.substring(1, 2);
                    newChunks.merge(leftPair, chunks.get(rule.pair), Long::sum);
                    newChunks.merge(rightPair, chunks.get(rule.pair), Long::sum);
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
