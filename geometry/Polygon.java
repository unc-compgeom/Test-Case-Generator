package geometry;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by Vance Miller on 10/9/2014.
 */
public class Polygon extends LinkedList<Point> {
    @Override
    public String toString() {
        String ret = "";
        if (size() > 0) {
            Point p;
            Iterator<Point> it = iterator();
            p = it.next();
            while (it.hasNext()) {
                Point n = it.next();
                ret += p.toString() + " " + n.toString() + "\n";
                p = n;
            }

            return ret + p.toString() + " " + get(0).toString();
        }
        return ret;
    }
}
