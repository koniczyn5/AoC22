package commons;

public record Coordinate(long x, long y) implements Comparable<Coordinate> {

    @Override
    public int compareTo(Coordinate o) {
        if (this.equals(o)) {
            return 0;
        }
        int compareX = Long.compare(this.x, o.x);
        return compareX != 0
                ? compareX
                : Long.compare(this.y, o.y);
    }
}
