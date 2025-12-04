package fr.imt.raytracer.geometry;

import fr.imt.raytracer.raytracer.Intersection;
import fr.imt.raytracer.raytracer.Ray;

import java.util.Optional;

public class Plane extends Shape {

    private final Point point;
    private final Vector normal;
    // Epsilon pour la détection de parallèle au plan
    private static final double EPSILON = 1e-9;

    public Plane(Point point, Vector normal) {
        this.point = point;
        // La normale doit être normalisée [cite: 626]
        this.normal = normal.normalize();
    }

    @Override
    public AbstractVec3 getNormal(Point p) {
        // La normale d'un plan est constante en tout point
        return normal;
    }

    @Override
    public Optional<Intersection> intersect(Ray ray) {
        Point o = ray.getOrigin();
        Vector d = ray.getDirection();

        // Dénominateur : d . n
        double denominator = d.dot(normal);

        // 1. Vérifie si le rayon est parallèle au plan (d . n ≈ 0) [cite: 2260]
        if (Math.abs(denominator) < EPSILON) {
            return Optional.empty();
        }

        // 2. Calcul du numérateur : (q - o) . n
        // q est 'point' (un point du plan), o est l'origine du rayon
        Vector qMinusO = point.sub(o);
        double numerator = qMinusO.dot(normal);

        // 3. Calcul de t (distance)
        double t = numerator / denominator;

        // 4. Vérifie si l'intersection est devant la caméra (t > 0)
        // On utilise EPSILON pour éviter d'éventuelles auto-intersections
        if (t > EPSILON) {
            Point position = o.add(d.scale(t));
            return Optional.of(new Intersection(t, position, this));
        }

        return Optional.empty();
    }
}