package fr.imt.raytracer.raytracer;

import fr.imt.raytracer.geometry.Point;
import fr.imt.raytracer.imaging.Color;

/**
 * Représente une source de lumière ponctuelle.
 */
public class PointLight extends Light {

    private Point position;

    /**
     * Crée une lumière ponctuelle.
     * @param position La position de la source dans l'espace 3D.
     * @param color La couleur émise.
     */
    public PointLight(Point position, Color color) {
        super(color);
        this.position = position;
    }

    /**
     * Retourne la position de la source.
     */
    public Point getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "PointLight{position=" + position + ", color=" + color + "}";
    }

}