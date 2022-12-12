package day12;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Main {
    public static void main(String[] args) {

        File file = new File("resources\\day12\\input.txt");
        Map<Coordinate, Hill> hills = new HashMap<>();
        Hill destination = null;
        Hill start = null;

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            int rowCounter = 0;
            while ((st = br.readLine()) != null) {
                for (int i = 0; i < st.length(); i++) {
                    char hillHeightChar = st.charAt(i);
                    switch (hillHeightChar) {
                        case 'S' -> {
                            Hill newHill = new Hill(i, rowCounter, 'a');
                            hills.put(new Coordinate(newHill), newHill);
                            start = newHill;
                        }
                        case 'E' -> {
                            Hill newHill = new Hill(i, rowCounter, 'z');
                            hills.put(new Coordinate(newHill), newHill);
                            newHill.setDistanceToEnd(0);
                            destination = newHill;
                        }
                        default -> {
                            Hill newHill = new Hill(i, rowCounter, hillHeightChar);
                            hills.put(new Coordinate(newHill), newHill);
                        }
                    }
                }
                rowCounter++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Queue<Hill> hillsToVisitQueue = new ArrayDeque<>();
        hillsToVisitQueue.add(destination);
        while (!hillsToVisitQueue.isEmpty()) {
            Hill currentHill = hillsToVisitQueue.poll();
            currentHill.setVisited(true);
            List<Coordinate> neighbours = List.of(
                    new Coordinate(currentHill.getX() - 1, currentHill.getY()),
                    new Coordinate(currentHill.getX() + 1, currentHill.getY()),
                    new Coordinate(currentHill.getX(), currentHill.getY() - 1),
                    new Coordinate(currentHill.getX(), currentHill.getY() + 1)
            );
            for (Coordinate potentialNeighbourCoordinates : neighbours) {
                Hill neighbour = hills.get(potentialNeighbourCoordinates);
                if (neighbour == null || neighbour.isVisited()) {
                    continue;
                }
                int currentDistance = currentHill.getDistanceToEnd() + 1;
                if (currentHill.getHeight() - neighbour.getHeight() <= 1
                        && currentDistance < neighbour.getDistanceToEnd()) {
                    neighbour.setDistanceToEnd(currentDistance);
                    hillsToVisitQueue.add(neighbour);
                }
            }
        }

        System.out.println("Part 1:");
        System.out.println(start.getDistanceToEnd());
        System.out.println("Part 2:");
        System.out.println(hills.values().stream()
                .filter(hill -> hill.getHeight() == 'a')
                .map(Hill::getDistanceToEnd)
                .min(Comparator.naturalOrder())
                .get()
        );
        System.out.println("Finished");
    }
}