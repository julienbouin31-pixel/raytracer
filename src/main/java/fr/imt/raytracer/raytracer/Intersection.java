package fr.imt.raytracer.raytracer;


import fr.imt.raytracer.geometry.AbstractVec3;
import fr.imt.raytracer.geometry.Point;
import fr.imt.raytracer.geometry.Shape;
import fr.imt.raytracer.geometry.Vector;
import fr.imt.raytracer.imaging.Color;

/**
 * Stocke les informations d'un impact entre un rayon et une forme.
 * <p>
 * </p>
 */
public class Intersection {

    /** Distance depuis l'origine du rayon (t). */
    private final double t;
    /** Point précis de l'impact dans l'espace 3D. */
    private final Point position;
    /** L'objet géométrique touché. */
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

    /**
     * Calcule la réflexion diffuse (Loi de Lambert) pour une lumière directionnelle.
     *
     * @param light La source de lumière (rayons parallèles).
     * @return La couleur résultante.
     */
    public Color computeDiffuse(DirectionalLight light){
        Vector normal = (Vector) shape.getNormal(position);
        Vector lightDir = light.getDirection().normalize();

        double dot = normal.dot(lightDir);
        double lambert = Math.max(0, dot);
        Color lightColor = light.getColor();
        Color diffuse = shape.getDiffuse();

        double r = lightColor.r() * lambert * diffuse.r();
        double g = lightColor.g() * lambert * diffuse.g();
        double b = lightColor.b() * lambert * diffuse.b();

        return new Color(r, g, b);

    }

    /**
     * Calcule la réflexion diffuse pour une lumière ponctuelle.
     */
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

    /**
     * Calcule la réflexion spéculaire (Modèle de Blinn-Phong).
     * Simule le reflet brillant de la source lumineuse sur la surface.
     * @param light La source lumineuse.
     * @param viewDir Le vecteur direction vers la caméra (V).
     * @return La couleur spéculaire calculée.
     */
    public Color computeSpecular(Light light, Vector viewDir) {

        Vector normal = ((Vector) shape.getNormal(position)).normalize();

        Vector L;
        if (light instanceof PointLight pl) {
            L = pl.getPosition().sub(position).normalize();
        } else {
            L = ((DirectionalLight) light).getDirection().normalize();
        }

        if (normal.dot(L) < 0) {
            return new Color(0, 0, 0);
        }

        Vector V = viewDir.normalize();

        Vector H = L.add(V).normalize();

        double dotNH = Math.max(0, normal.dot(H));
        double specFactor = Math.pow(dotNH, shape.getShininess());

        Color lightColor = light.getColor();
        Color specColor  = shape.getSpecular();

        double r = specFactor * lightColor.r() * specColor.r();
        double g = specFactor * lightColor.g() * specColor.g();
        double b = specFactor * lightColor.b() * specColor.b();

        return new Color(r, g, b);
    }
}