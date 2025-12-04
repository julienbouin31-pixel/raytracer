package fr.imt.raytracer.geometry;


import fr.imt.raytracer.imaging.Color;
import fr.imt.raytracer.raytracer.Intersection;
import fr.imt.raytracer.raytracer.Ray;

import java.util.Optional;

public abstract class Shape {

    protected Color diffuse = new Color();
    protected Color specular = new Color();

    public void setDiffuse(Color diffuse) { this.diffuse = diffuse; }
    public void setSpecular(Color specular) { this.specular = specular; }

    public Color getDiffuse() { return diffuse; }
    public Color getSpecular() { return specular; }

    protected double shininess = 0;

    public double getShininess() {
        return shininess;
    }

    public void setShininess(double shininess) {
        this.shininess = shininess;
    }

    public abstract AbstractVec3 getNormal(Point point);



    public abstract Optional<Intersection> intersect(Ray ray);
}
