package geometry;

import java.util.Comparator;

public class Point {
    public final long x;
    public final long y;
    public Point next, prev;
    public boolean valid = true;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
        this.next = this;
        this.prev = this;
    }

    private Point(long x, long y) {
        this.x = x;
        this.y = y;
        this.next = this;
        this.prev = this;
    }

    /**
     * Subtracts another point's coordinates from this point's coordinates.
     * Returns a new point with location (this.x - p.x, this.y - p.y).
     *
     * @return a new point at location (this.x - p.x, this.y - p.y).
     */
    public Point sub(final Point p1) {
        return new Point(x - p1.x, y - p1.y);
    }

    @Override
    public String toString() {
        return x + " " + y;
    }


    public static Comparator<Point> comparator() {
        return new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                if (o1.x < o2.x) {
                    return -1;
                } else if (o1.x > o2.x) {
                    return 1;
                } else if (o1.y < o2.y) {
                    return -1;
                } else if (o1.y > o2.y) {
                    return 1;
                } else {
                    return 0;
                }
            }
        };
    }

    public double dot(final Point v) {
        return x * v.x + y * v.y;
    }

}
