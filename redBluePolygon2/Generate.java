package redBluePolygon2;

import geometry.Point;
import geometry.Polygon;
import javafx.collections.ObservableList;
import predicate.Predicate;
import predicate.Predicate.Orientation;

import java.util.*;

/**
 * Created by Vance Miller on 10/2/2014.
 */
public class Generate {
    public static final int EDGES_PER_POLYGON = 3;

    public static void generate(ObservableList<String> output, int numPolygons,
                                int min, int max) {
        // replaced before generate() returns.
        output.add("Placeholder");

        // red polygons
        List<Polygon> red;
        red = generateNonIntersectingPolygons(numPolygons, min, max);
        for (Polygon p : red) {
            output.add(p + "");
        }
        // blue polygons
        List<Polygon> blue;
        blue = generateNonIntersectingPolygons(numPolygons, min, max);
        for (Polygon p : blue) {
            output.add(p + "");
        }
        // calculate number of Polygon intersections
        int intersectionCount = 0;
        for (Polygon b : blue) {
            for (Polygon r : red) {
                if (Predicate.polygonIntersect(r, b)) {
                    intersectionCount++;
                }
            }
        }
        // formatting for Matlab
        output.set(0, numPolygons * EDGES_PER_POLYGON + " " + numPolygons
                * EDGES_PER_POLYGON + " " + intersectionCount + "");
    }

    private static List<Polygon> generateNonIntersectingPolygons(
            int numPolygons, int min, int max) {
        Point head;
        // generate random points
        {
            Point p, q;
            int numPoints = numPolygons * EDGES_PER_POLYGON;
            // Insert all points into a set to ensure uniqueness
            Set<Point> points = new TreeSet<Point>(Point.comparator());
            Random r = new Random();
            // generate the head
            head = new Point(r.nextInt(max - min) + min, r.nextInt(max - min)
                    + min);
            points.add(head);
            // generate the rest
            p = head;
            int i = 1;
            while (i < numPoints) {
                for (; i < numPoints; i++) {
                    q = new Point(r.nextInt(max - min) + min, r.nextInt(max - min)
                            + min);
                    if (points.add(q)) {
                        p.next = q;
                        q.prev = p;
                        p = q;
                    } else {
                        i--;
                    }
                }
                boolean hasTriples = true;
                while (hasTriples) {
                    Point a, b, c = null;
                    List<Point> remove = new LinkedList<Point>();
                    Iterator<Point> ait, bit, cit;
                    hasTriples = false;
                    ait = points.iterator();
                    while (ait.hasNext()) {
                        a = ait.next();
                        bit = points.iterator();
                        while (bit.hasNext()) {
                            b = bit.next();
                            cit = points.iterator();
                            while (cit.hasNext()) {
                                c = cit.next();
                                if (a == b || b == c || c == a) {
                                    continue;
                                }
                                if (Predicate.orientation(a, b, c) == Orientation.COLINEAR) {
                                    hasTriples = true;
                                    remove.add(c);
                                    i--;
                                }
                            }
                        }
                    }
                    points.removeAll(remove);
                    remove.clear();
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
            // for each edge
            Iterator<Point> ait = points.iterator();
            Point a;
            do {
                a = ait.next();
                Iterator<Point> bit = points.iterator();
                Point b;
                do {
                    b = bit.next();
                    if (Predicate.edgeIntersect(a, b)) {
                        tangled = true;
                        if (!pathExists(a, b, true)) {
                            // merge into one polygon
                            splice(a, b);
                            numSplits--;
                        } else if (numSplits < numPolygons
                                && pathExists(a, b, true)) {
                            // split into two polygons
                            splice(a, b);
                            if (a.next.next == a) {
                                points.remove(a);
                                points.remove(a.next);
                            } else if (b.next.next == b) {
                                points.remove(b);
                                points.remove(b.next);
                            } else {
                                numSplits++;
                            }
                        } else {
                            // untangle
                            untangle(a, b, points);
                        }
                        break;
                    }
                } while (bit.hasNext());
                if (tangled) {
                    break;
                }
            } while (ait.hasNext());
            System.out
                    .print("Done: " + numSplits * 100.0 / numPolygons + "%\r");
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

    private static boolean pathExists(Point a1, Point b0, boolean nextPrev) {
        Point iterator = a1;
        if (nextPrev) {
            do {
                if (iterator == b0) {
                    return true;
                }
                iterator = iterator.next;
            } while (iterator != a1);
        } else {
            do {
                if (iterator == b0) {
                    return true;
                }
                iterator = iterator.prev;
            } while (iterator != a1);
        }
        return false;

    }

    static void untangle(Point a, Point b, List<Point> points) {
        Point tmp, iterator;

        a.next.prev = a.next.next;
        a.next.next = b.next;
        b.next.prev = a.next;

        a.next = b;
        b.next = b.prev;
        b.prev = a;

        // while point pointers are reversed, swap them
        iterator = b.next;
        while (iterator.next.prev != iterator || iterator.prev.next != iterator) {
            // do swap
            tmp = iterator.prev;
            iterator.prev = iterator.next;
            iterator.next = tmp;
            // next point
            iterator = iterator.next;
        }

        // fix the points list
        int i = points.indexOf(a);

        while (points.get(i).next != a) {
            Point next = points.get(i).next;
            i = (i + 1) % points.size();
            points.set(i, next);
        }
    }

    /**
     * Splice splits the polygon if there is a path from a to b and merges it
     * otherwise.
     *
     * @param a
     * @param b
     */
    private static void splice(Point a, Point b) {
        Point tmp = b.next;

        b.next = a.next;
        b.next.prev = b;

        a.next = tmp;
        a.next.prev = a;
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
