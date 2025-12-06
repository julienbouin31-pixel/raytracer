package fr.imt.raytracer.geometry;

import fr.imt.raytracer.raytracer.Intersection;
import fr.imt.raytracer.raytracer.Ray;

import java.util.Optional;

/**
 * Représente un plan infini défini par un point et un vecteur normal.
 */
public class Plane extends Shape {

    private final Point point;
    private final Vector normal;

    /** Tolérance pour la détection de parallélisme et d'intersections. */
    private static final double EPSILON = 1e-9;

    /**
     * Crée un plan défini par un point et une normale.
     * @param point Un point appartenant au plan.
     * @param normal La normale du plan (sera normalisée automatiquement).
     */
    public Plane(Point point, Vector normal) {
        this.point = point;
        this.normal = normal.normalize();
    }

    /**
     * Retourne la normale au point donné.
     * Pour un plan, la normale est constante.
     */
    @Override
    public AbstractVec3 getNormal(Point p) {
        return normal;
    }

    /**
     * Calcule l'intersection entre le plan et un rayon.
     * @param ray Le rayon incident.
     * @return L'intersection si le rayon n'est pas parallèle et pointe vers le plan, sinon {@code Optional.empty()}.
     */
    @Override
    public Optional<Intersection> intersect(Ray ray) {
        Point o = ray.getOrigin();
        Vector d = ray.getDirection();

        double denominator = d.dot(normal);

        if (Math.abs(denominator) < EPSILON) {
            return Optional.empty();
        }

        Vector qMinusO = point.sub(o);
        double numerator = qMinusO.dot(normal);

        double t = numerator / denominator;

        if (t > EPSILON) {
            Point position = o.add(d.scale(t));
            return Optional.of(new Intersection(t, position, this));
        }

        return Optional.empty();
    }
}