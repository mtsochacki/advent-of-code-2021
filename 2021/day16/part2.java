import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class part2 {
    public static class Packet {
        Long value;
        int length;
        String content;
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

    public static Long makeCalculations(int packetType, ArrayList<Long> values) {
        switch (packetType) {
            case 0:
                Long sum = 0L;
                for (Long num : values) {
                    sum += num;
                }
                return sum;
            case 1:
                Long product = 1L;
                for (Long num : values) {
                    product *= num;
                }
                return product;
            case 2:
                return Collections.min(values);
            case 3:
                return Collections.max(values);
            case 5:
                return values.get(0) > values.get(1) ? 1L : 0L;
            case 6:
                return values.get(0) < values.get(1) ? 1L : 0L;
            case 7:
                return values.get(0).equals(values.get(1)) ? 1L : 0L;
            default:
                System.out.println("Error in switch statement");
                return -1L;
        }
    }

    public static Packet calculateLiterallValue(Packet packet) {
        int i = 6;
        String literallBinValue = "";
        while (packet.content.substring(i, i + 1).equals("1")) {
            literallBinValue += packet.content.substring(i + 1, i + 5);
            i += 5;
        }
        literallBinValue += packet.content.substring(i + 1, i + 5);
        i += 5;
        packet.content = packet.content.substring(i);
        packet.value = Long.parseLong(literallBinValue, 2);
        packet.length = i;
        return packet;
    }

    public static Packet calculateOperatorZero(Packet packet, int packetTypeID) {
        int subLength = Integer.parseInt(packet.content.substring(7, 22), 2);
        int packetLength = 22;
        packet.content = packet.content.substring(22);;
        ArrayList<Long> results = new ArrayList<>();

        while (subLength > 0) {
            packet = processPacket(packet);
            packetLength += packet.length;
            subLength -= packet.length;
            results.add(packet.value);
        }
        packet.length = packetLength;
        packet.value = makeCalculations(packetTypeID, results);
        return packet;
    }

    public static Packet calculateOperatorOne(Packet packet, int packetTypeID) {
        int subAmount = Integer.parseInt(packet.content.substring(7, 18), 2);
        int packetLength = 18;
        packet.content = packet.content.substring(18);
        ArrayList<Long> result = new ArrayList<>();

        for (int j = 0; j < subAmount; j++) {
            packet = processPacket(packet);
            packetLength += packet.length;
            result.add(packet.value);
        }
        packet.length = packetLength;
        packet.value = makeCalculations(packetTypeID, result);
        return packet;
    }

    public static Packet processPacket(Packet packet) {
        int packetTypeID = Integer.parseInt(packet.content.substring(3, 6), 2);
        if (packetTypeID == 4) {
            return calculateLiterallValue(packet);
        } else {
            if (packet.content.substring(6, 7).equals("0")) {
                return calculateOperatorZero(packet, packetTypeID);
            } else {
                return calculateOperatorOne(packet, packetTypeID);
            }
        }
    }

    public static void main(String[] args) {
        Packet transmission = new Packet();
        transmission.content = readInput("data.txt");
        transmission = processPacket(transmission);
        System.out.println("Output is " + transmission.value);
    }
}