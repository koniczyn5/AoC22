package day11;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.function.LongUnaryOperator;

@Data
public class Monkey {
    private long inspectionCount = 0;
    private final Queue<Long> items = new ArrayDeque<>();
    @ToString.Exclude
    private LongUnaryOperator operation;
    private long divisibleBy;
    private final Map<Boolean, Long> testOutcomeToMonkeyIndex = new HashMap<>();

    public long inspectItem() throws NullPointerException {
        inspectionCount++;
        return items.poll();
    }
}
