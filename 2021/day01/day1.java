import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class day1 {
    public static int part1() {
        int counter = 0;
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader("data.txt"));
            String l;
            String previous;
            previous = in.readLine();

            while ((l = in.readLine()) != null) {
                if (Integer.parseInt(l) > Integer.parseInt(previous))
                    counter++;
                previous = l;
            }
        } catch (IOException e) {
            System.out.println("Something went horribly wrong");
        }
        return counter;
    }

    public static int part2() {

        int counter = 0;
        int total = 0;
        int previous = 0;
        int x = 0;
        Queue<Integer> q = new LinkedList<Integer>();
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader("data.txt"));
            String l;
            x = Integer.parseInt(in.readLine());
            q.add(x);
            total = total + x;
            x = Integer.parseInt(in.readLine());
            q.add(x);
            total = total + x;
            x = Integer.parseInt(in.readLine());
            q.add(x);
            total = total + x;
            previous = total;
            while ((l = in.readLine()) != null) {
                x = Integer.parseInt(l);
                q.add(x);
                total = total + x - q.remove();
                if (total > previous) {
                    counter++;
                }
                previous = total;
            }
        } catch (IOException e) {
            System.out.println("Something went horribly wrong");
        }
        return counter;
    }

    public static void main(String[] args) {
        System.out.println(part1());
        System.out.println(part2());
    }
}