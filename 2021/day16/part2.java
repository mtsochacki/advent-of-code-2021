import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
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
        System.out.println(Arrays.toString(values.toArray()));
        switch (packetType) {
            case 0:
                Long sum = 0L;
                for (Long element : values) {
                    sum += element;
                }
                System.out.println(sum);
                return sum;
            case 1:
                Long result = 1L;
                for (Long element : values) {
                    result *= element;
                }
                System.out.println(result);
                return result;
            case 2:
                Long result2 = Collections.min(values);
                System.out.println(result2);
                return result2;
            case 3:
                Long result3 = Collections.max(values);
                System.out.println(result3);
                return result3;
            case 5:
                if (values.get(0) > values.get(1))
                    return 1L;
                else
                    return 0L;
            case 6:
                if (values.get(0) < values.get(1))
                    return 1L;
                else
                    return 0L;
            case 7:
                if (values.get(0).equals(values.get(1))) {
                    System.out.println("takie same");
                    return 1L;
                } else {

                    return 0L;
                }
            default:
                return 1L;
        }
    }

    public static Packet calculateLiterallValue(Packet packet) {
        int i = 6;
        Packet tmpPacket = new Packet();
        String transmissions = packet.content;
        Long value;
        String actualValue = "";
        while (transmissions.substring(i, i + 1).equals("1")) {
            actualValue += transmissions.substring(i + 1, i + 5);
            i += 5;
        }
        actualValue += transmissions.substring(i + 1, i + 5);
        i += 5;
        transmissions = transmissions.substring(i);
        value = Long.parseLong(actualValue, 2);
        System.out.println("Literal num " + value);
        // System.out.println(transmissions);
        tmpPacket.content = transmissions;
        tmpPacket.length = i;
        tmpPacket.value = value;
        return tmpPacket;
    }

    public static Packet processPacket(Packet packet) {
        Packet tmpPacket = new Packet();
        /* sprawdź czy pakiet się skończył czy nie, i jeśli tak to go zwróć */
        if (!packet.content.contains("1")) {
            return packet;
        }
        /* Odczytaj typ pakietu */
        int packetTypeID = Integer.parseInt(packet.content.substring(3, 6), 2);
        System.out.println("type_id " + packetTypeID);
        /* Oblicz literall Value pakietu i zwróć */
        if (packetTypeID == 4) {
            return calculateLiterallValue(packet);
        } else {
            /* Jeśli to operational packet z Length ID 0 */
            if (packet.content.substring(6, 7).equals("0")) {
                /* Odczytaj długość subpakietu */
                int subLength = Integer.parseInt(packet.content.substring(7, 22), 2);
                int packetLength = 22;
                /* Przypisz do stringa ztrimowany subpakiet */
                String subpacket = packet.content.substring(22);
                packet.content = subpacket;
                /* Stwórz ArrayLista na przechowywanie wyników subpakietów */
                ArrayList<Long> result = new ArrayList<>();

                /* aż nie przerobisz wszystkich bitów to przerabiaj kolejne subpakiety */
                while (subLength > 0) {
                    tmpPacket = processPacket(packet);
                    packetLength += tmpPacket.length;
                    subLength -= tmpPacket.length;
                    result.add(tmpPacket.value);
                    packet = tmpPacket;
                }
                tmpPacket = packet;
                tmpPacket.length = packetLength;
                // System.out.println(Arrays.toString(result.toArray()));
                packet.value = makeCalculations(packetTypeID, result);
                // System.out.println("Calculated value is " + packet.value);
                return tmpPacket;
            } else {
                /* Odczytaj ile jest subpakietów */
                int amountOfBits = Integer.parseInt(packet.content.substring(7, 18), 2);
                int packetLength = 18;
                /* Przypisz do stringa ztrimowany subpakiet */
                String subpacket = packet.content.substring(18);
                packet.content = subpacket;
                /* Stwórz ArrayLista na przechowywanie wyników */
                ArrayList<Long> result = new ArrayList<>();
                /* Przerób x kolejnych subpakietów */
                for (int j = 0; j < amountOfBits; j++) {
                    tmpPacket = processPacket(packet);
                    packetLength += tmpPacket.length;
                    result.add(tmpPacket.value);
                    packet = tmpPacket;
                }
                tmpPacket = packet;
                tmpPacket.length = packetLength;
                // System.out.println(Arrays.toString(result.toArray()));
                packet.value = makeCalculations(packetTypeID, result);
                // System.out.println("Calculated value is " + packet.value);
                return tmpPacket;
            }
        }
    }

    public static void main(String[] args) {
        String bits = readInput("data.txt");
        Packet x = new Packet();
        x.content = bits;
        x.value = 0L;
        x = processPacket(x);
        System.out.println("Output is " + x.value);
        System.out.println("Output is " + x.content);

    }
}
