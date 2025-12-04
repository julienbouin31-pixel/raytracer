package fr.imt.raytracer.geometry;

import fr.imt.raytracer.raytracer.Intersection;
import fr.imt.raytracer.raytracer.Ray;

import java.util.Optional;

public class Triangle extends Shape {

    private final Point a, b, c;
    private final Vector normal;
    private static final double EPSILON = 1e-9;

    public Triangle(Point a, Point b, Point c) {
        this.a = a;
        this.b = b;
        this.c = c;

        // Calculer la normale du triangle à la construction : n = (b-a) x (c-a) / ||...|| [cite: 2267]
        Vector edge1 = b.sub(a);
        Vector edge2 = c.sub(a);
        // On s'assure que la normale existe (triangle non plat) et on la normalise
        this.normal = edge1.cross(edge2).normalize();
    }

    @Override
    public AbstractVec3 getNormal(Point point) {
        // La normale d'un triangle est constante en tout point
        return normal;
    }

    @Override
    public Optional<Intersection> intersect(Ray ray) {
        Point o = ray.getOrigin(); // LookFrom
        Vector d = ray.getDirection();

        // e1 et e2 sont les bords du triangle
        Vector e1 = b.sub(a); // (b - a)
        Vector e2 = c.sub(a); // (c - a)

        // 1. Calcul de P = d x e2 (P = d x (c-a)) [cite: 2286]
        Vector p = d.cross(e2);

        // 2. Calcul du déterminant : det = e1 . P (e1 . (d x e2)) [cite: 2288]
        double det = e1.dot(p);

        // 3. Vérification de parallèle au plan (det ≈ 0) [cite: 2290]
        if (Math.abs(det) < EPSILON) {
            return Optional.empty();
        }

        double invDet = 1.0 / det;

        // 4. Calcul de T = o - a (lookFrom - a) [cite: 2292]
        Vector t = o.sub(a);

        // 5. Calcul de Beta : beta = (T . P) * invDet [cite: 2292]
        double beta = t.dot(p) * invDet;

        // 6. Vérification des bornes de Beta : doit être dans le triangle [cite: 2293]
        if (beta < 0.0 || beta > 1.0) {
            return Optional.empty();
        }

        // 7. Calcul de Q = T x e1 (Q = T x (b-a)) [cite: 2294]
        Vector q = t.cross(e1);

        // 8. Calcul de Gamma : gamma = (d . Q) * invDet [cite: 2295]
        double gamma = d.dot(q) * invDet;

        // 9. Vérification des bornes de Gamma : doit être dans le triangle (gamma > 0 et beta + gamma <= 1) [cite: 2296, 2298]
        if (gamma < 0.0 || (beta + gamma) > 1.0) {
            return Optional.empty();
        }

        // 10. Calcul de t (distance le long du rayon) : t = (e2 . Q) * invDet [cite: 2297]
        double distanceT = e2.dot(q) * invDet;

        // 11. Vérification finale : t doit être positif (devant la caméra) [cite: 2299]
        if (distanceT > EPSILON) {
            Point position = o.add(d.scale(distanceT));
            return Optional.of(new Intersection(distanceT, position, this));
        }

        return Optional.empty();
    }
}