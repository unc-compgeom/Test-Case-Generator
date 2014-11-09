package redBluePolygon2;

import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
