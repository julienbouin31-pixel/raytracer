package fr.imt.raytracer.geometry;

/**
 * Représente un vecteur 3D.
 */
public class Vector extends AbstractVec3 {

    /**
     * Construit un vecteur (x, y, z).
     */
    public Vector(double x, double y, double z) {
        super(x, y, z);
    }

    @Override
    protected Vector create(double x, double y, double z) {
        return new Vector(x, y, z);
    }

    /**
     * Somme vectorielle (this + v).
     */
    public Vector add(Vector v) {
        return new Vector(x + v.x, y + v.y, z + v.z);
    }

    /**
     * Soustraction vectorielle (this - v).
     */
    public Vector sub(Vector v) {
        return new Vector(x - v.x, y - v.y, z - v.z);
    }

    /**
     * Produit scalaire (Dot Product).
     */
    public double dot(Vector v) {
        return x * v.x + y * v.y + z * v.z;
    }

    /**
     * Produit vectoriel (Cross Product).
     */
    public Vector cross(Vector v) {
        return new Vector(
                y * v.z - z * v.y,
                z * v.x - x * v.z,
                x * v.y - y * v.x
        );
    }

    /**
     * Calcule la norme du vecteur.
     */
    public double length() {
        return Math.sqrt(x*x + y*y + z*z);
    }

    /**
     * Retourne le vecteur unitaire (direction pure, longueur = 1).
     * Si le vecteur est nul (longueur < EPSILON), retourne un vecteur (0,0,0).
     */
    public Vector normalize() {
        double len = length();
        if (len < EPSILON) return new Vector(0, 0, 0);
        return new Vector(x / len, y / len, z / len);
    }

    /**
     * Multiplie le vecteur par un scalaire.
     */
    @Override
    public Vector scale(double s) {
        return new Vector(x * s, y * s, z * s);
    }

    /**
     * Affiche le vecteur sous la forme "<x, y, z>".
     */
    @Override
    public String toString() {
        return "<" + x + ", " + y + ", " + z + ">";
    }
}