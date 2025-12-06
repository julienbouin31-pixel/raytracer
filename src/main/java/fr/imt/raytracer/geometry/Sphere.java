package fr.imt.raytracer.geometry;

import fr.imt.raytracer.raytracer.Intersection;
import fr.imt.raytracer.raytracer.Ray;

import java.util.Optional;

/**
 * Représente une sphère définie par son centre et son rayon.
 */
public class Sphere extends Shape {

    private Point center;
    private double radius;

    /**
     * Construit une sphère.
     * @param center Le centre de la sphère.
     * @param radius Le rayon de la sphère.
     */
    public Sphere(Point center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    public Point getCenter() { return center; }
    public double getRadius() { return radius; }

    /**
     * Affiche les détails de la sphère et ses propriétés matérielles.
     */
    @Override
    public String toString() {
        return "Sphere{center=" + center + ", radius=" + radius +
                ", diffuse=" + diffuse + ", specular=" + specular + "}";
    }

    /**
     * Calcule la normale au point donné.
     */
    @Override
    public AbstractVec3 getNormal(Point point) {
        return point.sub(center).normalize();
    }

    /**
     * Calcule l'intersection Rayon-Sphère en résolvant une équation quadratique.
     *
     * @param ray Le rayon incident.
     * @return L'intersection la plus proche située devant l'origine du rayon, ou {@code Optional.empty()}.
     */
    @Override
    public Optional<Intersection> intersect(Ray ray) {

        Point o = ray.getOrigin();
        Vector d = ray.getDirection();

        Vector oc = o.sub(center);

        double a = d.dot(d);
        double b = 2 * oc.dot(d);
        double c = oc.dot(oc) - radius * radius;

        double delta = b * b - 4 * a * c;

        if (delta < 0) {
            return Optional.empty();
        }

        double sqrtD = Math.sqrt(delta);
        double t1 = (-b - sqrtD) / (2 * a);
        double t2 = (-b + sqrtD) / (2 * a);

        double t = Double.MAX_VALUE;

        if (t1 > 0 && t1 < t) t = t1;
        if (t2 > 0 && t2 < t) t = t2;

        if (t == Double.MAX_VALUE) {
            return Optional.empty();
        }

        Point position = o.add(d.scale(t));

        return Optional.of(new Intersection(t, position, this));
    }
}