import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;

public class day05 {
    public static class LineOfVents {
        int xStart;
        int xEnd;
        int yStart;
        int yEnd;
    }

    public static Boolean isVertical(LineOfVents line) {
        if (line.xStart == line.xEnd)
            return true;
        else
            return false;
    }

    public static Boolean isHorizontal(LineOfVents line) {
        if (line.yStart == line.yEnd)
            return true;
        else
            return false;
    }

    public static ArrayList<LineOfVents> readInput(String filename) {
        Scanner sc = null;
        ArrayList<LineOfVents> input = new ArrayList<>();
        try {
            sc = new Scanner(new File(filename));
            sc.useDelimiter(",|\\n| -> ");
            while (sc.hasNextInt()) {
                LineOfVents line = new LineOfVents();
                line.xStart = sc.nextInt();
                line.yStart = sc.nextInt();
                line.xEnd = sc.nextInt();
                line.yEnd = sc.nextInt();
                input.add(line);
            }
        } catch (Exception e) {
            System.out.println("Something went wrong" + e);
        } finally {
            sc.close();
        }
        return input;
    }

    public static void calculateOverlap(Boolean includePart2) {
        ArrayList<LineOfVents> input = readInput("data.txt");
        int[][] diagram = new int[1000][1000];
        int count = 0;

        for (int i = 0; i < input.size(); i++) {
            if (isVertical(input.get(i))) {
                for (int j = Integer.min(input.get(i).yStart, input.get(i).yEnd); j <= Integer.max(input.get(i).yStart,
                        input.get(i).yEnd); j++) {
                    diagram[j][input.get(i).xEnd] += 1;
                }
            } else if (isHorizontal(input.get(i))) {
                for (int j = Integer.min(input.get(i).xStart, input.get(i).xEnd); j <= Integer.max(input.get(i).xStart,
                        input.get(i).xEnd); j++) {
                    diagram[input.get(i).yEnd][j] += 1;
                }
                // check if we event want to calculate part2
            } else if (includePart2) {
                int lineLength = Math.abs(input.get(i).xStart - input.get(i).xEnd);
                int x = Integer.min(input.get(i).xStart, input.get(i).xEnd);
                // if both coordinates increase or decrerase
                if ((input.get(i).xStart < input.get(i).xEnd) && (input.get(i).yStart < input.get(i).yEnd)
                        || (input.get(i).xStart > input.get(i).xEnd) && (input.get(i).yStart > input.get(i).yEnd)) {
                    int y = Integer.min(input.get(i).yStart, input.get(i).yEnd);
                    for (int j = 0; j <= lineLength; j++) {
                        diagram[y + j][x + j] += 1;
                    }
                    // if one coordinate increase and the other one decreases
                } else {
                    int y = Integer.max(input.get(i).yStart, input.get(i).yEnd);
                    for (int j = 0; j <= lineLength; j++) {
                        diagram[y - j][x + j] += 1;
                    }
                }
            }
        }
        for (int i = 0; i < 1000; i++) {
            for (int j = 0; j < 1000; j++) {
                if (diagram[i][j] >= 2) {
                    count++;
                }
            }
        }
        System.out.println(count);
    }

    public static void main(String[] args) {
        calculateOverlap(false);
        calculateOverlap(true);
    }
}
