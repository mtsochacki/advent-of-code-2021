import java.io.File;
import java.util.*;
import java.util.stream.LongStream;

public class day16 {
    static Pack initialisePacket(String binData) {
        if (Pack.getTypeID(binData) == 4) {
            return new LiteralPacket(binData);
        } else {
            return new OperatorPacket(binData);
        }
    }

    public static abstract class Pack {
        long value;
        int versionID;
        int typeID;
        String binContent;

        Pack(String binData) {
            this.versionID = Integer.parseInt(binData.substring(0, 3), 2);
            this.typeID = Integer.parseInt(binData.substring(3, 6), 2);
            this.binContent = binData.substring(6);
        }

        static int getTypeID(String binData) {
            return Integer.parseInt(binData.substring(3, 6), 2);
        }

        abstract int parsePacket();
        abstract int getVersionIdSum();
    }

    public static class LiteralPacket extends Pack {
        LiteralPacket(String binData) {
            super(binData);
        }

        int parsePacket() {
            int idx = 0;
            for (; idx < this.binContent.length(); idx += 5) {
                String binGroupValue = this.binContent.substring(idx + 1, idx + 5);
                this.value *= 16;
                this.value += Integer.parseInt(binGroupValue, 2);
                char binGroupControl = this.binContent.charAt(idx);
                if (binGroupControl == '0') {
                    idx += 5;
                    break;
                }
            }

            return idx + 6;
        }

        int getVersionIdSum() {
            return this.versionID;
        }
    }

    public static class OperatorPacket extends Pack {
        ArrayList<Pack> subpackets;
        int lengthTypeID;

        OperatorPacket(String binData) {
            super(binData);
            this.subpackets = new ArrayList<>();
            this.lengthTypeID = Integer.parseInt(this.binContent.substring(0, 1), 2);
        }

        int parsePacket() {
            if (this.lengthTypeID == 0) {
                int lengthOfPackets = Integer.parseInt(this.binContent.substring(1, 16), 2);
                int packetsLength = this.parsePacketsByLength(lengthOfPackets);
                this.calculatePacketValue();
                return packetsLength + 22;
            } else {
                int numberOfPackets = Integer.parseInt(this.binContent.substring(1, 12), 2);
                int packetsLength = this.parsePacketsByNumber(numberOfPackets);
                this.calculatePacketValue();
                return packetsLength + 18;
            }
        }

        void calculatePacketValue() {
            LongStream packetValues = this.subpackets.stream().mapToLong(p -> p.value);
            switch (this.typeID) {
                case 0 -> this.value = packetValues.sum();
                case 1 -> this.value = packetValues.reduce(1, (p, acc) -> p * acc);
                case 2 -> this.value = packetValues.min().orElseThrow();
                case 3 -> this.value = packetValues.max().orElseThrow();
                case 5 -> {
                    List<Long> values = packetValues.boxed().toList();
                    this.value = values.get(0) > values.get(1) ? 1L : 0L;
                }
                case 6 -> {
                    List<Long> values = packetValues.boxed().toList();
                    this.value = values.get(0) < values.get(1) ? 1L : 0L;
                }
                case 7 -> {
                    List<Long> values = packetValues.boxed().toList();
                    this.value = Objects.equals(values.get(0), values.get(1)) ? 1L : 0L;
                }
            }
        }

        int parsePacketsByLength(int lengthOfPackets) {
            int idx = 0;
            while (idx < lengthOfPackets) {
                Pack packet = initialisePacket(this.binContent.substring(16 + idx));
                int packetLength = packet.parsePacket();
                idx += packetLength;
                subpackets.add(packet);
            }
            return idx;
        }

        int parsePacketsByNumber(int numberOfPackets) {
            int num = 0;
            int idx = 0;
            while (num < numberOfPackets) {
                Pack packet = initialisePacket(this.binContent.substring(idx + 12));
                int packetLength = packet.parsePacket();
                idx += packetLength;
                num += 1;
                subpackets.add(packet);
            }
            return idx;
        }

        int getVersionIdSum() {
            return this.versionID + this.subpackets.stream().mapToInt(Pack::getVersionIdSum).sum();
        }
    }
    public static class Packet {
        Long value;
        int length;
        int versionSum;
        String binData;
    }

    public static String readInput(String filename) {
        String s;
        String result = "";
        Scanner sc = null;
        try {
            sc = new Scanner(new File(filename));
            s = sc.next();
            for (char c : s.toCharArray()) {
                result += hexToBin(String.valueOf(c));
            }
        } catch (Exception e) {
            System.out.println("Something went wrong " + e);
        } finally {
            sc.close();
        }
        return result;
    }

    private static String hexToBin(String hex) {
        String bin = Integer.toBinaryString(Integer.parseInt(hex, 16));
        return String.format("%4s", bin).replace(' ', '0');
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

    public static Packet calculateLiteralValue(Packet packet) {
        int i = 6;
        String literalBinValue = "";
        while (packet.binData.charAt(i) == '1') {
            literalBinValue += packet.binData.substring(i + 1, i + 5);
            i += 5;
        }
        literalBinValue += packet.binData.substring(i + 1, i + 5);
        i += 5;
        packet.binData = packet.binData.substring(i);
        packet.value = Long.parseLong(literalBinValue, 2);
        packet.length = i;
        return packet;
    }

    public static Packet calculateOperatorZero(Packet packet, int packetTypeID) {
        int subLength = Integer.parseInt(packet.binData.substring(7, 22), 2);
        int packetLength = 22;
        packet.binData = packet.binData.substring(22);
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
        int subAmount = Integer.parseInt(packet.binData.substring(7, 18), 2);
        int packetLength = 18;
        packet.binData = packet.binData.substring(18);
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
        packet.versionSum += Integer.parseInt(packet.binData.substring(0, 3),2);
        int packetTypeID = Integer.parseInt(packet.binData.substring(3, 6), 2);
        if (packetTypeID == 4) {
            return calculateLiteralValue(packet);
        } else {
            if (packet.binData.charAt(6) == '0') {
                return calculateOperatorZero(packet, packetTypeID);
            } else {
                return calculateOperatorOne(packet, packetTypeID);
            }
        }
    }

    public static void main(String[] args) {
        Pack pack = initialisePacket(readInput("data.txt"));
        pack.parsePacket();
        System.out.println(pack.getVersionIdSum());
        System.out.println(pack.value);
    }
}