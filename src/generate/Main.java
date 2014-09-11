package generate;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
		int numPoints;
		if (args.length < 2) {
			numPoints = 10;
		} else {
			numPoints = Integer.parseInt(args[1]);
		}
		int min;
		if (args.length < 3) {
			min = Integer.MIN_VALUE;
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
		// generate points
		Random ayn = new Random();
		for (int i = 0; i < numPoints; i++) {
			int x = ayn.nextInt(max - min) + min;
			int y = ayn.nextInt(max - min) + min;
			try {
				w.write(x + " " + y + "\n");
			} catch (IOException e) {
				e.printStackTrace();
				try {
					w.close();
				} catch (IOException e1) {
					e1.printStackTrace();
					return;
				}
				return;
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
