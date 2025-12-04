package fr.imt.raytracer.raytracer;


import fr.imt.raytracer.geometry.AbstractVec3;
import fr.imt.raytracer.geometry.Point;
import fr.imt.raytracer.geometry.Shape;
import fr.imt.raytracer.geometry.Vector;
import fr.imt.raytracer.imaging.Color;

public class Intersection {

    private final double t;
    private final Point position;
    private final Shape shape;

    public Intersection(double t, Point position, Shape shape){
        this.t = t;
        this.position = position;
        this.shape = shape;
    }

    public double getT() {
        return t;
    }

    public Point getPosition() {
        return position;
    }

    public Shape getShape() {
        return shape;
    }

    public Color computeDiffuse(DirectionalLight light){
        Vector normal = (Vector) shape.getNormal(position);
        Vector lightDir = light.getDirection().normalize().scale(-1.0);
        double dot = normal.dot(lightDir);
        double lambert = Math.max(0, dot);
        Color lightColor = light.getColor();
        Color diffuse = shape.getDiffuse();

        double r = lightColor.r() * lambert * diffuse.r();
        double g = lightColor.g() * lambert * diffuse.g();
        double b = lightColor.b() * lambert * diffuse.b();

        return new Color(r, g, b);

    }

    public Color computeDiffuse(PointLight light) {

        Vector normal = (Vector) shape.getNormal(position);

        // direction depuis le point d'intersection vers la lumière
        Vector lightDir = light.getPosition()
                .sub(position)
                .normalize();


        double dot = normal.dot(lightDir);
        double lambert = Math.max(0, dot);

        Color lightColor = light.getColor();
        Color diffuse = shape.getDiffuse();

        double r = lightColor.r() * lambert * diffuse.r();
        double g = lightColor.g() * lambert * diffuse.g();
        double b = lightColor.b() * lambert * diffuse.b();

        return new Color(r, g, b);
    }

    public Color computeSpecular(Light light, Camera camera) {
        // 1. Récupération de la normale
        Vector normal = ((Vector) shape.getNormal(position)).normalize();

        Vector L;
        if (light instanceof PointLight pl) {
            // Vecteur vers la lumière (Point)
            L = pl.getPosition().sub(position).normalize();
        } else {
            // Vecteur vers la lumière (Directionnelle)
            // On inverse la direction des rayons pour pointer VERS la source
            L = ((DirectionalLight) light).getDirection().normalize().scale(-1.0);
        }

        // --- CORRECTION "LUMIÈRE FANTÔME" ---
        // Si la surface tourne le dos à la lumière, il ne peut pas y avoir de reflet spéculaire.
        // Cela supprime la tache lumineuse bizarre au centre des objets.
        if (normal.dot(L) <= 0) {
            return new Color(0, 0, 0);
        }
        // ------------------------------------

        // 2. Vecteur Vue (V) : De l'intersection VERS la caméra
        Vector V = camera.getLookFrom().sub(position).normalize();

        // 3. Vecteur Halfway (H) pour le modèle Blinn-Phong
        Vector H = L.add(V).normalize();

        // 4. Calcul du facteur spéculaire
        double dotNH = Math.max(0, normal.dot(H));
        double specFactor = Math.pow(dotNH, shape.getShininess());

        Color lightColor = light.getColor();
        Color specColor  = shape.getSpecular();

        // 5. Calcul final de la couleur
        double r = specFactor * lightColor.r() * specColor.r();
        double g = specFactor * lightColor.g() * specColor.g();
        double b = specFactor * lightColor.b() * specColor.b();

        return new Color(r, g, b);
    }







}
