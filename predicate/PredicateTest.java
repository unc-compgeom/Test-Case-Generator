//package predicate;
//
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertTrue;
//import geometry.Point;
//
//import org.junit.Test;
//
//public class PredicateTest {
//
//	@Test
//	public void testEdgeIntersectEndpoint1() {
//		Point r0 = new Point(0, 0);
//		Point r1 = new Point(0, 1);
//		Point b0 = new Point(0, 0);
//		Point b1 = new Point(1, 0);
//		assertFalse(Predicate.edgeIntersect(r0, r1, b0, b1));
//	}
//
//	@Test
//	public void testEdgeIntersectEndpoint2() {
//		Point r1 = new Point(0, 0);
//		Point r0 = new Point(0, 1);
//		Point b1 = new Point(0, 0);
//		Point b0 = new Point(1, 0);
//		assertFalse(Predicate.edgeIntersect(r0, r1, b0, b1));
//	}
//
//	@Test
//	public void testEdgeIntersectEndpoint3() {
//		Point r0 = new Point(0, 1);
//		Point r1 = new Point(0, 0);
//		Point b0 = new Point(0, 0);
//		Point b1 = new Point(1, 0);
//		assertFalse(Predicate.edgeIntersect(r0, r1, b0, b1));
//	}
//
//	@Test
//	public void testEdgePoint() {
//		Point r1 = new Point(0, 0);
//		Point r0 = new Point(0, 2);
//		Point b1 = new Point(0, 1);
//		Point b0 = new Point(0, 1);
//		assertFalse(Predicate.edgeIntersect(r0, r1, b0, b1));
//	}
//
//	@Test
//	public void testEdgeIntersectDiag1() {
//		Point r1 = new Point(0, 0);
//		Point r0 = new Point(1, 1);
//		Point b1 = new Point(1, 0);
//		Point b0 = new Point(0, 1);
//		assertTrue(Predicate.edgeIntersect(r0, r1, b0, b1));
//	}
//
//	@Test
//	public void testEdgeIntersectDiag2() {
//		Point r0 = new Point(0, 0);
//		Point r1 = new Point(1, 1);
//		Point b0 = new Point(1, 0);
//		Point b1 = new Point(0, 1);
//		assertTrue(Predicate.edgeIntersect(r0, r1, b0, b1));
//	}
//
//	@Test
//	public void testEdgeParallel1() {
//		Point r0 = new Point(0, 0);
//		Point r1 = new Point(0, 1);
//		Point b0 = new Point(0, 2);
//		Point b1 = new Point(0, 3);
//		assertFalse(Predicate.edgeIntersect(r0, r1, b0, b1));
//	}
//
//	@Test
//	public void testEdgeParallel2() {
//		Point r0 = new Point(0, 1);
//		Point r1 = new Point(0, 0);
//		Point b0 = new Point(0, 3);
//		Point b1 = new Point(0, 2);
//		assertFalse(Predicate.edgeIntersect(r0, r1, b0, b1));
//	}
//
//	@Test
//	public void testEdgeParallel3() {
//		Point r0 = new Point(0, 0);
//		Point r1 = new Point(0, 2);
//		Point b0 = new Point(0, 1);
//		Point b1 = new Point(0, 2);
//		assertTrue(Predicate.edgeIntersect(r0, r1, b0, b1));
//	}
//
//	@Test
//	public void testEdgeParallel4() {
//		Point r0 = new Point(0, 0);
//		Point r1 = new Point(0, 2);
//		Point b0 = new Point(0, 1);
//		Point b1 = new Point(0, 3);
//		assertTrue(Predicate.edgeIntersect(r0, r1, b0, b1));
//	}
//
//	@Test
//	public void testPointComparisonEqual() {
//		Point r0 = new Point(0, 0);
//		Point r1 = new Point(0, 0);
//		assertTrue(Point.comparator().compare(r0, r1) == 0);
//	}
//
//	@Test
//	public void testOnEdge1() {
//		Point p0 = new Point(0, 0);
//		Point p1 = new Point(0, 1);
//		Point p2 = new Point(0, 2);
//		assertFalse(Predicate.onEdge(p2, p0, p1));
//	}
//
//	@Test
//	public void testOnEdge2() {
//		Point p0 = new Point(0, 0);
//		Point p1 = new Point(0, 1);
//		Point p2 = new Point(0, 2);
//		assertFalse(Predicate.onEdge(p0, p1, p2));
//	}
//
//	@Test
//	public void testOnEdge3() {
//		Point p0 = new Point(0, 0);
//		Point p1 = new Point(0, 1);
//		Point p2 = new Point(0, 2);
//		assertFalse(Predicate.onEdge(p2, p1, p0));
//	}
//
//	@Test
//	public void testOnEdge4() {
//		Point p0 = new Point(0, 0);
//		Point p1 = new Point(0, 1);
//		Point p2 = new Point(0, 2);
//		assertTrue(Predicate.onEdge(p1, p0, p2));
//	}
//
//	@Test
//	public void testOnEdge5() {
//		Point p0 = new Point(0, 0);
//		Point p1 = new Point(0, 1);
//		Point p2 = new Point(0, 2);
//		assertTrue(Predicate.onEdge(p1, p2, p0));
//	}
//
//	@Test
//	public void testSameEdge1() {
//		Point r0 = new Point(0, 0);
//		Point r1 = new Point(0, 2);
//		Point b0 = new Point(0, 0);
//		Point b1 = new Point(0, 2);
//		assertFalse(Predicate.edgeIntersect(r0, r1, b0, b1));
//	}
//
//	@Test
//	public void testSameEdge2() {
//		Point r0 = new Point(0, 7);
//		Point r1 = new Point(0, 37);
//		Point b0 = new Point(0, 37);
//		Point b1 = new Point(0, 7);
//		assertFalse(Predicate.edgeIntersect(r0, r1, b0, b1));
//	}
//
// }
