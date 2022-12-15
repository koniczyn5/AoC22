package day15;

import commons.Coordinate;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class Spot {
    private final Coordinate point;
    @EqualsAndHashCode.Exclude
    private SpotType spotType;


    public enum SpotType {
        SENSOR,
        BEACON,
        NOT_BEACON
    }
}
