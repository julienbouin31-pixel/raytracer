package fr.imt.raytracer.raytracer;


import fr.imt.raytracer.geometry.Vector;
import fr.imt.raytracer.imaging.Color;

public class DirectionalLight extends Light {

    private Vector direction;

    public DirectionalLight(Vector direction, Color color) {
        super(color);
        this.direction = direction;
    }

    public Vector getDirection() { return direction; }

    @Override
    public String toString() {
        return "DirectionalLight{direction=" + direction + ", color=" + color + "}";
    }

}

