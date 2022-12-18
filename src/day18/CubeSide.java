package day18;

public enum CubeSide {
    RIGHT,
    LEFT,
    TOP,
    BOTTOM,
    FRONT,
    BACK;

    public CubeSide getOpposite() {
        switch (this) {
            case RIGHT -> {
                return LEFT;
            }
            case LEFT -> {
                return RIGHT;
            }
            case TOP -> {
                return BOTTOM;
            }
            case BOTTOM -> {
                return TOP;
            }
            case FRONT -> {
                return BACK;
            }
            case BACK -> {
                return FRONT;
            }
            default -> {
                return RIGHT;
            }
        }
    }
}
