package fr.imt.raytracer.geometry;

public abstract class AbstractVec3 {

    protected double x;
    protected double y;
    protected double z;
    protected static final double EPSILON = 1e-9;

    protected AbstractVec3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    // --- Comparaison correcte des doubles ---
    protected static boolean almostEqual(double a, double b) {
        return Math.abs(a - b) < EPSILON;
    }

    // --- Multiplication par un scalaire ---
    public AbstractVec3 scale(double s) {
        return create(x * s, y * s, z * s);
    }

    // --- Produit de Schur ---
    public AbstractVec3 schur(AbstractVec3 other) {
        return create(x * other.x, y * other.y, z * other.z);
    }


    protected abstract AbstractVec3 create(double x, double y, double z);

    public double getX() { return x; }
    public double getY() { return y; }
    public double getZ() { return z; }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AbstractVec3 v)) return false;
        return almostEqual(x, v.x) &&
                almostEqual(y, v.y) &&
                almostEqual(z, v.z);
    }
}
