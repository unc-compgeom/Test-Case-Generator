package redBlue;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javafx.collections.ObservableList;

public class Generate {

	public static void generate(ObservableList<String> output, int numEdges,
			int min, int max) {

		// Random points
		Random ayn = new Random();
		// red edges
		List<Edge> red = new LinkedList<Edge>();
		// blue edges
		List<Edge> blue = new LinkedList<Edge>();
		for (int i = 0; i < numEdges / 2; i++) {
			Point p1 = new Point(ayn.nextInt(max - min) + min, ayn.nextInt(max
					- min)
					+ min);
			Point p2 = new Point(ayn.nextInt(max - min) + min, ayn.nextInt(max
					- min)
					+ min);
			Edge candidate = new Edge(p1, p2);
			boolean doesNotIntersect = true;
			for (Edge edge : red) {
				if (Predicate.edgeIntersect(candidate, edge)) {
					doesNotIntersect = false;
					break;
				}
			}
			if (doesNotIntersect) {
				red.add(candidate);
				output.add(candidate + "");
			} else {
				// try again;
				i--;
			}
		}
		for (int i = 0; i < numEdges / 2; i++) {
			Point p1 = new Point(ayn.nextInt(max - min) + min, ayn.nextInt(max
					- min)
					+ min);
			Point p2 = new Point(ayn.nextInt(max - min) + min, ayn.nextInt(max
					- min)
					+ min);
			Edge candidate = new Edge(p1, p2);
			boolean doesNotIntersect = true;
			for (Edge edge : blue) {
				if (Predicate.edgeIntersect(candidate, edge)) {
					doesNotIntersect = false;
					break;
				}
			}
			if (doesNotIntersect) {
				blue.add(candidate);
				output.add(candidate + "");
			} else {
				// try again;
				i--;
			}
		}
	}
}
