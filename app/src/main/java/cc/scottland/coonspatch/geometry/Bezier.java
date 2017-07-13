package cc.scottland.coonspatch.geometry;

/**
 * Created by scottdonaldson on 7/13/17.
 */

public class Bezier {

    private Point p0;
    private Point p1;
    private Point p2;
    private Point p3;

    public Bezier(Point p0, Point p1, Point p2, Point p3) {
        setP0(p0);
        setP1(p1);
        setP2(p2);
        setP3(p3);
    }

    public Point getP0() {
        return p0;
    }

    public void setP0(Point p0) {
        this.p0 = p0;
    }

    public Point getP1() {
        return p1;
    }

    public void setP1(Point p1) {
        this.p1 = p1;
    }

    public Point getP2() {
        return p2;
    }

    public void setP2(Point p2) {
        this.p2 = p2;
    }

    public Point getP3() {
        return p3;
    }

    public void setP3(Point p3) {
        this.p3 = p3;
    }

    public Point evaluate(float t) {
        if (t < 0 || t > 1) throw new Error("Curve parameter out of bounds: " + t);

        float it = 1 - t;

        Point pt = new Point();

        pt = pt.add(p0.multiply(it * it * it))
                .add(p1.multiply(3 * it * it * t))
                .add(p2.multiply(3 * it * t * t))
                .add(p3.multiply(t * t * t));

        return pt;
    }

    public Point derivate(float t) {
        if (t < 0 || t > 1) throw new Error("Curve parameter out of bounds: " + t);

        float it = 1 - t;

        Point pt = new Point();

        pt = pt.add(p1.subtract(p0).multiply(3 * it * it))
                .add(p2.subtract(p1).multiply(6 * it * t))
                .add(p3.subtract(p2).multiply(3 * t * t));

        return pt;
    }
}
