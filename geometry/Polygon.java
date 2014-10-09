package geometry;

import java.util.LinkedList;

/**
 * Created by Vance Miller on 10/9/2014.
 */
public class Polygon extends LinkedList<Point> {
    @Override
    public String toString() {
        String ret = "";
        for (Point p : this) {
            ret += p.toString() + " ";
        }
        return ret;
    }
}
