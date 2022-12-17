package day17;

import commons.Coordinate;
import lombok.Data;

import java.util.List;


/**
 * Every rock currentPosition is it the lowest leftmost part
 */
@Data
public class Rock {
    private Coordinate currentPosition;
    private final List<Coordinate> partRelativePositions;
}
