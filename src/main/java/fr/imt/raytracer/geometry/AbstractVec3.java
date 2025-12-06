package fr.imt.raytracer.geometry;

/**
 * Base abstraite pour représenter un vecteur 3D.
 * Gère les opérations communes et la précision flottante.
 */
public abstract class AbstractVec3 {

    protected double x;
    protected double y;
    protected double z;

    /** Tolérance pour les comparaisons de doubles ($10^{-9}$). */
    protected static final double EPSILON = 1e-9;

    /**
     * Construit un vecteur avec les coordonnées données.
     * @param x Coordonnée X
     * @param y Coordonnée Y
     * @param z Coordonnée Z
     */
    protected AbstractVec3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Vérifie si deux valeurs sont égales à {@link #EPSILON} près.
     */
    protected static boolean almostEqual(double a, double b) {
        return Math.abs(a - b) < EPSILON;
    }

    /**
     * Multiplie chaque composante par un scalaire.
     * @param s Le facteur d'échelle.
     * @return Une nouvelle instance mise à l'échelle.
     */
    public AbstractVec3 scale(double s) {
        return create(x * s, y * s, z * s);
    }

    /**
     * Effectue le produit de Schur (multiplication composante par composante).
     * @param other Le vecteur avec lequel multiplier.
     * @return Une nouvelle instance résultant du produit.
     */
    public AbstractVec3 schur(AbstractVec3 other) {
        return create(x * other.x, y * other.y, z * other.z);
    }

    /**
     * Factory method pour créer une instance concrète du sous-type.
     */
    protected abstract AbstractVec3 create(double x, double y, double z);

    public double getX() { return x; }
    public double getY() { return y; }
    public double getZ() { return z; }

    /**
     * Compare l'égalité de deux vecteurs avec une tolérance {@link #EPSILON}.
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AbstractVec3 v)) return false;
        return almostEqual(x, v.x) &&
                almostEqual(y, v.y) &&
                almostEqual(z, v.z);
    }
}