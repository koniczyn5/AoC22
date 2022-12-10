package day10;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Main {

    private static final List<Integer> signalStrengths = new ArrayList<>();
    private static final List<Character> crtDisplay = new ArrayList<>();

    public static void main(String[] args) {

        File file = new File("resources\\day10\\input.txt");

        int xValue = 1;
        int cycleCounter = 0;

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            while ((st = br.readLine()) != null && cycleCounter <= 240) {
                String[] instructions = st.split(" ");
                //Start cycle
                cycleCounter++;
                //During cycle
                processCycle(xValue, cycleCounter);

                if (Objects.equals(instructions[0], "addx")) {
                    //End first cycle
                    //Start second cycle
                    cycleCounter++;
                    //During second cycle
                    processCycle(xValue, cycleCounter);
                    //End  second cycle
                    xValue += Integer.parseInt(instructions[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Part 1:");
        System.out.println(signalStrengths.stream().reduce(Integer::sum).orElse(0));
        System.out.println("Part 2:");
        for (int i = 0; i < 6; i++) {
            for (Character character: crtDisplay.subList(40*i,40*(i+1))){
                System.out.print(character);
            }
            System.out.println();
        }
        System.out.println("Finished");
    }

    private static void processCycle(int xValue, int cycleCounter) {
        if (cycleCounter <= 220 && (cycleCounter - 20) % 40 == 0) {
            signalStrengths.add(cycleCounter * xValue);
        }
        if (xValue <= cycleCounter % 40 && cycleCounter % 40 <= xValue + 2) {
            crtDisplay.add('#');
        } else {
            crtDisplay.add('.');
        }
    }
}