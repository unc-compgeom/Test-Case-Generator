package redBluePolygon;

import geometry.Polygon;
import javafx.collections.ObservableList;
import geometry.Edge;
import geometry.Point;
import predicate.Predicate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Vance Miller on 10/2/2014.
 */
public class Generate {
    /**
     * Make red polygons that do not intersect (except on line endpoints).
     * Make blue p...
     *
     * Red and blue can intersect
     */
    private static final int COLLISION_LIMIT = (int) Math.pow(2, 10);
    public static final int NUM_EDGES = 4;

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
        for (Polygon p : blue) {
            output.add(p + "");
        }

        // formatting for Jack
        output.set(0, red.size() + " " + blue.size() + " " + intersectionCount + "");
    }

    private static List<Polygon> generateNonIntersectingPolygons(int numPolygons, int min, int max) {
        // Random points
        Random AynRand = new Random();
        List<Polygon> polygons = new ArrayList<Polygon>(numPolygons / 2);
        int[] collisions = new int[numPolygons / 2];
        for (int i = 0; i < numPolygons / 2; i++) {
            Point p0 = new Point(AynRand.nextInt(max - min) + min, AynRand.nextInt(max - min) + min);
            Point p1 = new Point(AynRand.nextInt(max - min) + min, AynRand.nextInt(max - min) + min);
            Point p2 = new Point(AynRand.nextInt(max - min) + min, AynRand.nextInt(max - min) + min);
            Polygon candidate = new Polygon();
            candidate.add(p0);
            candidate.add(p1);
            candidate.add(p2);
            boolean doesNotIntersect = true;
            {
                int j = 0;
                for (Polygon p : polygons) {
                    if (Predicate.polygonIntersect(candidate, p)) {
                        collisions[j]++;
                        doesNotIntersect = false;
                        break;
                    }
                    j++;
                }
            }
            if (doesNotIntersect) {
                polygons.add(candidate);
            } else {
                // try again;
                i--;
            }
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
            System.out.print("Done: " + i * 200.0 / numPolygons + "%\r");
        }
        return polygons;
    }
}

