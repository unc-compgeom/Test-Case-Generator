package predicate;

import geometry.Edge;
import geometry.Point;
import geometry.Polygon;

import java.util.Iterator;

public class Predicate {
    public static boolean polygonIntersect(Polygon p0, Polygon p1) {
        Iterator<Point> redIt = p0.iterator();
        Point red0 = redIt.next();
        // check all edges
        while (redIt.hasNext()) {
            Point red1 = redIt.next();
            Edge redEdge = new Edge(red0, red1);
            Iterator<Point> blueIt = p0.iterator();
            Point blue0 = blueIt.next();
            // for one edge on the red polygon, check all edges on the blue
            // polygon
            if (edgeIntersectsPolygon(redEdge, p1)) {
                return true;
            }
            red0 = red1;
        }
        // check the last edge on the red polygon for all blue polygon edges
        Point red1 = p0.get(0);
        Edge redEdge = new Edge(red0, red1);
        if (edgeIntersectsPolygon(redEdge, p1)) {
            return true;
        }
        return false;
    }

    private static boolean edgeIntersectsPolygon(Edge e, Polygon p) {
        Iterator<Point> it = p.iterator();
        Point p0 = it.next();
        // for a given edge, check all edges of the polygon for intersections
        while (it.hasNext()) {
            Point p1 = it.next();
            Edge pEdge = new Edge(p0, p1);
            if (edgeIntersect(e, pEdge)) {
                return true;
            }
            p0 = p1;
        }
        // check the last edge of the polygon
        Edge pEdge = new Edge(p0, p.get(0));
        if (edgeIntersect(e, pEdge)) {
            return true;
        }
        return false;
    }

    /**
     * Returns true iff e1 and e2 intersect
     *
     * @param e1 an Edge
     * @param e2 an Edge
     * @return true iff e1 and e2 intersect
     */
    public static boolean edgeIntersect(Edge e1, Edge e2) {
        Point a = e1.o;
        Point b = e1.sym.o;
        Point c = e2.o;
        Point d = e2.sym.o;
        return edgeIntersect(a, b, c, d);
    }

    /**
     * Returns true iff the two edges intersect.
     *
     * @param a Endpoint of edge 1
     * @param b Endpoint of edge 1
     * @param c Endpoint of edge 2
     * @param d Endpoint of edge 2
     * @return true iff e1 and e2 intersect
     */
    public static boolean edgeIntersect(Point a, Point b, Point c, Point d) {
        if (a == c && b == d) {
            return false;
        }
        Orientation abc = orientation(a, b, c);
        Orientation abd = orientation(a, b, d);
        Orientation cda = orientation(c, d, a);
        Orientation cdb = orientation(c, d, b);
        if (abc == Orientation.COLINEAR && abd == Orientation.COLINEAR) {
            // parallel
            if (onEdge(c, a, b)) {
                return true;
            } else if (onEdge(d, a, b)) {
                return true;
            } else {
                return false;
            }
        } else if (abc == Orientation.COLINEAR) {
            // c is either on AB or after AB, d is not on AB
            if (onEdge(c, a, b)) {
                return true;
            } else {
                return false;
            }
        } else if (abd == Orientation.COLINEAR) {
            // d is either on AB or after AB, c is not on AB
            if (onEdge(d, a, b)) {
                return true;
            } else {
                return false;
            }
        } else {
            return (abc != abd && cda != cdb);
        }
    }

    /**
     * Returns true iff the edge from a to a.next and b to b.next intersect.
     *
     * @param a start of edge 1
     * @param b start of edge 2
     * @return true iff they intersect
     */
    public static boolean edgeIntersect(Point a, Point b) {
        return edgeIntersect(a, a.next, b, b.next);
    }

    private static boolean ccw(Point p1, Point p2, Point p3) {
        long a = p1.x;
        long b = p1.y;
        long c = p2.x;
        long d = p2.y;
        long e = p3.x;
        long f = p3.y;
        return ((c - a) * (f - b) - (d - b) * (e - a)) > 0;
    }

    public static Orientation orientation(Point p, Point q, Point r) {
        long o = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y);
        if (o < 0) {
            return Orientation.COUNTERCLOCKWISE;
        } else if (0 < o) {
            return Orientation.CLOCKWISE;
        } else {
            return Orientation.COLINEAR;
        }
    }

    public enum Orientation {
        CLOCKWISE, COUNTERCLOCKWISE, COLINEAR
    }

    ;

    /**
     * Tests if p is on ab. If p is on a or b returns false. If p is colinear
     * with a and b and between them returns true. Else returns false;
     *
     * @param p Point to test
     * @param a Endpoint 1
     * @param b Endpoint 2
     * @return true iff p is on the line ab
     */
    public static boolean onEdge(final Point p, final Point a, Point b) {
        if (orientation(a, b, p) == Orientation.COLINEAR) {
            final double dot = p.sub(a).dot(b.sub(a));
            final double distSq = distSquared(a, b);
            return 0 < dot && dot < distSq;
        } else {
            return false;
        }
    }

    public static double distSquared(Point a, Point b) {
        return Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2);
    }
}