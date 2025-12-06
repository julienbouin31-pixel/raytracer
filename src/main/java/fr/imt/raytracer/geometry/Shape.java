package fr.imt.raytracer.geometry;

import fr.imt.raytracer.imaging.Color;
import fr.imt.raytracer.raytracer.Intersection;
import fr.imt.raytracer.raytracer.Ray;

import java.util.Optional;

/**
 * Classe de base pour toutes les formes géométriques.
 */
public abstract class Shape {

    protected Color diffuse = new Color();
    protected Color specular = new Color();

    /** Coefficient de brillance (exposant de Phong). */
    protected double shininess = 0;

    /** Définit la couleur diffuse (aspect mat de l'objet). */
    public void setDiffuse(Color diffuse) { this.diffuse = diffuse; }

    /** Définit la couleur spéculaire (reflets de lumière). */
    public void setSpecular(Color specular) { this.specular = specular; }

    public Color getDiffuse() { return diffuse; }
    public Color getSpecular() { return specular; }

    public double getShininess() {
        return shininess;
    }

    /**
     * Définit le coefficient de brillance.
     * Une valeur élevée rend les reflets plus petits et plus nets.
     */
    public void setShininess(double shininess) {
        this.shininess = shininess;
    }

    /**
     * Calcule le vecteur normal à la surface au point donné.
     * @param point Le point sur la surface.
     * @return La normale (doit être normalisée).
     */
    public abstract AbstractVec3 getNormal(Point point);

    /**
     * Calcule l'intersection entre un rayon et la forme.
     * @param ray Le rayon incident.
     * @return L'intersection la plus proche si elle existe, sinon {@code Optional.empty()}.
     */
    public abstract Optional<Intersection> intersect(Ray ray);
}