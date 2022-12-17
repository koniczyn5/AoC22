package day16;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Valve {
    private final String name;
    private final int flowRate;
    private final List<String> leadsTo = new ArrayList<>();
}
