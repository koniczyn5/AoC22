package day12;

import commons.Coordinate;
import lombok.Data;

@Data
public class Hill {
    private final int x;
    private final int y;
    private final int height;
    private boolean isVisited = false;
    private int distanceToEnd = Integer.MAX_VALUE;

    public Coordinate produceCoordinate() {
        return new Coordinate(x, y);
    }
}
