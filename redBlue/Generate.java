package redBlue;

import geometry.Edge;
import geometry.Point;
import javafx.collections.ObservableList;
import predicate.Predicate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Generate {
    private static final int COLLISION_LIMIT = (int) Math.pow(2, 10);

    public static void generate(ObservableList<String> output, int numEdges, int min, int max) {
        // formatting for Jack, replaced at the end
        output.add("Placeholder");

        // red edges
        List<Edge> red;
        red = generateNonIntersectingEdges(numEdges, min, max);
        for (Edge e : red) {
            output.add(e + "");
        }
        // blue edges
        List<Edge> blue;
        blue = generateNonIntersectingEdges(numEdges, min, max);
        for (Edge e : blue) {
            output.add(e + "");
        }

        // calculate number of line intersections
        int intersectionCount = 0;
        for (Edge b : blue) {
            for (Edge r : red) {
                if (Predicate.edgeIntersect(r, b)) {
                    intersectionCount++;
                }
            }
        }
        for (Edge e : blue) {
            output.add(e + "");
        }

        // formatting for Jack
        output.set(0, red.size() + " " + blue.size() + " " + intersectionCount + "");
    }

    private static List<Edge> generateNonIntersectingEdges(int numEdges, int min, int max) {
        // Random points
        Random AynRand = new Random();
        List<Edge> edges = new ArrayList<Edge>(numEdges / 2);
        int[] collisions = new int[numEdges / 2];
        for (int i = 0; i < numEdges / 2; i++) {
            Point p1 = new Point(AynRand.nextInt(max - min) + min, AynRand.nextInt(max - min) + min);
            Point p2 = new Point(AynRand.nextInt(max - min) + min, AynRand.nextInt(max - min) + min);
            Edge candidate = new Edge(p1, p2);
            boolean doesNotIntersect = true;
            {
                int j = 0;
                for (Edge edge : edges) {
                    if (Predicate.edgeIntersect(candidate, edge)) {
                        collisions[j]++;
                        doesNotIntersect = false;
                        break;
                    }
                    j++;
                }
            }
            if (doesNotIntersect) {
                edges.add(candidate);
            } else {
                // try again;
                i--;
            }
            boolean didRemove = false;
            for (int j = 0; j < collisions.length; j++) {
                if (collisions[j] > COLLISION_LIMIT) {
                    edges.remove(j);
                    didRemove = true;
                }
            }
            if (didRemove) {
                // reset collision counter
                for (int j = 0; j < collisions.length; j++) {
                    collisions[j] = 0;
                }
            }
            System.out.print("Done: " + i * 200.0 / numEdges + "%\r");
        }
        return edges;
    }
}
