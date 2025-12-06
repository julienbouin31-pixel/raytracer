package fr.imt.raytracer.geometry;

import fr.imt.raytracer.raytracer.Intersection;
import fr.imt.raytracer.raytracer.Ray;

import java.util.Optional;

/**
 * Représente un triangle défini par 3 sommets (a, b, c).
 */
public class Triangle extends Shape {

    private final Point a, b, c;
    private final Vector normal;

    /** Tolérance pour les calculs flottants (déterminant proche de 0). */
    private static final double EPSILON = 1e-9;

    /**
     * Construit un triangle et pré-calcule sa normale via le produit vectoriel.
     */
    public Triangle(Point a, Point b, Point c) {
        this.a = a;
        this.b = b;
        this.c = c;
        Vector edge1 = b.sub(a);
        Vector edge2 = c.sub(a);
        this.normal = edge1.cross(edge2).normalize();
    }

    /**
     * Retourne la normale du triangle.
     * Celle-ci est constante sur toute la surface.
     */
    @Override
    public AbstractVec3 getNormal(Point point) {
        return normal;
    }

    /**
     * Calcule l'intersection Triangle-Sphère
     * @param ray Le rayon incident.
     * @return L'intersection si le rayon traverse le triangle, {@code Optional.empty()} sinon.
     */
    @Override
    public Optional<Intersection> intersect(Ray ray) {
        Point o = ray.getOrigin(); // LookFrom
        Vector d = ray.getDirection();

        Vector e1 = b.sub(a); // (b - a)
        Vector e2 = c.sub(a); // (c - a)

        Vector p = d.cross(e2);

        double det = e1.dot(p);

        if (Math.abs(det) < EPSILON) {
            return Optional.empty();
        }

        double invDet = 1.0 / det;

        Vector t = o.sub(a);

        double beta = t.dot(p) * invDet;

        if (beta < 0.0 || beta > 1.0) {
            return Optional.empty();
        }

        Vector q = t.cross(e1);

        double gamma = d.dot(q) * invDet;

        if (gamma < 0.0 || (beta + gamma) > 1.0) {
            return Optional.empty();
        }

        double distanceT = e2.dot(q) * invDet;

        if (distanceT > EPSILON) {
            Point position = o.add(d.scale(distanceT));
            return Optional.of(new Intersection(distanceT, position, this));
        }

        return Optional.empty();
    }
}