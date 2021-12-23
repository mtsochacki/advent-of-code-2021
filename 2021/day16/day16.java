import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

public class day16 {
    public static class Packet {
        int versionNum;
        int length;
    }

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

    public static Packet processPacket(String packet, int start) {
        Packet result = new Packet();
        int i = start;
        result.versionNum += Integer.parseInt(packet.substring(i, i + 3), 2);
        i += 3;
        if (Integer.parseInt(packet.substring(i, i + 3), 2) == 4) {
            i += 3;
            while (packet.charAt(i) == '1') {
                i += 5;
            }
            i += 4;
            result.length = i;
        } else {
            i += 3;
            if (packet.substring(i, i + 1) == "0") {
                i += 1;
                int lengthOfSub = Integer.parseInt(packet.substring(i, i + 15), 2);
                i += 15;
                int tmpLength = i + lengthOfSub;
                while (i != tmpLength) {
                    Packet subPacket = processPacket(packet, i);
                    result.versionNum += subPacket.versionNum;
                    i = subPacket.length;
                }
            } else {
                i += 1;
                int numOfSub = Integer.parseInt(packet.substring(i, i + 11), 2);
                i += 11;
                for (int j = 0; j < numOfSub; j++) {
                    Packet subPacket = processPacket(packet, i);
                    result.versionNum += subPacket.versionNum;
                    i = subPacket.length;
                }
            }
        }
        System.out.println(result.versionNum);
        return result;
    }

    public static void main(String[] args) {
        processPacket(readInput("data.txt"), 0);
    }
}