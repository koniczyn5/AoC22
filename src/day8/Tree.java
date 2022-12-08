package day8;

import lombok.Data;

@Data
public class Tree {
    private final int height;
    private boolean isVisible = false;
    private int leftScenic = 0;
    private int rightScenic = 0;
    private int topScenic = 0;
    private int bottomScenic = 0;

    public int getTotalScenicScore() {
        return leftScenic * rightScenic * topScenic * bottomScenic;
    }
}
