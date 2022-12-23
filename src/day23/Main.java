package day23;

import javafx.geometry.Point2D;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    private static final List<List<Point2D>> placesToCheck = List.of(
            List.of(new Point2D(-1, -1), new Point2D(0, -1), new Point2D(1, -1)), //North
            List.of(new Point2D(-1, 1), new Point2D(0, 1), new Point2D(1, 1)), //South
            List.of(new Point2D(-1, -1), new Point2D(-1, 0), new Point2D(-1, 1)), //West
            List.of(new Point2D(1, -1), new Point2D(1, 0), new Point2D(1, 1))  //East
    );

    private static final List<Point2D> placesToMove = List.of(
            new Point2D(0, -1), new Point2D(0, 1),
            new Point2D(-1, 0), new Point2D(1, 0)
    );

    private static final List<Point2D> neighbours = List.of(
            new Point2D(-1, -1), new Point2D(0, -1), new Point2D(1, -1),
            new Point2D(-1, 1), new Point2D(0, 1), new Point2D(1, 1),
            new Point2D(-1, 0), new Point2D(1, 0)
    );

    public static void main(String[] args) {

        File file = new File("resources\\day23\\input.txt");
        List<Point2D> elves = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            int rowCounter = 0;
            while ((st = br.readLine()) != null) {
                for (int i = 0; i < st.length(); i++) {
                    if (st.charAt(i) == '#') {
                        elves.add(new Point2D(i, rowCounter));
                    }
                }
                rowCounter++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        double minX = 0, maxX = 0;
        double minY = 0, maxY = 0;

        int i = 0;
        while (true) {
            Map<Point2D, Integer> potentialPositions = new HashMap<>();
            for (int j = 0; j < elves.size(); j++) {
                Point2D currentPosition = elves.get(j);
                if (neighbours.stream().map(point2D -> point2D.add(currentPosition)).anyMatch(elves::contains)) {
                    for (int directionCounter = 0; directionCounter < 4; directionCounter++) {
                        int idx = (i + directionCounter) % 4;
                        if (placesToCheck.get(idx).stream().map(point2D -> point2D.add(currentPosition)).noneMatch(elves::contains)) {
                            Point2D proposedPosition = currentPosition.add(placesToMove.get(idx));
                            if (potentialPositions.remove(proposedPosition) == null) {
                                potentialPositions.put(proposedPosition, j);
                            }
                            break;
                        }
                    }
                }
            }
            if (potentialPositions.isEmpty()) {
                break;
            }
            if (i == 10) {
                minX = elves.stream().map(Point2D::getX).min(Double::compareTo).orElse((double) 0);
                maxX = elves.stream().map(Point2D::getX).max(Double::compareTo).orElse((double) 0);
                minY = elves.stream().map(Point2D::getY).min(Double::compareTo).orElse((double) 0);
                maxY = elves.stream().map(Point2D::getY).max(Double::compareTo).orElse((double) 0);
            }
            for (Map.Entry<Point2D, Integer> entry : potentialPositions.entrySet()) {
                elves.set(entry.getValue(), entry.getKey());
            }
            i++;
        }


        System.out.println("Part 1:");
        System.out.println(
                (maxX - minX + 1) * (maxY - minY + 1) - elves.size()
        );
        System.out.println("Part 2:");
        System.out.println(i + 1);
        System.out.println("Finished");
    }
}