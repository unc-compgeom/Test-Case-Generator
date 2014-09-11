package redBlue;

public class Point {
	public int x, y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Subtracts another point's coordinates from this point's coordinates.
	 * Returns a new point with location (this.x - p.x, this.y - p.y).
	 *
	 * @param p
	 *            the point to subtract
	 * @return a new point at location (this.x - p.x, this.y - p.y).
	 */
	public Point sub(final Point p1) {
		return new Point(x - p1.x, y - p1.y);
	}

	@Override
	public String toString() {
		return x + " " + y;
	}

}
