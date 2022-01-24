import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class day16 {
    public static class Packet {
        Long value;
        int length;
        int versionSum;
        String binData;
    }

    public static String readInput(String filename) {
        StringBuilder result = new StringBuilder();
        Scanner sc = null;
        try {
            sc = new Scanner(new File(filename));
            String line = sc.next();
            for (char c : line.toCharArray()) {
                result.append(hexDigitToBin(c));
            }
        } catch (Exception e) {
            System.out.println("Something went wrong " + e);
        } finally {
            sc.close();
        }
        return result.toString();
    }

    public static String hexDigitToBin(char c){
        int decNum = Integer.parseInt(String.valueOf(c), 16);
        String binString = String.format("%4s", Integer.toBinaryString(decNum));
        return binString.replace(' ', '0');
    }

    public static Long calculateTotalSubpacketValue(int packetType, ArrayList<Long> values) {
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

    public static Packet calculateLiteralValue(Packet packet) {
        int i = 6;
        StringBuilder literalBinValue = new StringBuilder();
        do{
            literalBinValue.append(packet.binData.substring(i + 1, i + 5));
            i += 5;
        } while(packet.binData.charAt(i - 5) == '1');
        packet.binData = packet.binData.substring(i);
        packet.value = Long.parseLong(literalBinValue.toString(), 2);
        packet.length = i;
        return packet;
    }

    public static Packet processSubpacketsByLength(Packet packet, int packetTypeID) {
        int packetLength = 22;
        int subLength = Integer.parseInt(packet.binData.substring(7, packetLength), 2);
        packet.binData = packet.binData.substring(packetLength);
        ArrayList<Long> results = new ArrayList<>();
        while (subLength > 0) {
            packet = processPacket(packet);
            packetLength += packet.length;
            subLength -= packet.length;
            results.add(packet.value);
        }
        packet.length = packetLength;
        packet.value = calculateTotalSubpacketValue(packetTypeID, results);
        return packet;
    }

    public static Packet processSubpakcetsByQuantity(Packet packet, int packetTypeID) {
        int packetLength = 18;
        int numberOfPackets = Integer.parseInt(packet.binData.substring(7, packetLength), 2);
        packet.binData = packet.binData.substring(packetLength);
        ArrayList<Long> result = new ArrayList<>();
        for (int j = 0; j < numberOfPackets; j++) {
            packet = processPacket(packet);
            packetLength += packet.length;
            result.add(packet.value);
        }
        packet.length = packetLength;
        packet.value = calculateTotalSubpacketValue(packetTypeID, result);
        return packet;
    }

    public static Packet processPacket(Packet packet) {
        packet.versionSum += Integer.parseInt(packet.binData.substring(0, 3),2);
        int packetTypeID = Integer.parseInt(packet.binData.substring(3, 6), 2);
        if (packetTypeID == 4) {
            return calculateLiteralValue(packet);
        } else {
            if (packet.binData.charAt(6) == '0') {
                return processSubpacketsByLength(packet, packetTypeID);
            } else {
                return processSubpakcetsByQuantity(packet, packetTypeID);
            }
        }
    }

    public static void main(String[] args) {
        Packet transmission = new Packet();
        transmission.binData = readInput("data.txt");
        transmission = processPacket(transmission);
        System.out.println("Sum of packet versions " + transmission.versionSum);
        System.out.println("Evaluated expression is " + transmission.value);
    }
}