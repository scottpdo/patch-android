package cc.scottland.coonspatch.geometry;

/**
 * Created by scottdonaldson on 7/13/17.
 */

public class Point {

    private float x;
    private float y;
    private float z;

    public Point() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public Point(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float getX() { return x; }
    public void setX(float x) { this.x = x; }
    public float getY() { return y; }
    public void setY(float y) { this.y = y; }
    public float getZ() { return z; }
    public void setZ(float z) { this.z = z; }

    public void moveX(float dx) { this.x += dx; }
    public void moveY(float dy) { this.y += dy; }
    public void moveZ(float dz) { this.z += dz; }

    /**
     * Multiply the point by a scalar value s (i.e. scale its position relative to the origin by s).
     */
    public Point multiply(float s) {
        return new Point(x * s, y * s, z * s);
    }

    public Point inv() { return multiply(-1); }

    public Point add(Point pt) {
        return new Point(x + pt.getX(), y + pt.getY(), z + pt.getZ());
    }

    public Point subtract(Point pt) {
        return add(pt.inv());
    }

    public float distance(Point pt) {
        float dx = x - pt.getX();
        float dy = y - pt.getY();
        float dz = z - pt.getZ();
        return (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    public void set(Point pt) {
        setX(pt.getX());
        setY(pt.getY());
        setZ(pt.getZ());
    }

    public Point clone() {
        return new Point(x, y, z);
    }
}
