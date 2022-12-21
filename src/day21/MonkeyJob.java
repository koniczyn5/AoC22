package day21;

import lombok.Data;

@Data
public abstract class MonkeyJob {

    private boolean reliesOnHuman;

    public abstract long getValue();

    public abstract long getHumanValue(long equalTo);
}
