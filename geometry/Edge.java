package geometry;

public class Edge {
    public Edge sym;
    public Point o;

    public Edge(Point o, Point symO) {
        this.sym = new Edge(this, symO);
        this.o = o;
    }

    private Edge(Edge sym, Point o) {
        this.sym = sym;
        this.o = o;
    }

    @Override
    public String toString() {
        return o.toString() + " " + sym.o.toString();
    }
}
