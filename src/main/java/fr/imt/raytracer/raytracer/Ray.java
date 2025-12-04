package fr.imt.raytracer.raytracer;

import fr.imt.raytracer.geometry.Point;
import fr.imt.raytracer.geometry.Shape;
import fr.imt.raytracer.geometry.Vector;

public class Ray {
    private Point origin;
    private Vector direction;
    private Shape ignoreShape;

    public Shape getIgnoreShape() { return ignoreShape; }
    public void setIgnoreShape(Shape s) { this.ignoreShape = s; }

    public Ray(Point origin, Vector direction) {
        this.origin = origin;
        this.direction = direction;
    }

    public Vector getDirection() {
        return direction;
    }

    public void setDirection(Vector direction) {
        this.direction = direction;
    }

    public Point getOrigin() {
        return origin;
    }

    public void setOrigin(Point origin) {
        this.origin = origin;
    }
}
