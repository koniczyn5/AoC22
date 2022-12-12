package day12;

import lombok.Data;

@Data
public final class Coordinate {
    private final int x;
    private final int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordinate(Hill hill) {
        this.x = hill.getX();
        this.y = hill.getY();
    }
}
