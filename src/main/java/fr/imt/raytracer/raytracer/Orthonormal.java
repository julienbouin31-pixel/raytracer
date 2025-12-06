package fr.imt.raytracer.raytracer;

import fr.imt.raytracer.geometry.Vector;

/**
 * Définit le repère local (la base orthonormée) de la caméra.
 */
public class Orthonormal {

    /** Vecteur "Droite" (Right) de la caméra. */
    private final Vector u;

    /** Vecteur "Haut" (Up) recalculé pour être parfaitement orthogonal. */
    private final Vector v;

    /** Vecteur "Arrière" (Back), opposé à la direction du regard. */
    private final Vector w;

    /**
     * Construit la base orthonormée à partir de la configuration de la caméra.
     * @param camera La caméra source.
     */
    public Orthonormal(Camera camera) {
        this.w = camera.getLookFrom().sub(camera.getLookAt()).normalize();
        this.u = camera.getUp().cross(w).normalize();
        this.v = w.cross(u).normalize();
    }

    /** Retourne le vecteur U (axe X local, vers la droite). */
    public Vector getU() {
        return u;
    }

    /** Retourne le vecteur V (axe Y local, vers le haut). */
    public Vector getV() {
        return v;
    }

    /** Retourne le vecteur W (axe Z local, vers l'arrière). */
    public Vector getW() {
        return w;
    }

    @Override
    public String toString() {
        return "Orthonormal{u=" + u + ", v=" + v + ", w=" + w + '}';
    }
}