package fr.imt.raytracer.raytracer;

import fr.imt.raytracer.geometry.Vector;
import fr.imt.raytracer.imaging.Color;

public class RayTracer {

    public Color getPixelColor(int i, int j, Scene scene) {

        Orthonormal ortho = new Orthonormal(scene.getCamera());
        Ray ray = computeRayForPixel(i, j, scene, ortho);

        //Intersection inter = scene.findNearestIntersection(ray);


        //if (inter != null) {
          //  return scene.getAmbient();
        //}

        return scene.computeColor(ray);
    }

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
