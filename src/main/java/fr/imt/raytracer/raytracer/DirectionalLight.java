package fr.imt.raytracer.raytracer;


import fr.imt.raytracer.geometry.Vector;
import fr.imt.raytracer.imaging.Color;

/**
 * Représente une source de lumière située à l'infini (ex: le Soleil).
 */
public class DirectionalLight extends Light {

    private Vector direction;

    /**
     * Crée une lumière directionnelle.
     * @param direction Le vecteur indiquant la direction des rayons lumineux.
     * @param color La couleur de la lumière.
     */
    public DirectionalLight(Vector direction, Color color) {
        super(color);
        this.direction = direction;
    }

    /**
     * Retourne la direction de la lumière.
     */
    public Vector getDirection() {
        return direction;
    }

    @Override
    public String toString() {
        return "DirectionalLight{direction=" + direction + ", color=" + color + "}";
    }

}