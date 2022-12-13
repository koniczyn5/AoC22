package day13;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        File file = new File("resources\\day13\\input.txt");
        int sumOfIndicesForRightPairs = 0;
        List<Packet> packets = new ArrayList<>();
        Packet firstDivider = Packet.mapFromString("[[2]]");
        Packet secondDivider = Packet.mapFromString("[[6]]");
        packets.add(firstDivider);
        packets.add(secondDivider);

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            int pairIndex = 1;
            while ((st = br.readLine()) != null) {
                if (st.isBlank()) {
                    pairIndex++;
                    continue;
                }
                Packet firstPacket = Packet.mapFromString(st);
                Packet secondPacket = Packet.mapFromString(br.readLine());
                packets.add(firstPacket);
                packets.add(secondPacket);

                if (Packet.compare(firstPacket, secondPacket) > 0) {
                    sumOfIndicesForRightPairs += pairIndex;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        packets.sort(Packet::compare);
        Collections.reverse(packets);

        System.out.println("Part 1:");
        System.out.println(sumOfIndicesForRightPairs);
        System.out.println("Part 2:");
        System.out.println(
                (packets.indexOf(firstDivider) + 1) * (packets.indexOf(secondDivider) + 1)
        );
        System.out.println("Finished");
    }
}