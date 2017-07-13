package cc.scottland.coonspatch;

import cc.scottland.coonspatch.geometry.Point;

/**
 * Created by scottdonaldson on 7/13/17.
 */

public class Camera {

    Point location;
    Point direction;

    public Camera(Point location, Point direction) {
        setLocation(location);
        setDirection(direction);
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public Point getDirection() {
        return direction;
    }

    public void setDirection(Point direction) {
        this.direction = direction;
    }
}
