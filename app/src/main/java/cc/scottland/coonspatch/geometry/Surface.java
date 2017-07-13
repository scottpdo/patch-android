package cc.scottland.coonspatch.geometry;

/**
 * Created by scottdonaldson on 7/13/17.
 */

public class Surface {

    private Bezier u0;
    private Bezier u1;
    private Bezier v0;
    private Bezier v1;

    public Surface() {
        setU0(new Bezier(
                new Point(0, 0, 0),
                new Point(0.333f, 0, 0),
                new Point(0.667f, 0, 0),
                new Point(1, 0, 0))
        );
        setU1(new Bezier(
                new Point(0, 1, 0),
                new Point(0.333f, 1, 0),
                new Point(0.667f, 1, 0),
                new Point(1, 1, 0))
        );
        setV0(new Bezier(
                new Point(0, 0, 0),
                new Point(0, 0.333f, 0),
                new Point(0, 0.667f, 0),
                new Point(0, 1, 0))
        );
        setV1(new Bezier(
                new Point(1, 0, 0),
                new Point(1, 0.333f, 0),
                new Point(1, 0.667f, 0),
                new Point(1, 1, 0))
        );
    }

    public Surface(Bezier u0, Bezier u1, Bezier v0, Bezier v1) {
        setU0(u0);
        setU1(u1);
        setV0(v0);
        setV1(v1);
    }

    public Bezier getU0() {
        return u0;
    }

    public void setU0(Bezier u0) {
        this.u0 = u0;
    }

    public Bezier getU1() {
        return u1;
    }

    public void setU1(Bezier u1) {
        this.u1 = u1;
    }

    public Bezier getV0() {
        return v0;
    }

    public void setV0(Bezier v0) {
        this.v0 = v0;
    }

    public Bezier getV1() {
        return v1;
    }

    public void setV1(Bezier v1) {
        this.v1 = v1;
    }

    public Point patch(float u, float v) {

        Point Lu = u0.evaluate(u).multiply(1 - v).add(u1.evaluate(u).multiply(v));
        Point Lv = v0.evaluate(v).multiply(1 - u).add(v1.evaluate(v).multiply(u));

        Point B = u0.evaluate(0).multiply((1 - u) * (1 - v))
                .add(u0.evaluate(1).multiply(u * (1 - v)))
                .add(u1.evaluate(0).multiply((1 - u) * v))
                .add(u1.evaluate(1).multiply(u * v));

        Point C = Lu.add(Lv).add(B.multiply(-1));

        return C;
    }
}
