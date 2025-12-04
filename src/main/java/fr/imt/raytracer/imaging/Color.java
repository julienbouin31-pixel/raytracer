package fr.imt.raytracer.imaging;


import fr.imt.raytracer.geometry.AbstractVec3;

public class Color extends AbstractVec3 {

    public Color() {
        super(0, 0, 0); // noir
    }

    public Color(double r, double g, double b) {
        super(Math.max(0, r), Math.max(0, g), Math.max(0, b));
        // On garde le min 0 (pas de lumière négative), mais on enlève le min 1
    }

    @Override
    protected Color create(double x, double y, double z) {
        return new Color(x, y, z);
    }

    public double r() { return x; }
    public double g() { return y; }
    public double b() { return z; }

    public int toRGB() {
        // On limite à 1 juste avant de convertir en entier 0-255
        double rClamped = Math.min(1.0, r());
        double gClamped = Math.min(1.0, g());
        double bClamped = Math.min(1.0, b());

        int red   = (int) Math.round(rClamped * 255);
        int green = (int) Math.round(gClamped * 255);
        int blue  = (int) Math.round(bClamped * 255);

        return ((red & 0xff) << 16) | ((green & 0xff) << 8) | (blue & 0xff);
    }

    @Override
    public String toString() {
        return "Color(" + r() + ", " + g() + ", " + b() + ")";
    }

}

