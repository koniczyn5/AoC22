package day4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        File file = new File("resources\\day4\\input.txt");

        int numberOfFullOverlaps = 0;
        int numberOfPairs = 0;
        int numberOfNoOverlaps = 0;

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            while ((st = br.readLine()) != null) {
                String[] assignmentPairs = st.split(",");

                numberOfPairs++;
                int overlapStatus = overlapStatus(assignmentPairs[0], assignmentPairs[1]);
                numberOfFullOverlaps += overlapStatus == 1 ? 1 : 0;
                numberOfNoOverlaps += overlapStatus == -1 ? 1 : 0;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        System.out.println("Part 1:");
        System.out.println(numberOfFullOverlaps);
        System.out.println("Part 2:");
        System.out.println(numberOfPairs - numberOfNoOverlaps);
        System.out.println("Finished");
    }

    /*
    Positive value means full overlap,
    Negative value means no overlap,
    Zero means partial overlap.
     */
    private static int overlapStatus(String firstAssignment, String secondAssignment) {
        List<Integer> firstSectionLimits = Arrays.stream(firstAssignment.split("-"))
                .map(Integer::valueOf)
                .toList();
        List<Integer> secondSectionLimits = Arrays.stream(secondAssignment.split("-"))
                .map(Integer::valueOf)
                .toList();

        Integer firstSectionStart = firstSectionLimits.get(0);
        Integer firstSectionEnd = firstSectionLimits.get(1);
        Integer secondSectionStart = secondSectionLimits.get(0);
        Integer secondSectionEnd = secondSectionLimits.get(1);

        if (firstSectionEnd < secondSectionStart
        || secondSectionEnd < firstSectionStart) {
            return -1;
        }
        if (firstSectionStart <= secondSectionStart) {
            if (secondSectionEnd <= firstSectionEnd) {
                return 1;
            }
        }
        if (secondSectionStart <= firstSectionStart) {
            if (firstSectionEnd <= secondSectionEnd) {
                return 1;
            }
        }
        return 0;
    }
}

