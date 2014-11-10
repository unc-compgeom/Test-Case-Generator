package redBluePolygon2;

import geometry.Point;
import geometry.Polygon;
import javafx.collections.ObservableList;
import predicate.Predicate;

import java.util.*;

/**
 * Created by Vance Miller on 10/2/2014.
 */
public class Generate {
    public static final int NUM_EDGES = 3;

    public static void generate(ObservableList<String> output, int numPolygons,
                                int min, int max) {
        // formatting for Jack, replaced at the end
        output.add("Placeholder");

        // red edges
        List<Polygon> red;
        red = generateNonIntersectingPolygons(numPolygons, min, max);
        for (Polygon p : red) {
            output.add(p + "");
        }
        // blue edges
        List<Polygon> blue;
        blue = generateNonIntersectingPolygons(numPolygons, min, max);
        for (Polygon p : blue) {
            output.add(p + "");
        }

        // calculate number of Polygon intersections
        int intersectionCount = 0;
        for (Polygon b : blue) {
            for (Polygon r : red) {
                // if (Predicate.polygonIntersect(r, b)) {
                intersectionCount++;
                // }
            }
        }

        // formatting for Jack
        output.set(0, numPolygons * NUM_EDGES + " " + numPolygons * NUM_EDGES + " "
                + intersectionCount + "");
    }

    private static List<Polygon> generateNonIntersectingPolygons(
            int numPolygons, int min, int max) {
        Point head;
        // generate random points
        {
            Point p, q;
            // Use a set to ensure uniqueness
            Set<Point> points = new TreeSet<Point>(Point.comparator());
            Random AynRand = new Random();
            int numPoints = numPolygons * NUM_EDGES;
            // generate the head
            head = new Point(AynRand.nextInt(max - min) + min,
                    AynRand.nextInt(max - min) + min);
            p = head;
            points.add(head);
            // generate the rest
            for (int i = 1; i < numPoints; i++) {
                q = new Point(AynRand.nextInt(max - min) + min, AynRand.nextInt(max
                        - min)
                        + min);
                if (points.add(q)) {
                    p.next = q;
                    q.prev = p;
                    p = q;
                } else {
                    i--;
                }
            }
            p.next = head;
            head.prev = p;
        }
        // insert all points into a linked list
        LinkedList<Point> points = new LinkedList<Point>();
        {
            Point it = head;
            do {
                points.add(it);
                it = it.next;
            } while (it != head);
        }

        // untangle intersections and create polygons
        int numSplits = 1;
        boolean tangled = true;
        while (tangled) {
            tangled = false;
            // for each crossing
            Iterator<Point> ait = points.iterator();
            Point a0, a1;
            do {
                a0 = ait.next();
                a1 = a0.next;
                Iterator<Point> bit = points.iterator();
                Point b0, b1;
                do {
                    b0 = bit.next();
                    b1 = b0.next;
                    if (Predicate.edgeIntersect(a0, a1, b0, b1)) {
                        tangled = true;
                        if (numSplits < numPolygons && !adjacent(b1, a0) && !adjacent(a1, b0)) {
                            // split into two polygons
                            split(a0, a1, b0, b1);
                            numSplits++;
                        } else {
                            // untangle
                            untangle(a0, a1, b0, b1);
                            reverseList(a1, b0, points);
                        }
                    }
                } while (bit.hasNext());
            } while (ait.hasNext());
            System.out.print("Done: " + numSplits * 100.0 / numPolygons + "%\r");
        }
        // separate into list of polygons

        List<Polygon> polygons = new ArrayList<Polygon>(numSplits);
        {
            Point iterator = head;
            Polygon tmp = new Polygon();
            for (Point p : points) {
                if (p != iterator) {
                    polygons.add(tmp);
                    tmp = new Polygon();
                    iterator = p;
                }
                tmp.add(p);
                iterator = iterator.next;
            }
            polygons.add(tmp);
        }
        return polygons;
    }

    private static boolean adjacent(Point a, Point b) {
        return a.next == b;
    }

    /**
     * Swaps the positions of all elements between start and stop inclusive. Stop must come after start in the list.
     *
     * @param start
     * @param stop
     * @param list
     */
    private static void reverseList(Point start, Point stop, List<Point> list) {
        int begin = list.indexOf(start);
        int end = list.indexOf(stop);
        int numSwaps = (end - begin) / 2;
        for (int i = 0; i <= numSwaps; i++) {
            Point tmp = list.get(begin + i);
            list.set(begin + i, list.get(end - i));
            list.set(end - i, tmp);
        }
    }

    private static void untangle(Point a0, Point a1, Point b0, Point b1) {
        Point tmp, iterator, start, end;
        start = a1.prev;
        end = b0.next;

        iterator = b0;
        // reverse b0 to a1
        do {
            tmp = iterator.prev;
            iterator.prev = iterator.next;
            iterator.next = tmp;
            iterator = tmp;
        } while (iterator != a1);

        start.next = b0;
        b0.prev = start;
        end.next = a1;
        a1.prev = end;
    }

    private static void split(Point a0, Point a1, Point b0, Point b1) {
        a0.next = b1;
        b1.prev = a0;
        b0.next = a1;
        a1.prev = b0;
    }
}

/**
 * random points
 *
 * pick cycle, something that avoids loops
 *
 * need to reverse list when swapping edges
 *
 * Get any number of polygons that you want
 *
 * Split loops when possible. When forced to merge, you avoid creating bad cases
 *
 * random triangulation, Hamiltonian cycle
 *
 */
