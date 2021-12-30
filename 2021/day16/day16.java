import java.io.File;
import java.util.*;
import java.util.stream.LongStream;

public class day16 {
    static Packet initialisePacket(String binData) {
        if (Packet.getTypeID(binData) == 4) {
            return new LiteralPacket(binData);
        } else {
            return new OperatorPacket(binData);
        }
    }

    public static abstract class Packet {
        protected final int HEADER_LENGTH = 6;
        long value;
        int versionID;
        int typeID;
        int length;
        String binContent;

        Packet(String binData) {
            this.versionID = Integer.parseInt(binData.substring(0, 3), 2);
            this.typeID = Integer.parseInt(binData.substring(3, 6), 2);
            this.binContent = binData.substring(6);
        }

        static int getTypeID(String binData) {
            return Integer.parseInt(binData.substring(3, 6), 2);
        }

        abstract void parsePacket();
        abstract int getVersionIdSum();
    }

    public static class LiteralPacket extends Packet {
        LiteralPacket(String binData) {
            super(binData);
        }

        void parsePacket() {
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

            this.length = idx + 6;
        }

        int getVersionIdSum() {
            return this.versionID;
        }
    }

    public static class OperatorPacket extends Packet {
        private final int OP_HEADER_LENGTH;
        ArrayList<Packet> subpackets;
        int lengthTypeID;

        OperatorPacket(String binData) {
            super(binData);
            this.subpackets = new ArrayList<>();
            this.lengthTypeID = Integer.parseInt(this.binContent.substring(0, 1), 2);
            if (this.lengthTypeID == 0) {
                this.OP_HEADER_LENGTH = 1 + 15;
            } else {
                this.OP_HEADER_LENGTH = 1 + 11;
            }
        }

        void parsePacket() {
            int subpacketsLength;
            int subpacketsLengthInfo = Integer.parseInt(this.binContent.substring(1, this.OP_HEADER_LENGTH), 2);
            if (this.lengthTypeID == 0) {
                subpacketsLength = this.parseSubpacketsByLength(subpacketsLengthInfo);
            } else {
                subpacketsLength = this.parseSubpacketsByNumber(subpacketsLengthInfo);
            }
            this.calculatePacketValue();
            this.length = this.HEADER_LENGTH + this.OP_HEADER_LENGTH + subpacketsLength;
        }

        int parseSubpacketsByLength(int lengthOfPackets) {
            int subpacketsLength = 0;
            while (subpacketsLength < lengthOfPackets) {
                Packet packet = initialisePacket(this.binContent.substring(16 + subpacketsLength));
                packet.parsePacket();
                subpacketsLength += packet.length;
                subpackets.add(packet);
            }
            return subpacketsLength;
        }

        int parseSubpacketsByNumber(int numberOfPackets) {
            int num = 0;
            int subpacketsLength = 0;
            while (num < numberOfPackets) {
                Packet packet = initialisePacket(this.binContent.substring(12 + subpacketsLength));
                packet.parsePacket();
                subpacketsLength += packet.length;
                num += 1;
                subpackets.add(packet);
            }
            return subpacketsLength;
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

        int getVersionIdSum() {
            return this.versionID + this.subpackets.stream().mapToInt(Packet::getVersionIdSum).sum();
        }
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

    public static void main(String[] args) {
        Packet packet = initialisePacket(readInput("data.txt"));
        packet.parsePacket();
        System.out.println(packet.getVersionIdSum());
        System.out.println(packet.value);
    }
}