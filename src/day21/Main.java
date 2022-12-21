package day21;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Main {

    private static final Map<String, MonkeyJob> monkeyJobMap = new HashMap<>();
    public static final String HUMAN = "humn";
    public static final String ROOT = "root";

    public static void main(String[] args) {

        File file = new File("resources\\day21\\input.txt");

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            while ((st = br.readLine()) != null) {
                String[] parts = st.split(" ");
                String monkeyName = parts[0].replace(":", "");
                if (parts.length < 4) {
                    MonkeyYellJob monkeyYellJob = new MonkeyYellJob(monkeyJobMap, Long.parseLong(parts[1]));
                    if (HUMAN.equals(monkeyName)) {
                        monkeyYellJob.setReliesOnHuman(true);
                    }
                    monkeyJobMap.put(monkeyName, monkeyYellJob);
                } else {
                    MonkeyCalculateJob monkeyJob = new MonkeyCalculateJob(monkeyJobMap, parts[1], parts[2], parts[3]);
                    monkeyJobMap.put(monkeyName, monkeyJob);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        MonkeyJob rootMonkey = monkeyJobMap.get(ROOT);

        System.out.println("Part 1:");
        System.out.println(rootMonkey.getValue());

        MonkeyJob firstRootMonkey = monkeyJobMap.get(((MonkeyCalculateJob) rootMonkey).getFirstArgument());
        MonkeyJob secondRootMonkey = monkeyJobMap.get(((MonkeyCalculateJob) rootMonkey).getSecondArgument());
        long yellHuman = firstRootMonkey.isReliesOnHuman()
                ? firstRootMonkey.getHumanValue(secondRootMonkey.getValue())
                : secondRootMonkey.getHumanValue(firstRootMonkey.getValue());


        System.out.println("Part 2:");
        System.out.println(yellHuman);
        System.out.println("Finished");
    }
}