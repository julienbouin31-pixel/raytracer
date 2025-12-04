package fr.imt.raytracer.imaging;


import fr.imt.raytracer.geometry.AbstractVec3;

public class Color extends AbstractVec3 {

    public Color() {
        super(0, 0, 0); // noir
    }

    public Color(double r, double g, double b) {
        super(
                Math.min(1, Math.max(0, r)),
                Math.min(1, Math.max(0, g)),
                Math.min(1, Math.max(0, b))
        );
    }

    @Override
    protected Color create(double x, double y, double z) {
        return new Color(x, y, z);
    }

    public double r() { return x; }
    public double g() { return y; }
    public double b() { return z; }

    public int toRGB() {
        int red   = (int) Math.round(x * 255);
        int green = (int) Math.round(y * 255);
        int blue  = (int) Math.round(z * 255);

        return ((red & 0xff) << 16)
                | ((green & 0xff) << 8)
                | (blue & 0xff);
    }

    @Override
    public String toString() {
        return "Color(" + r() + ", " + g() + ", " + b() + ")";
    }

}

