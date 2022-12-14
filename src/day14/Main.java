package day14;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        File file = new File("resources\\day14\\input.txt");
        Set<Coordinate> objectsInCave = new HashSet<>();
        int sandCounter = 0;

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            while ((st = br.readLine()) != null) {
                String[] instructions = st.split(" -> ");
                for (int i = 1; i < instructions.length; i++) {
                    List<Integer> firstCoordinates = Arrays.stream(instructions[i - 1].split(","))
                            .map(Integer::parseInt)
                            .toList();
                    List<Integer> secondCoordinates = Arrays.stream(instructions[i].split(","))
                            .map(Integer::parseInt)
                            .toList();
                    if (Objects.equals(firstCoordinates.get(0), secondCoordinates.get(0))) {
                        int begin = firstCoordinates.get(1);
                        int end = secondCoordinates.get(1);
                        if (end < begin) {
                            begin = end;
                            end = firstCoordinates.get(1);
                        }
                        for (int j = begin; j <= end; j++) {
                            objectsInCave.add(new Coordinate(firstCoordinates.get(0), j));
                        }
                    } else {
                        int begin = firstCoordinates.get(0);
                        int end = secondCoordinates.get(0);
                        if (end < begin) {
                            begin = end;
                            end = firstCoordinates.get(0);
                        }
                        for (int j = begin; j <= end; j++) {
                            objectsInCave.add(new Coordinate(j, firstCoordinates.get(1)));
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        int lowestY = findLowestPointInSet(objectsInCave);
        List<Integer> sandStartingCoordinates = List.of(500, 0);

        Coordinate currentSandPosition = new Coordinate(sandStartingCoordinates.get(0), sandStartingCoordinates.get(1));
        while (currentSandPosition.y() < lowestY) {
            if (!objectsInCave.contains(new Coordinate(currentSandPosition.x(), currentSandPosition.y() + 1))) {
                currentSandPosition = new Coordinate(currentSandPosition.x(), currentSandPosition.y() + 1);
            } else if (!objectsInCave.contains(new Coordinate(currentSandPosition.x() - 1, currentSandPosition.y() + 1))) {
                currentSandPosition = new Coordinate(currentSandPosition.x() - 1, currentSandPosition.y() + 1);
            } else if (!objectsInCave.contains(new Coordinate(currentSandPosition.x() + 1, currentSandPosition.y() + 1))) {
                currentSandPosition = new Coordinate(currentSandPosition.x() + 1, currentSandPosition.y() + 1);
            } else {
                objectsInCave.add(currentSandPosition);
                sandCounter++;
                currentSandPosition = new Coordinate(sandStartingCoordinates.get(0), sandStartingCoordinates.get(1));
            }
        }

        System.out.println("Part 1:");
        System.out.println(sandCounter);

        int floorY = lowestY + 2;
        while (true) {
            if (currentSandPosition.y() + 1 < floorY) {
                if (!objectsInCave.contains(new Coordinate(currentSandPosition.x(), currentSandPosition.y() + 1))) {
                    currentSandPosition = new Coordinate(currentSandPosition.x(), currentSandPosition.y() + 1);
                    continue;
                } else if (!objectsInCave.contains(new Coordinate(currentSandPosition.x() - 1, currentSandPosition.y() + 1))) {
                    currentSandPosition = new Coordinate(currentSandPosition.x() - 1, currentSandPosition.y() + 1);
                    continue;
                } else if (!objectsInCave.contains(new Coordinate(currentSandPosition.x() + 1, currentSandPosition.y() + 1))) {
                    currentSandPosition = new Coordinate(currentSandPosition.x() + 1, currentSandPosition.y() + 1);
                    continue;
                }
            }
            sandCounter++;
            if (currentSandPosition.x() == sandStartingCoordinates.get(0)
                    && currentSandPosition.y() == sandStartingCoordinates.get(1)) {
                break;
            }
            objectsInCave.add(currentSandPosition);
            currentSandPosition = new Coordinate(sandStartingCoordinates.get(0), sandStartingCoordinates.get(1));
        }


        System.out.println("Part 2:");
        System.out.println(sandCounter);
        System.out.println("Finished");
    }

    private static int findLowestPointInSet(Set<Coordinate> points) {
        return points.stream().map(Coordinate::y).max(Integer::compareTo).orElse(0);
    }
}