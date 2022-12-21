package day21;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Map;
import java.util.function.LongBinaryOperator;

@EqualsAndHashCode(callSuper = true)
@Data
public class MonkeyCalculateJob extends MonkeyJob {

    private static final Map<String, LongBinaryOperator> signToOperator = Map.of(
            "+", Long::sum,
            "-", (x, y) -> x - y,
            "*", (x, y) -> x * y,
            "/", (x, y) -> x / y
    );
    private static final Map<String, String> reverseSign = Map.of(
            "+", "-",
            "-", "+",
            "*", "/",
            "/", "*"
    );

    @ToString.Exclude
    private final Map<String, MonkeyJob> monkeys;
    private final String firstArgument;
    private final String sign;
    private final String secondArgument;
    private Long calculatedValue;

    @Override
    public long getValue() {
        if (calculatedValue == null) {
            MonkeyJob firstMonkey = monkeys.get(firstArgument);
            MonkeyJob secondMonkey = monkeys.get(secondArgument);

            setCalculatedValue(signToOperator.get(sign).applyAsLong(firstMonkey.getValue(),
                    secondMonkey.getValue()));

            if (firstMonkey.isReliesOnHuman() || secondMonkey.isReliesOnHuman()) {
                setReliesOnHuman(true);
            }
        }
        return calculatedValue;
    }

    @Override
    public long getHumanValue(long equalTo) {
        MonkeyJob relyingOnHumanMonkey = monkeys.get(firstArgument);
        MonkeyJob otherMonkey = monkeys.get(secondArgument);

        LongBinaryOperator operator = signToOperator.get(reverseSign.get(sign));
        long firstOperatorArgument = equalTo;
        long secondOperatorArgument = otherMonkey.getValue();

        if(otherMonkey.isReliesOnHuman()) {
            relyingOnHumanMonkey = otherMonkey;
            otherMonkey = monkeys.get(firstArgument);
            secondOperatorArgument = otherMonkey.getValue();

            if ("-".equals(sign) || "/".equals(sign)) {
                operator = signToOperator.get(sign);
                firstOperatorArgument = secondOperatorArgument;
                secondOperatorArgument = equalTo;
            }
        }

        return relyingOnHumanMonkey.getHumanValue(operator.applyAsLong(firstOperatorArgument, secondOperatorArgument));
    }
}
