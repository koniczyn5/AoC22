package day19;

public record State(
        long time,
        long ore,
        long clay,
        long obsidian,
        long geode,
        long oreRobot,
        long clayRobot,
        long obsidianRobot,
        long geodeRobot
) {

    public long score() {
        return ore + clay * 2L + obsidian * 3L + geode * 4L;
    }
}
