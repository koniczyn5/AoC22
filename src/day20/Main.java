package day20;

import lombok.AllArgsConstructor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Main {

    public static final int ENCRYPTION_KEY = 811589153;

    public static void main(String[] args) {

        File file = new File("resources\\day20\\input.txt");
        List<Number> initialNumbers = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            while ((st = br.readLine()) != null) {
                initialNumbers.add(new Number(Long.parseLong(st)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Number> message = new LinkedList<>(initialNumbers);
        mix(message, initialNumbers);

        System.out.println("Part 1:");
        System.out.println(getGrooveCoordinates(message));

        List<Number> partTwoInitialNumbers = initialNumbers.stream()
                .map(number -> new Number(number.value * ENCRYPTION_KEY))
                .toList();
        List<Number> partTwoMessage = new LinkedList<>(partTwoInitialNumbers);

        for (int i = 0; i < 10; i++) {
            mix(partTwoMessage, partTwoInitialNumbers);
        }

        System.out.println("Part 2:");
        System.out.println(getGrooveCoordinates(partTwoMessage));
        System.out.println("Finished");
    }

    public static Long getGrooveCoordinates(List<Number> list) {
        Number Zero = list.stream()
                .filter(x -> x.value == 0)
                .findFirst().orElse(null);

        int ind = list.indexOf(Zero);

        return list.get((ind + 1000) % list.size()).value +
                list.get((ind + 2000) % list.size()).value +
                list.get((ind + 3000) % list.size()).value;
    }

    public static void mix(List<Number> toMix, List<Number> sequence) {
        long modVal = toMix.size() - 1L;
        for (Number n : sequence) {
            int index = toMix.indexOf(n);
            Number val = toMix.remove(index);
            long newIndex = Math.floorMod(index + n.value, modVal);
            toMix.add(Math.toIntExact(newIndex), val);
        }
    }
}

@AllArgsConstructor
class Number {
    public Long value;
}