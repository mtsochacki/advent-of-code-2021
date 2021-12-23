import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

public class test {
    public static String readInput(String filename) {
        String s = "";
        String result = "";
        Scanner sc = null;
        try {
            sc = new Scanner(new File(filename));
            s = sc.next();
            for (char c : s.toCharArray()) {
                result += hexToBin.get(String.valueOf(c));
            }

        } catch (Exception e) {
            System.out.println("Something went wrong " + e);
        } finally {
            sc.close();
        }
        return result;
    }

    public static class Packet {
        int version;
        String s;
    }

    private static final HashMap<String, String> hexToBin = new HashMap<>();
    static {
        hexToBin.put("0", "0000");
        hexToBin.put("1", "0001");
        hexToBin.put("2", "0010");
        hexToBin.put("3", "0011");
        hexToBin.put("4", "0100");
        hexToBin.put("5", "0101");
        hexToBin.put("6", "0110");
        hexToBin.put("7", "0111");
        hexToBin.put("8", "1000");
        hexToBin.put("9", "1001");
        hexToBin.put("A", "1010");
        hexToBin.put("B", "1011");
        hexToBin.put("C", "1100");
        hexToBin.put("D", "1101");
        hexToBin.put("E", "1110");
        hexToBin.put("F", "1111");
    }

    public static String processPacket(Packet packet) {
        int i = 0;
        packet.version = Integer.parseInt(packet.s.substring(i, i + 3), 2);
        i += 3;
        int packetTypeID = Integer.parseInt(packet.s.substring(i, i + 3), 2);
        i += 3;
        if (packetTypeID == 4) {
            System.out.println(packet.s.substring(i, i + 1));
            String tmp = packet.s.substring(i, i + 1);
            while (tmp.equals("1")) {
                i += 5;
                tmp = packet.s.substring(i, i + 1);
            }
            i += 5;
        } else {
            if (packet.s.substring(i, i + 1).equals("0")) {
                i += 1;
                i += 15;
            } else {
                i += 1;
                i += 11;
            }
        }
        packet.s = packet.s.substring(i);
        System.out.println(packet.s);
        return packet.s;
    }

    public static void main(String[] args) {

        int versionSum = 0;
        Packet x = new Packet();
        x.s = readInput("data.txt");
        System.out.println(x.s);

        processPacket(x);
        versionSum += x.version;
        while (x.s.contains("1")) {
            x.s = processPacket(x);
            versionSum += x.version;
        }
        System.out.println(versionSum);
    }
}
