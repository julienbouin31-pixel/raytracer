package fr.imt.raytracer.geometry;

/**
 * Représente une position précise dans l'espace 3D.
 */
public class Point extends AbstractVec3 {

    /**
     * Construit un point à partir de ses coordonnées.
     */
    public Point(double x, double y, double z) {
        super(x, y, z);
    }

    @Override
    protected Point create(double x, double y, double z) {
        return new Point(x, y, z);
    }

    /**
     * Translate le point par un vecteur.
     * @param v Le vecteur de déplacement.
     * @return Le nouveau point translaté.
     */
    public Point add(Vector v) {
        return new Point(x + v.getX(), y + v.getY(), z + v.getZ());
    }

    /**
     * Calcule le vecteur reliant le point p à ce point (this - p).
     * @param p Le point de départ.
     * @return Le vecteur allant de p vers ce point.
     */
    public Vector sub(Point p) {
        return new Vector(x - p.x, y - p.y, z - p.z);
    }

    /**
     * Retourne une représentation textuelle du point "(x, y, z)".
     */
    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }


    @Override
    public Point scale(double s) {
        return new Point(x * s, y * s, z * s);
    }
}