package day9;

import commons.Coordinate;
import lombok.Data;
import lombok.ToString;

@Data
public class RopeKnot {

    private int x = 0;
    private int y = 0;
    @ToString.Exclude
    private final RopeKnot prevKnot;
    @ToString.Exclude
    private RopeKnot nextKnot;

    public Coordinate produceCoordinate() {
        return new Coordinate(x, y);
    }

    public boolean isInRange(RopeKnot other) {
        return Math.abs(this.getX() - other.getX()) <= 1
                && Math.abs(this.getY() - other.getY()) <= 1;
    }
}
