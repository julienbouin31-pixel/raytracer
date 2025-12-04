package fr.imt.raytracer.geometry;

public class Vector extends AbstractVec3 {

    public Vector(double x, double y, double z) {
        super(x, y, z);
    }

    @Override
    protected Vector create(double x, double y, double z) {
        return new Vector(x, y, z);
    }

    // Addition de vecteurs
    public Vector add(Vector v) {
        return new Vector(x + v.x, y + v.y, z + v.z);
    }

    // Soustraction de vecteurs
    public Vector sub(Vector v) {
        return new Vector(x - v.x, y - v.y, z - v.z);
    }

    // Produit scalaire
    public double dot(Vector v) {
        return x * v.x + y * v.y + z * v.z;
    }

    // Produit vectoriel
    public Vector cross(Vector v) {
        return new Vector(
                y * v.z - z * v.y,
                z * v.x - x * v.z,
                x * v.y - y * v.x
        );
    }

    // Longueur
    public double length() {
        return Math.sqrt(x*x + y*y + z*z);
    }

    // Normalisation
    public Vector normalize() {
        double len = length();
        if (len < EPSILON) return new Vector(0, 0, 0);
        return new Vector(x / len, y / len, z / len);
    }

    @Override
    public Vector scale(double s) {
        return new Vector(x * s, y * s, z * s);
    }


    @Override
    public String toString() {
        return "<" + x + ", " + y + ", " + z + ">";
    }

}

