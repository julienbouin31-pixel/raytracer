package fr.imt.raytracer.raytracer;

import fr.imt.raytracer.geometry.Shape;
import fr.imt.raytracer.geometry.Vector;
import fr.imt.raytracer.imaging.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Représente la scène complète à rendre.
 * Elle contient tous les éléments (caméra, objets, lumières, paramètres de rendu)
 * et implémente la logique fondamentale du lancer de rayons (intersection, ombres, réflexions).
 */
public class Scene {

    private int width;
    private int height;
    private String output = "output.png";
    private Camera camera;
    private Color ambient = new Color(0,0,0);


    /** * Liste des sources de lumière dans la scène. */
    private List<Light> lights = new ArrayList<>();

    /** * Liste des objets géométriques dans la scène. */
    private List<Shape> shapes = new ArrayList<>();

    /** * La profondeur de récursion maximale pour les rayons secondaires (réflexion). */
    private int maxdepth = 1;


    public void setWidth(int width) { this.width = width; }
    public int getWidth() { return width; }
    public void setHeight(int height) { this.height = height; }
    public int getHeight() { return height; }
    public void setOutput(String output) { this.output = output; }
    public String getOutput() { return output; }
    public void setCamera(Camera camera) { this.camera = camera; }
    public Camera getCamera() { return camera; }
    public void setAmbient(Color ambient) { this.ambient = ambient; }
    public Color getAmbient() { return ambient; }
    public List<Light> getLights() { return lights; }
    public List<Shape> getShapes() { return shapes; }

    public int getMaxdepth() { return maxdepth; }
    public void setMaxdepth(int maxdepth) { this.maxdepth = maxdepth; }


    /**
     * Recherche l'intersection la plus proche entre le rayon donné et l'une des formes de la scène.
     *
     * @param ray Le rayon à tester.
     * @return L'objet {@link Intersection} le plus proche (avec {@code t > 1e-6}), ou {@code null} si aucune intersection valide n'est trouvée.
     */
    public Intersection findNearestIntersection(Ray ray) {
        Intersection closest = null;
        double minT = Double.MAX_VALUE;
        for (Shape shape : shapes) {
            if (shape == ray.getIgnoreShape()) continue;

            Optional<Intersection> inter = shape.intersect(ray);
            if (inter.isPresent()) {
                double t = inter.get().getT();
                if (t > 1e-6 && t < minT) {
                    minT = t;
                    closest = inter.get();
                }
            }
        }
        return closest;
    }

    /**
     * Point d'entrée pour le calcul de la couleur d'un rayon (profondeur de 1 par défaut).
     * @param ray Le rayon à lancer.
     * @return La couleur finale calculée pour ce rayon.
     */
    public Color computeColor(Ray ray) {
        return computeColor(ray, 1);
    }

    /**
     * Calcule la couleur finale d'un rayon, incluant l'éclairage direct et les réflexions récursives.
     *
     * @param ray Le rayon pour lequel calculer la couleur.
     * @param depth La profondeur de récursion actuelle (commence à 1).
     * @return La couleur résultante.
     */
    public Color computeColor(Ray ray, int depth) {
        if (depth > maxdepth) return new Color(0, 0, 0);

        Intersection inter = findNearestIntersection(ray);
        if (inter == null) return new Color(0, 0, 0);

        Color result = new Color(ambient.r(), ambient.g(), ambient.b());

        for (Light light : lights) {
            if (isInShadow(inter, light)) continue;

            Color diffuse = (light instanceof DirectionalLight dl) ? inter.computeDiffuse(dl) : inter.computeDiffuse((PointLight) light);

            Vector viewDir = ray.getDirection().scale(-1.0).normalize();

            Color specular = inter.computeSpecular(light, viewDir);

            result = new Color(
                    result.r() + diffuse.r() + specular.r(),
                    result.g() + diffuse.g() + specular.g(),
                    result.b() + diffuse.b() + specular.b()
            );
        }

        Color objSpecular = inter.getShape().getSpecular();
        boolean isReflective = (objSpecular.r() > 0 || objSpecular.g() > 0 || objSpecular.b() > 0);

        if (isReflective) {
            Vector n = (Vector) inter.getShape().getNormal(inter.getPosition());
            Vector d = ray.getDirection().normalize();

            double dot = d.dot(n);
            Vector r = d.sub(n.scale(2 * dot)).normalize();

            var origin = inter.getPosition().add(n.scale(1e-6));
            Ray reflectedRay = new Ray(origin, r);
            reflectedRay.setIgnoreShape(inter.getShape());

            Color reflectedColor = computeColor(reflectedRay, depth + 1);

            result = new Color(
                    result.r() + objSpecular.r() * reflectedColor.r(),
                    result.g() + objSpecular.g() * reflectedColor.g(),
                    result.b() + objSpecular.b() * reflectedColor.b()
            );
        }
        return result;
    }

    /**
     * Détermine si le point d'intersection est dans l'ombre par rapport à une source de lumière donnée.
     *
     * @param inter Le point d'intersection à tester.
     * @param light La source de lumière.
     * @return {@code true} si un objet est trouvé entre le point et la lumière, {@code false} sinon.
     */
    public boolean isInShadow(Intersection inter, Light light) {
        Vector normal = (Vector) inter.getShape().getNormal(inter.getPosition());
        var origin = inter.getPosition().add(normal.scale(1e-6));

        Vector lightDir;
        double maxDist;

        if (light instanceof PointLight pl) {
            Vector L = pl.getPosition().sub(origin);
            maxDist = L.length();
            lightDir = L.normalize();
        } else {
            lightDir = ((DirectionalLight) light).getDirection().normalize();
            maxDist = Double.POSITIVE_INFINITY;
        }

        Ray shadowRay = new Ray(origin, lightDir);
        shadowRay.setIgnoreShape(inter.getShape());

        Intersection shadowHit = findNearestIntersection(shadowRay);

        if (shadowHit == null) return false;

        return shadowHit.getT() < maxDist;
    }
}