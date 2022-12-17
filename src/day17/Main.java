package day17;

import commons.Coordinate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import static day17.RockType.PLUS;
import static day17.RockType.HORIZONTAL_LINE;
import static day17.RockType.VERTICAL_LINE;
import static day17.RockType.SQUARE;
import static day17.RockType.L;

public class Main {


    private static final Set<Coordinate> ROCK_TOWER = new TreeSet<>();
    private static final long ROCKS_TO_FALL = 2022L;

    public static void main(String[] args) {

        File file = new File("resources\\day17\\input.txt");

        Map<RockType, List<Coordinate>> partPositionsPerRockType = Map.of(
                PLUS, List.of(new Coordinate(0, 0), new Coordinate(1, -1), new Coordinate(1, 0), new Coordinate(1, 1), new Coordinate(2, 0)),
                HORIZONTAL_LINE, List.of(new Coordinate(0, 0), new Coordinate(1, 0), new Coordinate(2, 0), new Coordinate(3, 0)),
                VERTICAL_LINE, List.of(new Coordinate(0, 0), new Coordinate(0, 1), new Coordinate(0, 2), new Coordinate(0, 3)),
                SQUARE, List.of(new Coordinate(0, 0), new Coordinate(1, 0), new Coordinate(1, 1), new Coordinate(0, 1)),
                L, List.of(new Coordinate(0, 0), new Coordinate(1, 0), new Coordinate(2, 0), new Coordinate(2, 1), new Coordinate(2, 2))
        );
        String winds = "";

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            while ((st = br.readLine()) != null) {
                winds = st;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        long currentHeight = 0L;
        int windIndex = 0;


        for (long i = 1; i <= ROCKS_TO_FALL; i++) {
            //Define rock
            RockType rockType = RockType.values()[(int) (i % 5)];
            long startingY = currentHeight + 4;
            if (PLUS.equals(rockType)) {
                startingY++;
            }
            Rock currentRock = new Rock(partPositionsPerRockType.get(rockType));
            currentRock.setCurrentPosition(new Coordinate(-1L, startingY));

            while (true) {
                int jetDirection = winds.charAt(windIndex++) == '<' ? -1 : 1;
                if (windIndex == winds.length()) {
                    windIndex = 0;
                }
                moveByJet(currentRock, jetDirection);
                if (moveDown(currentRock)) {
                    for (Coordinate partOfRock : currentRock.getPartRelativePositions()) {
                        Coordinate newPosition = new Coordinate(currentRock.getCurrentPosition().x() + partOfRock.x(),
                                currentRock.getCurrentPosition().y() + partOfRock.y());
                        if (newPosition.y() > currentHeight) {
                            currentHeight = newPosition.y();
                        }

                        ROCK_TOWER.add(newPosition);
                    }
                    break;
                }
            }

        }

        System.out.println("Answer:");
        System.out.println(currentHeight);
        System.out.println("Finished");
    }

    private static void moveByJet(Rock rock, int jetDirection) {
        for (Coordinate partOfRock : rock.getPartRelativePositions()) {
            //Check against wall
            if (3 < rock.getCurrentPosition().x() + partOfRock.x() + jetDirection
                    || rock.getCurrentPosition().x() + partOfRock.x() + jetDirection < -3) {
                return;
            }
            //Check against rockTower
            Coordinate newPosition = new Coordinate(rock.getCurrentPosition().x() + partOfRock.x() + jetDirection,
                    rock.getCurrentPosition().y() + partOfRock.y());
            if (ROCK_TOWER.contains(newPosition)) {
                return;
            }
        }
        rock.setCurrentPosition(new Coordinate(rock.getCurrentPosition().x() + jetDirection, rock.getCurrentPosition().y()));
    }

    //Returns true, if rock cannot move down
    private static boolean moveDown(Rock rock) {
        for (Coordinate partOfRock : rock.getPartRelativePositions()) {
            //Check against floor
            if (rock.getCurrentPosition().y() + partOfRock.y() - 1 == 0) {
                return true;
            }
            //Check against rockTower
            Coordinate newPosition = new Coordinate(rock.getCurrentPosition().x() + partOfRock.x(),
                    rock.getCurrentPosition().y() + partOfRock.y() - 1);
            if (ROCK_TOWER.contains(newPosition)) {
                return true;
            }
        }
        rock.setCurrentPosition(new Coordinate(rock.getCurrentPosition().x(), rock.getCurrentPosition().y() - 1));
        return false;
    }
}