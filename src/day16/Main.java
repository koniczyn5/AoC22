package day16;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class Main {

    private static final Map<String, Valve> valves = new HashMap<>();
    private static final Map<String, Map<String, Integer>> distancesBetweenValves = new HashMap<>();
    private static final List<Set<String>> combinationsOfValves = new ArrayList<>();

    public static void main(String[] args) {

        File file = new File("resources\\day16\\input.txt");
        Set<String> usefulValveNames = new HashSet<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            while ((st = br.readLine()) != null) {
                String[] stParts = st.split(" ");
                String valveName = stParts[1];
                int valveFlowRate = Integer.parseInt(stParts[4].split("=")[1].split(";")[0]);
                Valve valve = new Valve(valveName, valveFlowRate);
                for (int i = 9; i < stParts.length; i++) {
                    valve.getLeadsTo().add(stParts[i].replace(",", ""));
                }
                valves.put(valveName, valve);
                if (valveFlowRate > 0) {
                    usefulValveNames.add(valveName);
                }
                distancesBetweenValves.put(valve.getName(), new HashMap<>());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Valve valve : valves.values()) {
            for (Valve otherValve : valves.values()) {
                if (!valve.getName().equals(otherValve.getName())) {
                    calculateDistanceBetweenValves(valve, otherValve, Collections.EMPTY_LIST);
                }
            }
        }

        Queue<Approach> approaches = new ArrayDeque<>();
        approaches.add(new Approach("AA"));
        List<Approach> finalApproaches = new ArrayList<>();

        simulateOpeningValves(usefulValveNames, approaches, finalApproaches);

        System.out.println("Part 1:");
        System.out.println(
                finalApproaches.stream()
                        .map(Approach::getPressureReleased)
                        .max(Integer::compareTo)
                        .orElse(0)
        );

        getNElementCombinations(usefulValveNames.toArray(new String[0]),
                usefulValveNames.size() / 2,
                0,
                0,
                new String[usefulValveNames.size() / 2]);

        int bestPressureReleasedWithElephantsHelp = 0;

        for (Set<String> myValves : combinationsOfValves) {
            Set<String> elephantValves = new HashSet<>(usefulValveNames);
            elephantValves.removeAll(myValves);

            Queue<Approach> myApproaches = new ArrayDeque<>();
            myApproaches.add(new Approach("AA"));
            myApproaches.peek().setTimeLeft(26);
            List<Approach> myFinalApproaches = new ArrayList<>();

            simulateOpeningValves(myValves, myApproaches, myFinalApproaches);

            Queue<Approach> elephantApproaches = new ArrayDeque<>();
            elephantApproaches.add(new Approach("AA"));
            elephantApproaches.peek().setTimeLeft(26);
            List<Approach> elephantFinalApproaches = new ArrayList<>();

            simulateOpeningValves(elephantValves, elephantApproaches, elephantFinalApproaches);

            int pressureReleasedWithElephantsHelp = myFinalApproaches.stream().map(Approach::getPressureReleased).max(Integer::compareTo).orElse(0) +
                    elephantFinalApproaches.stream().map(Approach::getPressureReleased).max(Integer::compareTo).orElse(0);
            if (pressureReleasedWithElephantsHelp > bestPressureReleasedWithElephantsHelp) {
                bestPressureReleasedWithElephantsHelp = pressureReleasedWithElephantsHelp;
            }
        }


        System.out.println("Part 2:");
        System.out.println(bestPressureReleasedWithElephantsHelp);
        System.out.println("Finished");
    }

    private static void simulateOpeningValves(Set<String> usefulValveNames, Queue<Approach> approaches, List<Approach> finalApproaches) {
        while (!approaches.isEmpty()) {
            Approach currentApproach = approaches.poll();
            Valve currentValve = valves.get(currentApproach.getCurrentValveName());
            for (String newDestination : usefulValveNames.stream().filter(st -> !st.equals(currentValve.getName())
                    && !currentApproach.getOpenedValves().contains(st)).toList()) {
                //Move to next valve
                Approach newApproach = new Approach(newDestination);
                newApproach.setPressurePerMinute(currentApproach.getPressurePerMinute());
                newApproach.getOpenedValves().addAll(currentApproach.getOpenedValves());
                int distanceBetween = distancesBetweenValves.get(currentValve.getName()).get(newDestination);
                if (currentApproach.getTimeLeft() <= distanceBetween) {
                    newApproach.setPressureReleased(currentApproach.getPressureReleased() + newApproach.getPressurePerMinute() * currentApproach.getTimeLeft());
                    newApproach.setTimeLeft(0);
                    finalApproaches.add(newApproach);
                    continue;
                }
                newApproach.setPressureReleased(currentApproach.getPressureReleased() + newApproach.getPressurePerMinute() * distanceBetween);
                newApproach.setTimeLeft(currentApproach.getTimeLeft() - distanceBetween);
                //Check and if possible open valve
                int newValveFlowRate = valves.get(newDestination).getFlowRate();
                if (newValveFlowRate > 0 && !newApproach.getOpenedValves().contains(newDestination)) {
                    newApproach.setTimeLeft(newApproach.getTimeLeft() - 1);
                    newApproach.setPressureReleased(newApproach.getPressureReleased() + newApproach.getPressurePerMinute());
                    newApproach.setPressurePerMinute(newApproach.getPressurePerMinute() + newValveFlowRate);
                    newApproach.getOpenedValves().add(newDestination);
                    if (newApproach.getTimeLeft() == 0) {
                        finalApproaches.add(newApproach);
                        continue;
                    }
                }
                approaches.add(newApproach);
            }
            if (currentApproach.getOpenedValves().size() == usefulValveNames.size()) {
                currentApproach.setPressureReleased(currentApproach.getPressureReleased() + currentApproach.getPressurePerMinute() * currentApproach.getTimeLeft());
                currentApproach.setTimeLeft(0);
                finalApproaches.add(currentApproach);
            }
        }
    }

    private static int calculateDistanceBetweenValves(Valve firstValve, Valve secondValve, List<String> currentChain) {
        AtomicReference<Integer> distanceBetween = new AtomicReference<>(distancesBetweenValves.get(firstValve.getName()).get(secondValve.getName()));
        if (distanceBetween.get() != null && distanceBetween.get() > 0) {
            return distanceBetween.get();
        }
        if (firstValve.getLeadsTo().contains(secondValve.getName())) {
            distancesBetweenValves.get(firstValve.getName()).put(secondValve.getName(), 1);
            distancesBetweenValves.get(secondValve.getName()).put(firstValve.getName(), 1);
            return 1;
        }
        List<String> newChain = new ArrayList<>(currentChain);
        newChain.add(firstValve.getName());
        firstValve.getLeadsTo().stream()
                .filter(st -> !currentChain.contains(st))
                .map(st -> calculateDistanceBetweenValves(valves.get(st), secondValve, newChain))
                .min(Integer::compareTo)
                .ifPresentOrElse(integer -> {
                    if (integer < valves.size()) {
                        distancesBetweenValves.get(firstValve.getName()).put(secondValve.getName(), integer + 1);
                        distancesBetweenValves.get(secondValve.getName()).put(firstValve.getName(), integer + 1);
                    }
                    distanceBetween.set(integer + 1);
                }, () -> distanceBetween.set(valves.size()));
        return distanceBetween.get();
    }

    private static void getNElementCombinations(String[] valves, int n, int readIndex, int saveIndex, String[] tmpArray) {
        if (saveIndex == n) {
            combinationsOfValves.add(new HashSet<>(Arrays.stream(tmpArray).toList()));
            return;
        }
        if (readIndex >= valves.length) {
            return;
        }
        tmpArray[saveIndex] = valves[readIndex];
        getNElementCombinations(valves, n, readIndex + 1, saveIndex + 1, tmpArray);
        getNElementCombinations(valves, n, readIndex + 1, saveIndex, tmpArray);
    }
}