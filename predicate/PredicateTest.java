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
//}
