package day15;

import commons.Coordinate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {

    private static final int ROW_TO_CHECK = 2000000;
    public static final int DISTRESS_MAX_POSITION = 4000000;

    public static void main(String[] args) {

        File file = new File("resources\\day15\\input.txt");
        Set<Spot> objectsInCave = new HashSet<>();
        Map<Spot, Spot> sensorAndBeacon = new HashMap<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            while ((st = br.readLine()) != null) {
                String[] stParts = st.split("=");
                Spot sensor = getSpotOfType(Integer.parseInt(stParts[1].split(",")[0]),
                        Integer.parseInt(stParts[2].split(":")[0]),
                        objectsInCave,
                        Spot.SpotType.SENSOR);
                Spot beacon = getSpotOfType(Integer.parseInt(stParts[3].split(",")[0]),
                        Integer.parseInt(stParts[4]),
                        objectsInCave,
                        Spot.SpotType.BEACON);

                long distanceToBeacon = getManhattanDistance(sensor, beacon);

                long distanceToRow = Math.abs(sensor.getPoint().y() - ROW_TO_CHECK);
                if (distanceToRow <= distanceToBeacon) {
                    for (int i = 0; i <= distanceToBeacon - distanceToRow; i++) {
                        List<Integer> xs = List.of(-i, i);
                        for (Integer x : xs) {
                            Spot newSpot = new Spot(new Coordinate(sensor.getPoint().x() + x, ROW_TO_CHECK));
                            newSpot.setSpotType(Spot.SpotType.NOT_BEACON);
                            objectsInCave.add(newSpot);
                        }
                    }
                }
                sensorAndBeacon.put(sensor, beacon);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Part 1:");
        System.out.println(
                objectsInCave.stream()
                        .filter(spot -> spot.getPoint().y() == ROW_TO_CHECK)
                        .filter(spot -> Spot.SpotType.NOT_BEACON.equals(spot.getSpotType()))
                        .count()
        );
        long x = 0;
        long y = 0;

        while (true) {
            if (x > DISTRESS_MAX_POSITION && y > DISTRESS_MAX_POSITION) break;
            Coordinate newPoint = new Coordinate((int) x, (int) y);
            Map.Entry<Spot, Spot> sensor = sensorAndBeacon.entrySet().stream().filter(spotSpotEntry ->
                            getManhattanDistance(spotSpotEntry.getKey(), spotSpotEntry.getValue()) >=
                                    getManhattanDistance(new Spot(newPoint), spotSpotEntry.getKey()))
                    .findFirst().orElse(null);

            if (sensor == null) {
                break;
            }

            long sensorRange = getManhattanDistance(sensor.getKey(), sensor.getValue());

            long mhdToSensor = getManhattanDistance(new Spot(newPoint), sensor.getKey());
            long skippedXSteps = sensorRange - mhdToSensor + 1;

            long nextX = x + skippedXSteps;
            x = nextX > DISTRESS_MAX_POSITION ? 0 : nextX;
            y = nextX > DISTRESS_MAX_POSITION ? y + 1 : y;
        }

        System.out.println("Part 2:");
        System.out.println(x * 4000000 + y);
        System.out.println("Finished");
    }

    private static Spot getSpotOfType(int x, int y, Set<Spot> objectsInCave, Spot.SpotType spotType) {
        Coordinate spotPoint = new Coordinate(x, y);
        Spot spot = new Spot(spotPoint);
        if (!objectsInCave.add(spot)) {
            spot = objectsInCave.stream()
                    .filter(sp -> sp.getPoint().equals(spotPoint))
                    .findFirst()
                    .orElse(spot);
        }
        spot.setSpotType(spotType);
        return spot;
    }

    private static long getManhattanDistance(Spot firstSpot, Spot secodnSpot) {
        return Math.abs(firstSpot.getPoint().x() - secodnSpot.getPoint().x())
                + Math.abs(firstSpot.getPoint().y() - secodnSpot.getPoint().y());
    }
}