package day19;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.util.Comparator.comparing;

public class Main {

    private static final int PART_ONE_TIME = 24;
    private static final int PART_TWO_TIME = 32;

    public static void main(String[] args) {

        File file = new File("resources\\day19\\input.txt");
        List<Blueprint> blueprints = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            while ((st = br.readLine()) != null) {
                String[] parts = st.split(" ");
                blueprints.add(new Blueprint(
                        Integer.parseInt(parts[6]),
                        Integer.parseInt(parts[12]),
                        Integer.parseInt(parts[18]),
                        Integer.parseInt(parts[21]),
                        Integer.parseInt(parts[27]),
                        Integer.parseInt(parts[30])
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        long sumOfQualities = blueprints.stream()
                .map(blueprint -> {
                    long numberOfGeodes = getMaxGeodesForBlueprint(blueprint, PART_ONE_TIME);
                    int blueprintNumber = blueprints.indexOf(blueprint) + 1;
                    return blueprintNumber * numberOfGeodes;
                })
                .reduce(Long::sum)
                .orElse(0L);


        System.out.println("Part 1:");
        System.out.println(sumOfQualities);

        List<Long> geodesInFirst3Blueprints = blueprints.stream()
                .limit(3)
                .map(blueprint -> getMaxGeodesForBlueprint(blueprint, PART_TWO_TIME))
                .toList();

        System.out.println("Part 2:");
        System.out.println(
                geodesInFirst3Blueprints.stream()
                        .reduce((integer, integer2) -> integer * integer2)
                        .orElse(0L)
        );
        System.out.println("Finished");
    }

    private static long getMaxGeodesForBlueprint(Blueprint blueprint, int maxTime) {
        List<State> finalStates = new ArrayList<>();
        TopUniqueElements<State> statesToCheck = new TopUniqueElements<>(1_000_000, comparing(State::score));
        statesToCheck.add(new State(0, 0, 0, 0, 0, 1, 0, 0, 0));

        while (!statesToCheck.isEmpty()) {
            State currentState = statesToCheck.poll();
            if (currentState.time() == maxTime) {
                finalStates.add(currentState);
                continue;
            }

            long currentTime = currentState.time();
            long currentOre = currentState.ore();
            long currentClay = currentState.clay();
            long currentObsidian = currentState.obsidian();
            long currentGeode = currentState.geode();
            long currentOreRobot = currentState.oreRobot();
            long currentClayRobot = currentState.clayRobot();
            long currentObsidianRobot = currentState.obsidianRobot();
            long currentGeodeRobot = currentState.geodeRobot();

            if (currentOre >= blueprint.geodeRobotOreCost()
                    && currentObsidian >= blueprint.geodeRobotObsidianCost()) {
                statesToCheck.add(new State(currentTime + 1,
                        currentOre + currentOreRobot - blueprint.geodeRobotOreCost(),
                        currentClay + currentClayRobot,
                        currentObsidian + currentObsidianRobot - blueprint.geodeRobotObsidianCost(),
                        currentGeode + currentGeodeRobot,
                        currentOreRobot,
                        currentClayRobot,
                        currentObsidianRobot,
                        currentGeodeRobot + 1
                ));
                continue;
            } else if (currentOre >= blueprint.obsidianRobotOreCost()
                    && currentClay >= blueprint.obsidianRobotClayCost()) {
                statesToCheck.add(new State(currentTime + 1,
                        currentOre + currentOreRobot - blueprint.obsidianRobotOreCost(),
                        currentClay + currentClayRobot - blueprint.obsidianRobotClayCost(),
                        currentObsidian + currentObsidianRobot,
                        currentGeode + currentGeodeRobot,
                        currentOreRobot,
                        currentClayRobot,
                        currentObsidianRobot + 1,
                        currentGeodeRobot
                ));
                continue;
            } else if (currentOre >= blueprint.oreRobotOreCost()) {
                statesToCheck.add(new State(currentTime + 1,
                        currentOre + currentOreRobot - blueprint.oreRobotOreCost(),
                        currentClay + currentClayRobot,
                        currentObsidian + currentObsidianRobot,
                        currentGeode + currentGeodeRobot,
                        currentOreRobot + 1,
                        currentClayRobot,
                        currentObsidianRobot,
                        currentGeodeRobot
                ));
            }

            if (currentOre >= blueprint.clayRobotOreCost()) {
                statesToCheck.add(new State(currentTime + 1,
                        currentOre + currentOreRobot - blueprint.clayRobotOreCost(),
                        currentClay + currentClayRobot,
                        currentObsidian + currentObsidianRobot,
                        currentGeode + currentGeodeRobot,
                        currentOreRobot,
                        currentClayRobot + 1,
                        currentObsidianRobot,
                        currentGeodeRobot
                ));
            }

            statesToCheck.add(new State(currentTime + 1,
                    currentOre + currentOreRobot,
                    currentClay + currentClayRobot,
                    currentObsidian + currentObsidianRobot,
                    currentGeode + currentGeodeRobot,
                    currentOreRobot,
                    currentClayRobot,
                    currentObsidianRobot,
                    currentGeodeRobot
            ));
        }

        return finalStates.stream()
                .map(State::geode)
                .max(Long::compareTo)
                .orElse(0L);
    }
}