package day12;

import lombok.Data;

@Data
public class Hill {
    private final int x;
    private final int y;
    private final int height;
    private boolean isVisited = false;
    private int distanceToEnd = Integer.MAX_VALUE;
}
