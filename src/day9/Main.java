package day9;

import commons.Coordinate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {

    public static final int ROPE_LENGTH = 10;

    public static void main(String[] args) {

        File file = new File("resources\\day9\\input.txt");

        RopeKnot head = new RopeKnot(null);
        List<RopeKnot> rope = new ArrayList<>();
        rope.add(head);
        for (int i = 1; i < ROPE_LENGTH; i++) {
            RopeKnot prevKnot = rope.get(i - 1);
            RopeKnot newKnot = new RopeKnot(prevKnot);
            prevKnot.setNextKnot(newKnot);
            rope.add(newKnot);
        }
        RopeKnot tail = rope.get(rope.size() - 1);

        Set<Coordinate> visitedByTail = new HashSet<>();
        visitedByTail.add(tail.produceCoordinate());

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            while ((st = br.readLine()) != null) {
                String[] instructions = st.split(" ");

                int xMovement = 0;
                int yMovement = 0;

                switch (instructions[0].charAt(0)) {
                    case 'R' -> xMovement = 1;
                    case 'U' -> yMovement = 1;
                    case 'L' -> xMovement = -1;
                    case 'D' -> yMovement = -1;
                }

                for (int i = 0; i < Integer.parseInt(instructions[1]); i++) {
                    head.setX(head.getX() + xMovement);
                    head.setY(head.getY() + yMovement);

                    RopeKnot currentKnot = head.getNextKnot();
                    RopeKnot prevKnot = currentKnot.getPrevKnot();

                    while (currentKnot != null
                            && !currentKnot.isInRange(currentKnot.getPrevKnot())) {
                        moveKnot(currentKnot, prevKnot);

                        prevKnot = currentKnot;
                        currentKnot = currentKnot.getNextKnot();
                    }
                    visitedByTail.add(tail.produceCoordinate());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Answer:");
        System.out.println(visitedByTail.size());
        System.out.println("Finished");
    }

    public static void moveKnot(RopeKnot currentKnot, RopeKnot prevKnot) {
        int xDiff = prevKnot.getX() - currentKnot.getX();
        int yDiff = prevKnot.getY() - currentKnot.getY();

        int xMovement = (int) Math.signum(xDiff);
        int yMovement = (int) Math.signum(yDiff);

        currentKnot.setX(currentKnot.getX() + xMovement);
        currentKnot.setY(currentKnot.getY() + yMovement);
    }

}

