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
        return line.xStart == line.xEnd; 
    }

    public static Boolean isHorizontal(LineOfVents line) {
        return line.yStart == line.yEnd;
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

        for (LineOfVents lineOfVents : input) {
            if (isVertical(lineOfVents)) {
                for (int j = Integer.min(lineOfVents.yStart, lineOfVents.yEnd); j <= Integer.max(lineOfVents.yStart,
                        lineOfVents.yEnd); j++) {
                    diagram[j][lineOfVents.xEnd] += 1;
                }
            } else if (isHorizontal(lineOfVents)) {
                for (int j = Integer.min(lineOfVents.xStart, lineOfVents.xEnd); j <= Integer.max(lineOfVents.xStart,
                        lineOfVents.xEnd); j++) {
                    diagram[lineOfVents.yEnd][j] += 1;
                }
                // check if we even want to calculate part2
            } else if (includePart2) {
                int lineLength = Math.abs(lineOfVents.xStart - lineOfVents.xEnd);
                int x = Integer.min(lineOfVents.xStart, lineOfVents.xEnd);
                // if both coordinates increase or decrease
                if ((lineOfVents.xStart < lineOfVents.xEnd) && (lineOfVents.yStart < lineOfVents.yEnd)
                        || (lineOfVents.xStart > lineOfVents.xEnd) && (lineOfVents.yStart > lineOfVents.yEnd)) {
                    int y = Integer.min(lineOfVents.yStart, lineOfVents.yEnd);
                    for (int j = 0; j <= lineLength; j++) {
                        diagram[y + j][x + j] += 1;
                    }
                    // if one coordinate increase and the other one decreases
                } else {
                    int y = Integer.max(lineOfVents.yStart, lineOfVents.yEnd);
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