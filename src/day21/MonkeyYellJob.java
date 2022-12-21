package day21;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
public class MonkeyYellJob extends MonkeyJob {
    @ToString.Exclude
    private final Map<String, MonkeyJob> monkeys;
    private final long value;

    @Override
    public long getHumanValue(long equalTo) {
        return equalTo;
    }
}
