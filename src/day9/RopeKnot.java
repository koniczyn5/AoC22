package day9;

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

    public boolean isInRange(RopeKnot other) {
        return Math.abs(this.getX() - other.getX()) <= 1
                && Math.abs(this.getY() - other.getY()) <= 1;
    }
}
