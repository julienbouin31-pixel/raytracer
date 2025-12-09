package fr.imt.raytracer.raytracer;

import fr.imt.raytracer.geometry.Vector;
import fr.imt.raytracer.imaging.Color;

/**
 * Classe principale responsable du processus de lancer de rayons (Ray Tracing).
 * Elle gère la caméra, calcule le rayon qui doit être tiré pour chaque pixel
 * et demande à la scène de calculer la couleur de ce rayon.
 */
public class RayTracer {

    /**
     * Calcule la couleur finale d'un pixel donné dans l'image.
     * C'est la fonction d'entrée principale pour le rendu d'un pixel.
     *
     * @param i L'indice en colonne (X) du pixel (0 à largeur - 1).
     * @param j L'indice en ligne (Y) du pixel (0 à hauteur - 1).
     * @param scene La scène complète contenant les objets, la caméra et les lumières.
     * @return La couleur calculée ({@link Color}) pour ce pixel.
     */
    public Color getPixelColor(int i, int j, Scene scene) {

        Orthonormal ortho = new Orthonormal(scene.getCamera());
        Ray ray = computeRayForPixel(i, j, scene, ortho);

        return scene.computeColor(ray);
    }

    /**
     * Calcule le rayon primaire qui part de la caméra et traverse le centre du pixel (i, j)
     * sur le plan de projection.
     *
     * @param i L'indice en colonne (X) du pixel.
     * @param j L'indice en ligne (Y) du pixel.
     * @param scene La scène (nécessaire pour la largeur, la hauteur et la caméra).
     * @param ortho La base orthonormée (U, V, W) de la caméra.
     * @return Le rayon ({@link Ray}) partant de la caméra dans la direction du pixel.
     */
    private Ray computeRayForPixel(int i, int j, Scene scene, Orthonormal ortho) {

        int width = scene.getWidth();
        int height = scene.getHeight();

        double fovr = Math.toRadians(scene.getCamera().getFov());

        double halfHeight = Math.tan(fovr / 2.0);

        double aspect = (double) width / (double) height;

        double halfWidth = aspect * halfHeight;

        double ndcX = ((i + 0.5) / width) * 2.0 - 1.0;
        double ndcY = ((j + 0.5) / height) * 2.0 - 1.0;

        double a = ndcX * halfWidth;
        double b = ndcY * halfHeight;

        Vector d = ortho.getU().scale(a)
                .add(ortho.getV().scale(b))
                .sub(ortho.getW())
                .normalize();

        return new Ray(scene.getCamera().getLookFrom(), d);
    }

}