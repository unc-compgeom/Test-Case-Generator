package redBlue;

public class Predicate {

	/**
	 * Returns true iff e1 and e2 intersect
	 * 
	 * @param e1
	 *            an Edge
	 * @param e2
	 *            an Edge
	 * @return true iff e1 and e2 intersect
	 */
	public static boolean edgeIntersect(Edge e1, Edge e2) {
		boolean isP1LeftOfE2 = triArea(e1.o, e2.o, e2.sym.o) > 0;
		boolean isP2LeftOfE2 = triArea(e1.sym.o, e2.o, e2.sym.o) > 0;

		return (isP1LeftOfE2 && !isP2LeftOfE2)
				|| (!isP1LeftOfE2 && isP2LeftOfE2);
	}

	/**
	 * Calculates twice the signed area of the triangle defined by {@link Point}
	 * s a, b, and color. If a, b, and color are in counterclockwise order, the
	 * area is positive, if they are co-linear the area is zero, else the area
	 * is negative.
	 *
	 * @param a
	 *            a Point
	 * @param b
	 *            a Point
	 * @param c
	 *            a Point
	 * @return twice the signed area
	 */
	private static long triArea(final Point a, final Point b, final Point c) {
		return b.sub(a).x * c.sub(a).y - b.sub(a).y * c.sub(a).x;
	}
}
