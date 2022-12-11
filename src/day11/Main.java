package day11;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Main {

    private final static int NUMBER_OF_ROUNDS = 20;

    public static void main(String[] args) {

        File file = new File("resources\\day11\\input.txt");
        List<Monkey> monkeys = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            Monkey readCurrentMonkey = null;
            while ((st = br.readLine()) != null) {
                if (st.isBlank()) {
                    continue;
                }
                String[] instructions = Arrays.stream(st.split(" "))
                        .filter(StringUtils::isNotBlank)
                        .toArray(String[]::new);

                switch (instructions[0]) {
                    case "Monkey" -> {
                        readCurrentMonkey = new Monkey();
                        monkeys.add(readCurrentMonkey);
                    }
                    case "Starting" -> {
                        for (int i = 2; i < instructions.length; i++) {
                            readCurrentMonkey.getItems().add(
                                    Long.parseLong(instructions[i].replace(",", ""))
                            );
                        }
                    }
                    case "Operation:" -> readCurrentMonkey.setOperation((long old) -> {
                        long secondArgument = "old".equals(instructions[5])
                                ? old
                                : Long.parseLong(instructions[5]);
                        return "*".equals(instructions[4])
                                ? old * secondArgument
                                : old + secondArgument;
                    });
                    case "Test:" -> readCurrentMonkey.setDivisibleBy(Integer.parseInt(instructions[3]));
                    case "If" -> readCurrentMonkey.getTestOutcomeToMonkeyIndex().put(
                            Boolean.parseBoolean(instructions[1].replace(":", "")),
                            Long.parseLong(instructions[5]));
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        long commonDivider = monkeys.stream()
                .map(Monkey::getDivisibleBy)
                .reduce((aLong, aLong2) -> aLong*aLong2)
                .orElse(1L);

        for (int i = 0; i < NUMBER_OF_ROUNDS; i++) {
            monkeys.forEach(monkey -> {
                while (!monkey.getItems().isEmpty()) {
                    long currentItem = monkey.inspectItem();
                    currentItem = monkey.getOperation().applyAsLong(currentItem);
                    currentItem /= 3; //Only applies to part 1 of puzzle
                    currentItem %= commonDivider;
                    long monkeyToThrow = monkey.getTestOutcomeToMonkeyIndex().get(currentItem % monkey.getDivisibleBy() == 0);
                    monkeys.get((int) monkeyToThrow).getItems().add(currentItem);
                }
            });
        }

        System.out.println("Answer:");
        System.out.println(monkeys.stream()
                .map(Monkey::getInspectionCount)
                .sorted(Comparator.reverseOrder())
                .limit(2)
                .reduce((integer, integer2) -> integer * integer2)
                .orElse(0L)
        );
        System.out.println("Finished");
    }
}