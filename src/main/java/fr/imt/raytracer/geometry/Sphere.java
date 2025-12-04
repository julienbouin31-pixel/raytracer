package fr.imt.raytracer.geometry;

import fr.imt.raytracer.raytracer.Intersection;
import fr.imt.raytracer.raytracer.Ray;

import java.util.Optional;

public class Sphere extends Shape {

    private Point center;
    private double radius;

    public Sphere(Point center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    public Point getCenter() { return center; }
    public double getRadius() { return radius; }

    @Override
    public String toString() {
        return "Sphere{center=" + center + ", radius=" + radius +
                ", diffuse=" + diffuse + ", specular=" + specular + "}";
    }

    @Override
    public AbstractVec3 getNormal(Point point) {
        return point.sub(center).normalize();
    }

    @Override
    public Optional<Intersection> intersect(Ray ray) {

        Point o = ray.getOrigin();
        Vector d = ray.getDirection();

        Vector oc = o.sub(center); // o - c

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
