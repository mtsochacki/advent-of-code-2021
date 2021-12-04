import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class day2 {
    public static int part1() {
        int result = 0;
        int forward = 0;
        int vertical = 0;
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader("2021/data.txt"));
            String l;
            while ((l = in.readLine()) != null) {
                String[] parts = l.split(" ");
                switch (parts[0]) {
                    case "forward":
                        forward = forward + Integer.parseInt(parts[1]);
                        break;
                    case "down":
                        vertical = vertical + Integer.parseInt(parts[1]);
                        break;
                    case "up":
                        vertical = vertical - Integer.parseInt(parts[1]);
                        break;
                    default:
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println("Something went horribly wrong");
        }
        result = forward * vertical;
        return result;
    }

    public static int part2() {
        int result = 0;
        int forward = 0;
        int aim = 0;
        int depth = 0;
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader("2021/data.txt"));
            String l;
            while ((l = in.readLine()) != null) {
                String[] parts = l.split(" ");
                switch (parts[0]) {
                    case "forward":
                        forward = forward + Integer.parseInt(parts[1]);
                        depth = depth + aim * Integer.parseInt(parts[1]);
                        break;
                    case "down":
                        aim = aim + Integer.parseInt(parts[1]);
                        break;
                    case "up":
                        aim = aim - Integer.parseInt(parts[1]);
                        break;
                    default:
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println("Something went horribly wrong");
        }
        result = forward * depth;
        return result;
    }

    public static void main(String[] args) {
        System.out.println(part1());
        System.out.println(part2());
    }
}