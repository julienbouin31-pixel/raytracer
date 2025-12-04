package fr.imt.raytracer.raytracer;

import fr.imt.raytracer.geometry.Point;
import fr.imt.raytracer.imaging.Color;

public class PointLight extends Light {

    private Point position;

    public PointLight(Point position, Color color) {
        super(color);
        this.position = position;
    }

    public Point getPosition() { return position; }

    @Override
    public String toString() {
        return "PointLight{position=" + position + ", color=" + color + "}";
    }

}
