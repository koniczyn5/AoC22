package day2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        File file = new File("resources\\day2\\input.txt");

        Map<Character, Map<Character, Integer>> myShapeToPoints = Map.of(
                'X', Map.of('A', 4, 'B', 1, 'C', 7),
                'Y', Map.of('A', 8, 'B', 5, 'C', 2),
                'Z', Map.of('A', 3, 'B', 9, 'C', 6)
        );

        Map<Character, Map<Character, Integer>> resultToPoints = Map.of(
                'X', Map.of('A', 3, 'B', 1, 'C', 2),
                'Y', Map.of('A', 4, 'B', 5, 'C', 6),
                'Z', Map.of('A', 8, 'B', 9, 'C', 7)
        );

        int totalScorePart1 = 0;
        int totalScorePart2 = 0;

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            while ((st = br.readLine()) != null) {
                Character myShape = st.charAt(2);
                Character opponentShape = st.charAt(0);
                totalScorePart1 += myShapeToPoints.get(myShape).get(opponentShape);
                totalScorePart2 += resultToPoints.get(myShape).get(opponentShape);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Part 1:");
        System.out.println(totalScorePart1);
        System.out.println("Part 2:");
        System.out.println(totalScorePart2);
        System.out.println("Finished");
    }

}
