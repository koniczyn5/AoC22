package day13;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class Packet {
    public static Packet mapFromString(String input) {
        if (input.matches("[0-9]+")) {
            return new IntPacket(Integer.parseInt(input));
        }
        //If it's not an int, it must be a list
        ListPacket result = new ListPacket();
        String content = input.substring(1, input.length() - 1);
        result.getPacketList().addAll(processContentOfList(content));
        return result;
    }

    public static int compare(Packet firstPacket, Packet secondPacket) {
        if (firstPacket instanceof ListPacket firstList && secondPacket instanceof ListPacket secondList) {
            int firstListSize = firstList.getPacketList().size();
            int secondListSize = secondList.getPacketList().size();

            for (int i = 0; i < firstListSize; i++) {
                if (secondListSize <= i) {
                    return -1;
                }
                int packetCompare = Packet.compare(firstList.getPacketList().get(i), secondList.getPacketList().get(i));
                if (packetCompare != 0) {
                    return packetCompare;
                }
            }
            if (secondListSize > firstListSize) {
                return 1;
            }
        }
        if (firstPacket instanceof IntPacket firstInt && secondPacket instanceof IntPacket secondInt) {
            int intCompare = Integer.compare(secondInt.getValue(), firstInt.getValue());
            if (intCompare != 0) {
                return intCompare;
            }
        }
        if (firstPacket instanceof IntPacket firstInt && secondPacket instanceof ListPacket secondList) {
            ListPacket wrapIntInList = new ListPacket();
            wrapIntInList.getPacketList().add(firstInt);
            int packetCompare = compare(wrapIntInList, secondList);
            if (packetCompare != 0) {
                return packetCompare;
            }
        }
        if (firstPacket instanceof ListPacket firstList && secondPacket instanceof IntPacket secondInt) {
            ListPacket wrapIntInList = new ListPacket();
            wrapIntInList.getPacketList().add(secondInt);
            return compare(firstList, wrapIntInList);
        }
        return 0;
    }

    private static List<Packet> processContentOfList(String input) {
        List<Packet> result = new ArrayList<>();
        int firstNestedListPosition = input.indexOf('[');
        //If no more lists, just split items
        if (firstNestedListPosition == -1) {
            for (String s : input.split(",")) {
                if (StringUtils.isNotBlank(s)) {
                    result.add(Packet.mapFromString(s));
                }
            }
        } else {
            for (String s : input.substring(0, firstNestedListPosition).split(",")) {
                if (StringUtils.isNotBlank(s)) {
                    result.add(Packet.mapFromString(s));
                }
            }
            String withoutLeadingInts = input.substring(firstNestedListPosition);
            int endFirstNestedListPosition = findClosingBracketPosition(withoutLeadingInts) + 1;
            result.add(Packet.mapFromString(withoutLeadingInts.substring(0, endFirstNestedListPosition)));
            if (endFirstNestedListPosition != withoutLeadingInts.length()) {
                result.addAll(processContentOfList(withoutLeadingInts.substring(endFirstNestedListPosition + 1)));
            }
        }
        return result;
    }

    private static int findClosingBracketPosition(String input) {
        int bracketBalance = 1;
        int currentPosition = 1;
        while (bracketBalance != 0) {
            if (input.charAt(currentPosition) == '[') {
                bracketBalance++;
            } else if (input.charAt(currentPosition) == ']') {
                bracketBalance--;
            }
            currentPosition++;
        }
        return currentPosition - 1;
    }
}
