package fr.imt.raytracer.raytracer;


import fr.imt.raytracer.imaging.Color;

public abstract class Light {
    protected Color color;

    public Light(Color color) {
        this.color = color;
    }

    public Color getColor() { return color; }
}
