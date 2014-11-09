package random;

import javafx.collections.ObservableList;

import java.util.Random;

public class Generate {
    // args should be fileName, numPoints, min, max
    public static void random(ObservableList<String> output, int numPoints,
                              int min, int max) {

        // random points
        Random ayn = new Random();
        for (int i = 0; i < numPoints; i++) {
            int x = ayn.nextInt(max - min) + min;
            int y = ayn.nextInt(max - min) + min;
            output.add(x + " " + y);
        }
    }
}
