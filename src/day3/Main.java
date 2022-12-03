package day3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {

        File file = new File("resources\\day3\\input.txt");

        List<String> inputs = new ArrayList<>();
        int sumOfPriorities = 0;
        int sumOfBadges = 0;

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            while ((st = br.readLine()) != null) {
                inputs.add(st);
                //I'm leaving this quick solution for part1, rather than using method getCommonTypeForBackpacks,
                //because it's faster
                Set<Character> typesInFirstCompartment = new HashSet<>();
                String firstCompartment = st.substring(0, st.length()/2);
                String secondCompartment = st.substring(st.length()/2);
                for (char c : firstCompartment.toCharArray()) {
                    typesInFirstCompartment.add(c);
                }
                for (char c : secondCompartment.toCharArray()){
                    if (typesInFirstCompartment.contains(c)) {
                        sumOfPriorities += getPriorityForType(c);
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < inputs.size(); i+=3) {
            sumOfBadges += getPriorityForType(getCommonTypeForBackpacks(inputs.subList(i,i+3)));
        }


        System.out.println("Part 1:");
        System.out.println(sumOfPriorities);
        System.out.println("Part 2:");
        System.out.println(sumOfBadges);
        System.out.println("Finished");
    }

    private static int getPriorityForType(char type) {
        if (((int) type) >= 97) {
            return ((int) type) - 96;
        } else {
            return ((int) type) - 38;
        }
    }

    private static char getCommonTypeForBackpacks(List<String> backpacks) {
        Set<Character> commonTypes = new HashSet<>();
        for (String backpack : backpacks) {
            Set<Character> typesInBackpack = new HashSet<>();
            for (char c : backpack.toCharArray()) {
                typesInBackpack.add(c);
            }
            commonTypes.retainAll(typesInBackpack);
            if (commonTypes.isEmpty()) {
                commonTypes.addAll(typesInBackpack);
            }
        }
        if (commonTypes.size()==1) {
            return commonTypes.stream().findFirst().orElse((char) 38);
        } else {
            throw new IllegalArgumentException("More than one common type of item in backpacks");
        }
    }
}
