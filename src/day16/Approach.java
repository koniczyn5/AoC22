package day16;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

@Data
public class Approach {
    private final String currentValveName;
    private int timeLeft = 30;
    @EqualsAndHashCode.Exclude
    private int pressureReleased = 0;
    private int pressurePerMinute = 0;
    @EqualsAndHashCode.Include
    private Set<String> openedValves = new HashSet<>();
}
