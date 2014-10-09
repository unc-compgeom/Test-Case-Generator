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
        while(redIt.hasNext()) {
            Point red1 = redIt.next();
            Edge redEdge = new Edge(red0, red1);
            Iterator<Point> blueIt = p0.iterator();
            Point blue0 = blueIt.next();
            // for one edge on the red polygon, check all edges on the blue polygon
            if (edgeIntersectsPolygon(redEdge,p1)) {
                return true;
            }
            red0 = red1;
        }
        // check the last edge on the red polygon for all blue polygon edges
        Point red1 = p0.get(0);
        Edge redEdge = new Edge(red0, red1);
        if (edgeIntersectsPolygon(redEdge,p1)) {
            return true;
        }
        return false;
    }

    private static boolean edgeIntersectsPolygon(Edge e, Polygon p) {
        Iterator<Point> it = p.iterator();
        Point p0 = it.next();
        // for a given edge, check all edges of the polygon for intersections
        while(it.hasNext()) {
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

    public enum Orientation {
        CLOCKWISE,
        COUNTERCLOCKWISE,
        COLINEAR
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
        return (ccw(a, c, d) != ccw(b, c, d)) && (ccw(a, b, c) != ccw(a, b, d));
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

    public static boolean intersection(Edge e1, Edge e2) {
        Point p1 = e1.o,
                p2 = e1.sym.o,
                q1 = e2.o,
                q2 = e2.sym.o;
        Orientation o1 = orientation(p1, p2, q1),
                o2 = orientation(p1, p2, q2),
                o3 = orientation(q1, q2, p1),
                o4 = orientation(q1, q2, p2);
        if (o1 != o2 && o3 != o4) {
            return true;
        }
        return false;
    }
}