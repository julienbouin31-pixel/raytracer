package fr.imt.raytracer.imaging;


import fr.imt.raytracer.geometry.AbstractVec3;

/**
 * Représente une couleur RVB (RGB)
 */
public class Color extends AbstractVec3 {

    /**
     * Crée une couleur noire par défaut (0, 0, 0).
     */
    public Color() {
        super(0, 0, 0); // noir
    }

    /**
     * Construit une couleur spécifique.
     * Les valeurs négatives sont ramenées à 0.
     * Les valeurs > 1.0 sont conservées (HDR) pour les calculs intermédiaires.
     */
    public Color(double r, double g, double b) {
        super(Math.max(0, r), Math.max(0, g), Math.max(0, b));
    }

    @Override
    protected Color create(double x, double y, double z) {
        return new Color(x, y, z);
    }

    public double r() { return x; }
    public double g() { return y; }
    public double b() { return z; }

    /**
     * Convertit la couleur flottante en entier 24-bits (0-255 par canal).
     * Applique un "clamping" : toute valeur > 1.0 est ramenée à 1.0 (blanc pur).
     * @return La couleur encodée (0xRRGGBB).
     */
    public int toRGB() {
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