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
        int numRed = 0, numBlue = 0;
        // replaced before generate() returns.
        output.add("Placeholder");

        // red polygons
        List<Polygon> red;
        red = generateNonIntersectingPolygons(numPolygons, min, max);
        for (Polygon p : red) {
            output.add(p + "");
            numRed += p.size();
        }
        // blue polygons
        List<Polygon> blue;
        blue = generateNonIntersectingPolygons(numPolygons, min, max);
        for (Polygon p : blue) {
            output.add(p + "");
            numBlue += p.size();
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
        output.set(0, numRed + " " + numBlue + " " + intersectionCount + "");
    }

    private static Point generatePolygon(int numPoints, int min, int max) {
        Point p, q, head;
        // Insert all points into a set to ensure uniqueness
        Set<Point> points = new TreeSet<Point>(Point.comparator());
        Polygon polygon = new Polygon();
        Random r = new Random();
        // generate the polygon
        while (points.size() < numPoints) {
            while (points.size() < numPoints) {
                p = new Point(r.nextInt(max - min) + min, r.nextInt(max - min)
                        + min);
               if (points.add(p)) {
                   polygon.add(p);
               }
            }
            boolean hasTriples = true;
            List<Point> toRemove = new LinkedList<Point>();
            while (hasTriples) {
                hasTriples = false;
                Point a, b, c;
                Iterator<Point> ait, bit, cit;
                ait = points.iterator();
                loop1: while (ait.hasNext()) {
                    a = ait.next();
                    bit = points.iterator();
                    while (bit.hasNext() && a.valid) {
                        b = bit.next();
                        if (b == a) continue;
                        cit = points.iterator();
                        while (cit.hasNext() && b.valid) {
                            c = cit.next();
                            if (b == c || c == a || !c.valid) {
                                continue;
                            }
                            if (Predicate.orientation(a, b, c) == Orientation.COLINEAR) {
                                hasTriples = true;
                                c.valid = false;
                                toRemove.add(c);
                            }
                        }
                    }
                }
                if (hasTriples) {
                    points.removeAll(toRemove);
                    polygon.removeAll(toRemove);
                    toRemove.clear();
                }
            }
        }

        Iterator<Point> it = polygon.iterator();
        head = it.next();
        p = head;
        q = head;
        while (it.hasNext()) {
            q = it.next();
            p.next = q;
            q.prev = p;
            p = q;
        }
        q.next = head;
        head.prev = q;
        return head;
    }

    private static List<Polygon> generateNonIntersectingPolygons(
            int numPolygons, int min, int max) {

        List<Point> polygons = null;
        double epsilon = 0.01;
        while (polygons == null || polygons.size() < numPolygons * epsilon ) {
            // make a special polygon
            Point head = generatePolygon(numPolygons * EDGES_PER_POLYGON, min, max);

            // untangle intersections and create polygons
            polygons = createPolygons(head, numPolygons);
        }

        // convert to polygon objects
        List<Polygon> convertedPolygons = new ArrayList<Polygon>(polygons.size());
        for (Point p : polygons) {
            Polygon tmp = new Polygon();
            Point iterator = p;
            do {
                tmp.add(iterator);
                iterator = iterator.next;
            } while (iterator != p);
            convertedPolygons.add(tmp);
        }
        return convertedPolygons;
    }

    private static List<Point> createPolygons(Point head, int target) {
        // keep all polygons in a linked list
        List<Point> polygons = new LinkedList<Point>();
        polygons.add(head);
        boolean tangled = true;
        while (tangled) {
            tangled = false;
            // for all pairs of polygons
            Iterator<Point> polygon1It = polygons.iterator();
            if (!polygon1It.hasNext()) {
                return null;
            }
            Point polygon1;
            outerloop:
            do {
                polygon1 = polygon1It.next();
                Iterator<Point> polygon2It = polygons.iterator();
                Point polygon2;
                do {
                    polygon2 = polygon2It.next();
                    // for all pairs of edges in polygon1 and polygon2
                    Point p1 = polygon1;
                    do {
                        Point p2 = polygon2;
                        do {
                            // find intersections
                            if (Predicate.edgeIntersect(p1, p2)) {
                                tangled = true;
                                if (!pathExists(p1, p2, true)) {
                                    // merge into one polygon
                                    splice(p1, p2);
                                    polygons.remove(polygon1);
                                    polygons.remove(polygon2);
                                    polygons.add(p1);
                                } else if (polygons.size() < target
                                        && pathExists(p1, p2, true)) {
                                    // split into two polygons
                                    splice(p1, p2);
                                    polygons.remove(polygon1);
                                    polygons.remove(polygon2);
                                    polygons.add(p1);
                                    polygons.add(p2);
                                } else {
                                    // untangle
                                    untangle(p1, p2);
                                }
                                if (p1.next.next == p1) {
                                    polygons.remove(p1);
                                }
                                if (p2.next.next == p2) {
                                    polygons.remove(p2);
                                }
                                break outerloop;
                            }
                            p2 = p2.next;
                        } while (p2 != polygon2);
                        p1 = p1.next;
                    } while (p1 != polygon1);
                } while (polygon2It.hasNext());
            } while (polygon1It.hasNext());
            System.out
                    .print("Done: " + polygons.size() * 100.0 / target + "%\r");
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

    static void untangle(Point a, Point b) {
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