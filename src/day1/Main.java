package day1;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        File file = new File("resources\\day1\\input.txt");

        Map<Integer, Long> caloriesPerElf = new HashMap<>();
        int elfNumber = 1;

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            while ((st = br.readLine()) != null) {
                if(StringUtils.isEmpty(st)) {
                    elfNumber++;
                } else {
                    caloriesPerElf.merge(elfNumber, Long.valueOf(st), Long::sum);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        caloriesPerElf.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .limit(3)
                .reduce((entry1, entry2) -> {
                    entry1.setValue(entry1.getValue() + entry2.getValue());
                    return entry1;
                })
                        .ifPresent(entry -> System.out.println(entry.getValue()));

        System.out.println("Finished");
    }
}
