package day9;

import lombok.Data;

@Data
public final class Coordinate {
    private final int x;
    private final int y;

    public Coordinate(RopeKnot knot) {
        this.x = knot.getX();
        this.y = knot.getY();
    }
}
