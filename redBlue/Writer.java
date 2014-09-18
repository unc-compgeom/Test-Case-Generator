package redBlue;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javafx.collections.ObservableList;

public class Writer {
	public static void write(String filename, ObservableList<String> lines)
			throws IOException {
		File outfile = new File(filename);
		FileWriter fw = new FileWriter(outfile);

		for (String line : lines) {
			fw.write(line + "\n");
		}
		fw.close();

	}
}
