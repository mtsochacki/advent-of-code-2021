import java.util.ArrayList;

public class day17 {
    public static boolean isInAreaVertical(int velocity) {
        int y = 0;
        while (true) {
            y += velocity;
            if (y >= -171 && y <= -136) {
                return true;
            } else if (y < -171) {
                return false;
            }
            velocity--;
        }
    }

    public static int calculateHeight(int velocity) {
        int y = 0;
        while (velocity > 0) {
            y += velocity;
            velocity--;
        }
        return y;
    }

    public static boolean isInAreaHorizontal(int velocity) {
        int x = 0;
        while (true) {
            x += velocity;
            if (x >= 60 && x <= 94) {
                return true;
            } else if (x > 94 || velocity == 0) {
                return false;
            }
            velocity--;
        }
    }

    public static boolean isInBox(int horizontalVelocity, int verticalVelocity) {
        int x = 0;
        int y = 0;
        while (true) {
            x += horizontalVelocity;
            y += verticalVelocity;
            if (x >= 60 && x <= 94 && y >= -171 && y <= -136) {
                return true;
            } else if (x < 60 && horizontalVelocity == 0) {
                return false;
            } else if (x > 94 || y < -171) {
                return false;
            }
            if (horizontalVelocity > 0) {
                horizontalVelocity--;
            }
            verticalVelocity--;
        }
    }

    public static void main(String[] args) {
        int total = 0;
        ArrayList<Integer> horizontalVelocities = new ArrayList<>();
        ArrayList<Integer> verticalVelocities = new ArrayList<>();

        for (int i = -1000; i < 1000; i++) {
            if (isInAreaHorizontal(i)) {
                horizontalVelocities.add(i);
            }
            if (isInAreaVertical(i)) {
                verticalVelocities.add(i);
            }
        }
        for (Integer x : horizontalVelocities) {
            for (Integer y : verticalVelocities) {
                if (isInBox(x, y)) {
                    total++;
                }
            }
        }
        System.out.println(calculateHeight(verticalVelocities.get(verticalVelocities.size() - 1)));
        System.out.println(total);
    }
}