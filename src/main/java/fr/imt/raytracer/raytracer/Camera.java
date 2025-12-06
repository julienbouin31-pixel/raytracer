package fr.imt.raytracer.raytracer;


import fr.imt.raytracer.geometry.Point;
import fr.imt.raytracer.geometry.Vector;

/**
 * Définit le point de vue de l'observateur (œil) dans la scène.
 */
public class Camera {

    private Point lookFrom;
    private Point lookAt;
    private Vector up;
    private double fov;

    /**
     * Construit une caméra.
     *
     * @param lookFrom Position de la caméra (l'œil).
     * @param lookAt Point visé (centre de l'écran virtuel).
     * @param up Vecteur "haut" (indique l'orientation verticale, souvent <0,1,0>).
     * @param fov Champ de vision vertical (Field Of View) en degrés.
     */
    public Camera(Point lookFrom, Point lookAt, Vector up, double fov) {

        this.lookFrom = lookFrom;
        this.lookAt = lookAt;
        this.up = up;
        this.fov = fov;
    }

    /** Retourne la position de l'œil. */
    public Point getLookFrom() { return lookFrom; }

    /** Retourne le point regardé. */
    public Point getLookAt() { return lookAt; }

    /** Retourne le vecteur d'orientation verticale. */
    public Vector getUp() { return up; }

    /** Retourne le champ de vision vertical en degrés. */
    public double getFov() { return fov; }

    @Override
    public String toString() {
        return "Camera[from=" + lookFrom + ", at=" + lookAt + ", up=" + up + ", fov=" + fov + "]";
    }

}