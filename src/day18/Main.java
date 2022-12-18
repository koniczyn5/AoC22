package day18;

import javafx.geometry.BoundingBox;
import javafx.geometry.Point3D;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {

    public static final List<Point3D> directions = List.of(
      new Point3D(1,0,0),
      new Point3D(-1,0,0),
      new Point3D(0,1,0),
      new Point3D(0,-1,0),
      new Point3D(0,0,1),
      new Point3D(0,0,-1)
    );
    public static void main(String[] args) {

        File file = new File("resources\\day18\\input.txt");
        Map<Point3D, Set<CubeSide>> droplets = new HashMap<>();
        int minX = Integer.MAX_VALUE, maxX = 0;
        int minY = Integer.MAX_VALUE, maxY = 0;
        int minZ = Integer.MAX_VALUE, maxZ = 0;

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            while ((st = br.readLine()) != null) {
                String[] parts = st.split(",");
                int x = Integer.parseInt(parts[0]);
                int y = Integer.parseInt(parts[1]);
                int z = Integer.parseInt(parts[2]);
                droplets.put(new Point3D(x, y, z), new HashSet<>(List.of(CubeSide.values())));
                if (x < minX) minX = x;
                if (y < minY) minY = y;
                if (z < minZ) minZ = z;
                if (x > maxX) maxX = x;
                if (y > maxY) maxY = y;
                if (z > maxZ) maxZ = z;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Map.Entry<Point3D, Set<CubeSide>> droplet : droplets.entrySet()) {
            for (CubeSide side : new HashSet<>(droplet.getValue())) {

                Point3D currentPoint = droplet.getKey();
                Point3D pointOnSide = currentPoint.add(directions.get(side.ordinal()));
                Set<CubeSide> otherDropletSides = droplets.get(pointOnSide);
                if (otherDropletSides != null) {
                    droplet.getValue().remove(side);
                    otherDropletSides.remove(side.getOpposite());
                }
            }
        }


        System.out.println("Part 1:");
        System.out.println(
                droplets.values().stream()
                        .map(Set::size)
                        .reduce(Integer::sum)
                        .orElse(0)
        );

        int surfaceArea = 0;
        BoundingBox boundingBox = new BoundingBox(minX-1,
                minY-1,
                minZ-1,
                maxX-minX+2,
                maxY-minY+2,
                maxZ-minZ+2);

        HashSet<Point3D> visited = new HashSet<>();
        Queue<Point3D> toVisit = new ArrayDeque<>();
        toVisit.add(new Point3D(minX-1, minY-1, minZ-1));

        while (!toVisit.isEmpty()) {
            Point3D current = toVisit.poll();
            if(visited.contains(current)) {
                continue;
            }
            visited.add(current);

            for (Point3D direction: directions) {
                Point3D candidate = current.add(direction);
                if (!boundingBox.contains(candidate) || visited.contains(candidate)) {
                    continue;
                }
                if (droplets.containsKey(candidate)) {
                    surfaceArea++;
                } else {
                    toVisit.add(candidate);
                }
            }
        }

        System.out.println("Part 2:");
        System.out.println(surfaceArea);
        System.out.println("Finished");
    }
}