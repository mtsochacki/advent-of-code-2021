import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

public class day17 {
    public static boolean isYInTrench(int yVelocity, int yStart, int yEnd) {
        int y = 0;
        while (true) {
            y += yVelocity;
            if (y >= yEnd && y <= yStart) {
                return true;
            } else if (y < yEnd) {
                return false;
            }
            yVelocity--;
        }
    }

    public static int calculateHeight(int yVelocity) {
        int y = 0;
        while (yVelocity > 0) {
            y += yVelocity;
            yVelocity--;
        }
        return y;
    }

    public static boolean isXInTrench(int xVelocity, int xStart, int xEnd) {
        int x = 0;
        while (true) {
            x += xVelocity;
            if (x >= xStart && x <= xEnd) {
                return true;
            } else if (x > xEnd || xVelocity == 0) {
                return false;
            }
            xVelocity--;
        }
    }

    public static boolean isInTrench(int xVelocity, int yVelocity,
            int xStart, int xEnd, int yStart, int yEnd) {
        int x = 0;
        int y = 0;
        while (true) {
            x += xVelocity;
            y += yVelocity;
            if (x >= xStart && x <= xEnd && y >= yEnd && y <= yStart) {
                return true;
            } else if (x < xStart && xVelocity == 0) {
                return false;
            } else if (x > xEnd || y < yEnd) {
                return false;
            }
            if (xVelocity > 0) {
                xVelocity--;
            }
            yVelocity--;
        }
    }

    public static void main(String[] args) {
        int xStart = 0;
        int xEnd = 0;
        int yStart = 0;
        int yEnd = 0;
        try {
            Scanner sc = new Scanner(new File("data.txt"));
            String[] newLine = sc.nextLine().split(":")[1].split(",");
            String[] coordinatesX = newLine[0].split("=")[1].split("\\..");
            String[] coordinatesY = newLine[1].split("=")[1].split("\\..");
            xStart = Integer.parseInt(coordinatesX[0]);
            xEnd = Integer.parseInt(coordinatesX[1]);
            yStart = Integer.parseInt(coordinatesY[1]);
            yEnd = Integer.parseInt(coordinatesY[0]);
            sc.close();
        } catch (Exception e) {
            System.out.println("There was an error " + e);
        }

        ArrayList<Integer> xVelocities = new ArrayList<>();
        ArrayList<Integer> yVelocities = new ArrayList<>();
        for (int i = -500; i < 500; i++) {
            if (isXInTrench(i, xStart, xEnd)) {
                xVelocities.add(i);
            }
            if (isYInTrench(i, yStart, yEnd)) {
                yVelocities.add(i);
            }
        }
        int total = 0;
        for (Integer x : xVelocities) {
            for (Integer y : yVelocities) {
                if (isInTrench(x, y, xStart, xEnd, yStart, yEnd)) {
                    total++;
                }
            }
        }
        System.out.println(calculateHeight(yVelocities.get(yVelocities.size() - 1)));
        System.out.println(total);
    }
}