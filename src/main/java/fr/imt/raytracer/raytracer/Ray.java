package fr.imt.raytracer.raytracer;

import fr.imt.raytracer.geometry.Point;
import fr.imt.raytracer.geometry.Shape;
import fr.imt.raytracer.geometry.Vector;

/**
 * Représente un rayon dans la scène, défini par une origine (Point) et une direction (Vector).
 */
public class Ray {

    /** * Le point de départ du rayon .
     */
    private Point origin;

    /** * La direction dans laquelle le rayon se propage.
     */
    private Vector direction;

    /** * Référence à une forme que le rayon doit ignorer lors de l'intersection.
     * Utile pour éviter l'auto-intersection lorsque l'on lance des rayons depuis la surface d'un objet.
     */
    private Shape ignoreShape;

    /**
     * Récupère la forme que le rayon doit ignorer.
     * @return La forme à ignorer, ou {@code null} si aucune.
     */
    public Shape getIgnoreShape() {
        return ignoreShape;
    }

    /**
     * Définit la forme à ignorer pour ce rayon.
     * @param s La forme à ignorer lors des tests d'intersection.
     */
    public void setIgnoreShape(Shape s) {
        this.ignoreShape = s;
    }

    /**
     * Construit un rayon avec une origine et une direction spécifiées.
     * @param origin Le point de départ du rayon.
     * @param direction La direction de propagation du rayon.
     */
    public Ray(Point origin, Vector direction) {
        this.origin = origin;
        this.direction = direction;
    }

    /**
     * Récupère le vecteur de direction du rayon.
     * @return Le vecteur direction.
     */
    public Vector getDirection() {
        return direction;
    }

    /**
     * Définit le vecteur de direction du rayon.
     * @param direction Le nouveau vecteur direction.
     */
    public void setDirection(Vector direction) {
        this.direction = direction;
    }

    /**
     * Récupère le point d'origine du rayon.
     * @return Le point d'origine.
     */
    public Point getOrigin() {
        return origin;
    }

    /**
     * Définit le point d'origine du rayon.
     * @param origin Le nouveau point d'origine.
     */
    public void setOrigin(Point origin) {
        this.origin = origin;
    }
}