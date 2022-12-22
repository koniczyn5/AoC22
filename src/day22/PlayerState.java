package day22;

import javafx.geometry.Point2D;
import lombok.Data;

import java.util.Map;

@Data
public class PlayerState {
    private Point2D currentPosition;
    private Direction currentDirection = Direction.RIGHT;

    public static final Map<Direction, Point2D> increment = Map.of(
            Direction.RIGHT, new Point2D(1, 0),
            Direction.DOWN, new Point2D(0, 1),
            Direction.LEFT, new Point2D(-1, 0),
            Direction.UP, new Point2D(0, -1)
    );

    public void turn(char turnDirection) {
        if (turnDirection == 'R') {
            currentDirection = Direction.values()[Math.floorMod(currentDirection.ordinal() + 1, Direction.values().length)];
        } else {
            currentDirection = Direction.values()[Math.floorMod(currentDirection.ordinal() - 1, Direction.values().length)];
        }
    }

    public double getScore() {
        return 1000 * currentPosition.getY() + 4 * currentPosition.getX() + currentDirection.ordinal();
    }
}
