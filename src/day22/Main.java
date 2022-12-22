package day22;

import javafx.geometry.Point2D;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {

    public static final Map<Point2D, Character> BOARD = new HashMap<>();

    public static void main(String[] args) {

        File file = new File("resources\\day22\\input.txt");
        String instructions = null;
        PlayerState player = new PlayerState();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            int rowCounter = 1;
            while ((st = br.readLine()) != null) {
                if (StringUtils.isBlank(st)) {
                    break;
                }
                char[] characters = st.toCharArray();
                for (int i = 0; i < characters.length; i++) {
                    if (characters[i] != ' ') {
                        if (BOARD.isEmpty()) {
                            player.setCurrentPosition(new Point2D(i + 1, rowCounter));
                        }
                        BOARD.put(new Point2D(i + 1, rowCounter), characters[i]);
                    }
                }
                rowCounter++;
            }
            instructions = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Integer> numbers = Arrays.stream(instructions.split("[RL]")).map(Integer::parseInt).toList();
        List<String> turns = Arrays.stream(instructions.split("\\d+")).filter(StringUtils::isNotBlank).toList();

        Iterator<String> turnsIterator = turns.iterator();
        for (Integer number : numbers) {
            for (int i = 0; i < number; i++) {
                Point2D potentialPosition = findNextPoint(player);
                if (BOARD.get(potentialPosition).equals('.')) {
                    player.setCurrentPosition(potentialPosition);
                } else {
                    break;
                }
            }
            if (turnsIterator.hasNext()) {
                player.turn(turnsIterator.next().charAt(0));
            }
        }


        System.out.println("Part 1:");
        System.out.println(player.getScore());

        System.out.println("Part 2:");
        System.out.println();
        System.out.println("Finished");
    }

    private static Point2D findNextPoint(PlayerState player) {
        double currentX = player.getCurrentPosition().getX();
        double currentY = player.getCurrentPosition().getY();
        Point2D result = player.getCurrentPosition().add(PlayerState.increment.get(player.getCurrentDirection()));
        if (BOARD.containsKey(result)) {
            return result;
        }

        if (Direction.RIGHT.equals(player.getCurrentDirection())) {
            double newX = BOARD.keySet().stream()
                    .filter(point2D -> point2D.getY() == currentY)
                    .map(Point2D::getX)
                    .min(Double::compareTo)
                    .orElse((double) 0);
            return new Point2D(newX, currentY);
        } else if (Direction.LEFT.equals(player.getCurrentDirection())) {
            double newX = BOARD.keySet().stream()
                    .filter(point2D -> point2D.getY() == currentY)
                    .map(Point2D::getX)
                    .max(Double::compareTo)
                    .orElse((double) 0);
            return new Point2D(newX, currentY);
        } else if (Direction.DOWN.equals(player.getCurrentDirection())) {
            double newY = BOARD.keySet().stream()
                    .filter(point2D -> point2D.getX() == currentX)
                    .map(Point2D::getY)
                    .min(Double::compareTo)
                    .orElse((double) 0);
            return new Point2D(currentX, newY);
        } else {
            double newY = BOARD.keySet().stream()
                    .filter(point2D -> point2D.getX() == currentX)
                    .map(Point2D::getY)
                    .max(Double::compareTo)
                    .orElse((double) 0);
            return new Point2D(currentX, newY);
        }
    }
}