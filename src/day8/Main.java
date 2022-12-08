package day8;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        File file = new File("resources\\day8\\input.txt");
        List<List<Tree>> forest = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            while ((st = br.readLine()) != null) {
                List<Tree> currentRow = new ArrayList<>();
                forest.add(currentRow);
                for (char c : st.toCharArray()) {
                    currentRow.add(new Tree(c));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < forest.size(); i++) {
            for (int j = 0; j < forest.size(); j++) {
                Tree currentTree = forest.get(i).get(j);
                //Check left
                for (int k = j - 1; k >= 0; k--) {
                    currentTree.setLeftScenic(currentTree.getLeftScenic() + 1);
                    if (currentTree.getHeight() <= forest.get(i).get(k).getHeight()) {
                        break;
                    }
                }
                //Check right
                for (int k = j + 1; k < forest.size(); k++) {
                    currentTree.setRightScenic(currentTree.getRightScenic() + 1);
                    if (currentTree.getHeight() <= forest.get(i).get(k).getHeight()) {
                        break;
                    }
                }
                //Check top
                for (int k = i - 1; k >= 0; k--) {
                    currentTree.setTopScenic(currentTree.getTopScenic() + 1);
                    if (currentTree.getHeight() <= forest.get(k).get(j).getHeight()) {
                        break;
                    }
                }
                //Check bottom
                for (int k = i + 1; k < forest.size(); k++) {
                    currentTree.setBottomScenic(currentTree.getBottomScenic() + 1);
                    if (currentTree.getHeight() <= forest.get(k).get(j).getHeight()) {
                        break;
                    }
                }
                //Determine visibility of tree
                int leftBorderTreeHeight = forest.get(i).get(0).getHeight();
                int rightBorderTreeHeight = forest.get(i).get(forest.size() - 1).getHeight();
                int topBorderTreeHeight = forest.get(0).get(j).getHeight();
                int bottomBorderTreeHeight = forest.get(forest.size() - 1).get(j).getHeight();
                if (isVisibleFromSide(j, currentTree.getLeftScenic(), currentTree.getHeight(), leftBorderTreeHeight)
                        || isVisibleFromSide(forest.size() - 1 - j, currentTree.getRightScenic(), currentTree.getHeight(), rightBorderTreeHeight)
                        || isVisibleFromSide(i, currentTree.getTopScenic(), currentTree.getHeight(), topBorderTreeHeight)
                        || isVisibleFromSide(forest.size() - 1 - i, currentTree.getBottomScenic(), currentTree.getHeight(), bottomBorderTreeHeight)
                ) {
                    currentTree.setVisible(true);
                }
            }
        }

        long visibleTrees = forest.stream()
                .flatMap(Collection::stream)
                .filter(Tree::isVisible)
                .count();

        long bestScenicScore = forest.stream()
                .flatMap(Collection::stream)
                .map(Tree::getTotalScenicScore)
                .max(Integer::compareTo)
                .orElse(0);

        System.out.println("Part 1:");
        System.out.println(visibleTrees);
        System.out.println("Part 2:");
        System.out.println(bestScenicScore);
        System.out.println("Finished");
    }

    private static boolean isVisibleFromSide(int coordinate,
                                             int sideScenicScore,
                                             int treeHeight,
                                             int borderTreeHeight) {
        return (sideScenicScore == coordinate
                && treeHeight > borderTreeHeight)
                || sideScenicScore == 0;
    }
}