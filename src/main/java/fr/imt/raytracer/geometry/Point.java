package fr.imt.raytracer.geometry;

public class Point extends AbstractVec3 {

    public Point(double x, double y, double z) {
        super(x, y, z);
    }

    @Override
    protected Point create(double x, double y, double z) {
        return new Point(x, y, z);
    }

    // Addition d'un point et d'un vecteur
    public Point add(Vector v) {
        return new Point(x + v.getX(), y + v.getY(), z + v.getZ());
    }

    // Soustraction de deux points -> vecteur
    public Vector sub(Point p) {
        return new Vector(x - p.x, y - p.y, z - p.z);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }

    @Override
    public Point scale(double s) {
        return new Point(x * s, y * s, z * s);
    }


}
