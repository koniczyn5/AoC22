package day6;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;

public class Main {

    private static final int MARKER_LENGTH = 14;

    public static void main(String[] args) {

        File file = new File("resources\\day6\\input.txt");


        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            while ((st = br.readLine()) != null) {
                Queue<Character> potentialMarker = new ArrayDeque<>();
                int positionCounter = 0;
                for (char character : st.toCharArray()) {
                    positionCounter++;
                    potentialMarker.offer(character);
                    if (potentialMarker.size() == MARKER_LENGTH) {
                        if (potentialMarker.stream().distinct().toList().size() == MARKER_LENGTH) {
                            System.out.println(positionCounter);
                            break;
                        }
                        potentialMarker.poll();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Finished");
    }

}

