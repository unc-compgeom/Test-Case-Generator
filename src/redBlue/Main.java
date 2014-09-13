package redBlue;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Main {
	// args should be fileName, numPoints, min, max
	public static void main(String[] args) {
		// handle args
		File outfile;
		if (args.length < 1) {
			outfile = new File("./points.txt");
		} else {
			outfile = new File(args[0]);
		}
		int numEdges;
		if (args.length < 2) {
			numEdges = 10;
		} else {
			numEdges = Integer.parseInt(args[1]);
		}
		int min;
		if (args.length < 3) {
			min = 0;
		} else {
			min = Integer.parseInt(args[2]);
		}
		int max;
		if (args.length < 4) {
			max = Integer.MAX_VALUE;
		} else {
			max = Integer.parseInt(args[3]);
		}
		// create file
		FileWriter w;
		try {
			w = new FileWriter(outfile);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
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
				try {
					w.write(candidate + "\n");
				} catch (IOException e) {
					e.printStackTrace();
					try {
						w.close();
						return;
					} catch (IOException e1) {
						e1.printStackTrace();
						System.exit(-1);
					}
				}
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
				try {
					w.write(candidate + "\n");
				} catch (IOException e) {
					e.printStackTrace();
					try {
						w.close();
						return;
					} catch (IOException e1) {
						e1.printStackTrace();
						System.exit(-1);
					}
				}
			} else {
				// try again;
				i--;
			}

		}

		try {
			w.close();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}
}
