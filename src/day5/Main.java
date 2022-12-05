package day5;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {

        File file = new File("resources\\day5\\input.txt");

        List<String> startingCrates = new ArrayList<>();
        Map<Integer, Stack<Character>> crateStacksVersion9000 = new HashMap<>();
        Map<Integer, Stack<Character>> crateStacksVersion9001 = new HashMap<>();


        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            while (!(st = br.readLine()).isBlank()) {
                startingCrates.add(st);
            }
            Collections.reverse(startingCrates);
            crateStacksVersion9000 = getStartingStacksFromInput(startingCrates);
            crateStacksVersion9001 = getStartingStacksFromInput(startingCrates);
            while ((st = br.readLine()) != null) {
                List<String> instructionParts = Arrays.stream(st.split(" "))
                        .filter(StringUtils::isNotBlank)
                        .toList();
                int numberOfCratesToMove = Integer.parseInt(instructionParts.get(1));
                int fromStack = Integer.parseInt(instructionParts.get(3));
                int toStack = Integer.parseInt(instructionParts.get(5));
                List<Character> listOfMovingCrates = new ArrayList<>();
                for (int i = 0; i < numberOfCratesToMove; i++) {
                    Character crate = crateStacksVersion9000.get(fromStack).pop();
                    crateStacksVersion9000.get(toStack).push(crate);

                    listOfMovingCrates.add(crateStacksVersion9001.get(fromStack).pop());
                }
                Collections.reverse(listOfMovingCrates);
                for (Character crate : listOfMovingCrates) {
                    crateStacksVersion9001.get(toStack).push(crate);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        System.out.println("Part 1:");
        for (Stack<Character> createStack : crateStacksVersion9000.values()) {
            System.out.print(createStack.peek());
        }
        System.out.println();
        System.out.println("Part 2:");
        for (Stack<Character> createStack : crateStacksVersion9001.values()) {
            System.out.print(createStack.peek());
        }
        System.out.println();
        System.out.println("Finished");
    }

    private static Map<Integer, Stack<Character>> getStartingStacksFromInput(List<String> startingCrates) {
        Map<Integer, Stack<Character>> startingCrateStacks = new HashMap<>();
        String stackNumbers = startingCrates.get(0);
        for (int number : Arrays.stream(stackNumbers.split(" "))
                .filter(StringUtils::isNotEmpty)
                .map(Integer::valueOf)
                .toList()) {
            startingCrateStacks.put(number, new Stack<>());
        }
        for (String cratesRow : startingCrates.subList(1, startingCrates.size())) {
            int stackCounter = 1;
            for (int i = 0; i < cratesRow.length(); i += 3) {
                String crate = cratesRow.substring(i, i + 3);
                if (!crate.isBlank()) {
                    startingCrateStacks.get(stackCounter).push(crate.charAt(1));
                }
                i++;
                stackCounter++;
            }
        }
        return startingCrateStacks;
    }
}

