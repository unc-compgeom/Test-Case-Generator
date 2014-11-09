package redBluePolygon;

import geometry.Point;
import geometry.Polygon;
import javafx.collections.ObservableList;
import predicate.Predicate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Created by Vance Miller on 10/2/2014.
 */
public class Generate {
    public static final int NUM_EDGES = 4;
    /**
     * Make red polygons that do not intersect (except on line endpoints).
     * Make blue p...
     * <p/>
     * Red and blue can intersect
     */
    private static final int COLLISION_LIMIT = (int) Math.pow(2, 10);

    public static void generate(ObservableList<String> output, int numPolygons, int min, int max) {
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
                if (Predicate.polygonIntersect(r, b)) {
                    intersectionCount++;
                }
            }
        }

        // formatting for Jack
        output.set(0, red.size()*3 + " " + blue.size()*3 + " " + intersectionCount + "");
    }

    private static List<Polygon> generateNonIntersectingPolygons(int numPolygons, int min, int max) {
        // Random points
        Random AynRand = new Random();
        List<Polygon> polygons = new ArrayList<Polygon>(numPolygons / 2);
        int[] collisions = new int[numPolygons / 2];
        for (int i = 0; i < numPolygons / 2; i++) {
            int numPoints = AynRand.nextInt(10);
            Polygon candidate = new Polygon();
            candidate.add(new Point(AynRand.nextInt(max - min) + min, AynRand.nextInt(max - min) + min));
            candidate.add(new Point(AynRand.nextInt(max - min) + min, AynRand.nextInt(max - min) + min));
            for(int points = 0; points < numPoints; points++) {
                Point pointCandidate = new Point(AynRand.nextInt(max - min) + min, AynRand.nextInt(max - min) + min);
                // ensure this point does not create self intersections on the polygon or intersect with other polygons
                {
                    Point p1;
                    Point p2;
                    Iterator<Point> it = candidate.iterator();
                    p1 = it.next();
                    while (it.hasNext()) {
                        p2 = it.next();
                        if (Predicate.edgeIntersect(p1, p2, pointCandidate, candidate.peekLast())) {
                            // check that the edge between the last point and the new point does not intersect others
                            pointCandidate = null;
                            // try again with a new point
                            break;
                        } else if (Predicate.edgeIntersect(p1, p2, pointCandidate, candidate.peekLast())) {
                            // check that the edge between the new point and the first point does not intersect anything
                            pointCandidate = null;
                            // try again with a new point
                            break;
                        } else {
                            p1 = p2;
                        }
                    }
                    if (pointCandidate != null) {
                        // last edge
                        p2 = candidate.get(0);
                        if (Predicate.edgeIntersect(p1, p2, pointCandidate, candidate.peekLast())) {
                            // check that the edge between the last point and the new point does not intersect others
                            pointCandidate = null;
                        } else if (Predicate.edgeIntersect(p1, p2, pointCandidate, candidate.peekLast())) {
                            // check that the edge between the new point and the first point does not intersect anything
                            pointCandidate = null;
                        }
                    }
                }
                if (pointCandidate == null) {
                    points--;
                } else {
                    // does not self intersect
                    // now check all other polygons in set
                    int polygonNumber = 0;
                    for (Polygon inSet : polygons) {
                        Point p1;
                        Point p2;
                        Iterator<Point> it = inSet.iterator();
                        p1 = it.next();
                        while (it.hasNext()) {
                            p2 = it.next();
                            if (Predicate.edgeIntersect(p1, p2, pointCandidate, candidate.peekLast())) {
                                // check that the edge between the last point and the new point does not intersect others
                                pointCandidate = null;
                                // try again with a new point
                                break;

                            } else if (Predicate.edgeIntersect(p1, p2, pointCandidate, candidate.peekLast())) {
                                // check that the edge between the new point and the first point does not intersect anything
                                pointCandidate = null;
                                // try again with a new point
                                break;
                            } else {
                                p1 = p2;
                            }
                        }
                        if (pointCandidate != null) {
                            // last edge
                            p2 = candidate.get(0);
                            if (Predicate.edgeIntersect(p1, p2, pointCandidate, candidate.peekLast())) {
                                // check that the edge between the last point and the new point does not intersect others
                                pointCandidate = null;
                            } else if (Predicate.edgeIntersect(p1, p2, pointCandidate, candidate.peekLast())) {
                                // check that the edge between the new point and the first point does not intersect anything
                                pointCandidate = null;
                            } else {
                                p1 = p2;
                            }
                            if (pointCandidate == null) {
                                collisions[polygonNumber]++;
                                break;
                            }
                        }
                    }
                    if (pointCandidate != null) {
                        candidate.add(pointCandidate);
                    } else {
                        points--;
                    }
                }
                polygons.add(candidate);

                boolean didRemove = false;
                for (int j = 0; j < collisions.length; j++) {
                    if (collisions[j] > COLLISION_LIMIT) {
                        polygons.remove(j);
                        didRemove = true;
                    }
                }
                if (didRemove) {
                    // reset collision counter
                    for (int j = 0; j < collisions.length; j++) {
                        collisions[j] = 0;
                    }
                }
            }
            System.out.print("Done: " + i * 200.0 / numPolygons + "%\r");
        }
        return polygons;
    }
}