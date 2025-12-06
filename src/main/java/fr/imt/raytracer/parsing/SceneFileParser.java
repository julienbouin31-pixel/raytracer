package fr.imt.raytracer.parsing;

import fr.imt.raytracer.geometry.*;
import fr.imt.raytracer.raytracer.Camera;
import fr.imt.raytracer.imaging.Color;
import fr.imt.raytracer.raytracer.DirectionalLight;
import fr.imt.raytracer.raytracer.PointLight;
import fr.imt.raytracer.raytracer.Scene;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Parseur de fichiers de description de scène.
 * <p>
 * Lit un fichier texte ligne par ligne pour configurer la caméra, les lumières et les objets.
 * </p>
 */
public class SceneFileParser {

    private Scene scene = new Scene();

    private List<Point> vertexList = new ArrayList<>();
    private Color currentDiffuse = new Color(0,0,0);
    private Color currentSpecular = new Color(0,0,0);
    private double currentShininess = 0;

    private int maxVerts = 0;

    /**
     * Analyse le fichier donné et construit la scène correspondante.
     *
     * @param filename Chemin du fichier de scène.
     * @return L'objet {@link Scene} prêt pour le rendu.
     * @throws Exception En cas d'erreur de lecture ou de format (index hors limites, etc.).
     */
    public Scene parse(String filename) throws Exception {

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {

            String line;

            while ((line = br.readLine()) != null) {
                line = line.trim();

                if (line.isEmpty() || line.startsWith("#")) continue;

                String[] tokens = line.split("\\s+");
                String cmd = tokens[0];

                switch (cmd) {
                    case "size" -> parseSize(tokens);
                    case "output" -> parseOutput(tokens);
                    case "camera" -> parseCamera(tokens);
                    case "ambient" -> parseAmbient(tokens);
                    case "diffuse" -> parseDiffuse(tokens);
                    case "specular" -> parseSpecular(tokens);
                    case "directional" -> parseDirectional(tokens);
                    case "point" -> parsePointLight(tokens);
                    case "sphere" -> parseSphere(tokens);
                    case "maxverts" -> parseMaxVerts(tokens);
                    case "vertex" -> parseVertex(tokens);
                    case "tri" -> parseTriangle(tokens);
                    case "plane" -> parsePlane(tokens);
                    case "shininess" -> parseShininess(tokens);
                    case "maxdepth" -> parseMaxDepth(tokens); // AJOUT
                    default -> System.out.println("Invalid command: " + cmd);
                }
            }
        }

        return scene;
    }

    /** Définit la brillance (exposant Phong) pour les futurs objets. */
    private void parseShininess(String[] t) {
        currentShininess = Double.parseDouble(t[1]);
    }

    /** Configure la profondeur de récursion maximale (réflexions). */
    private void parseMaxDepth(String[] t) {
        scene.setMaxdepth(Integer.parseInt(t[1]));
    }

    /** Définit la résolution de l'image (largeur, hauteur). */
    private void parseSize(String[] t) {
        scene.setWidth(Integer.parseInt(t[1]));
        scene.setHeight(Integer.parseInt(t[2]));
    }

    /** Définit le nom du fichier de sortie. */
    private void parseOutput(String[] t) {
        scene.setOutput(t[1]);
    }

    /** Configure la caméra */
    private void parseCamera(String[] t) {
        Point lookFrom = new Point(
                Double.parseDouble(t[1]),
                Double.parseDouble(t[2]),
                Double.parseDouble(t[3])
        );

        Point lookAt = new Point(
                Double.parseDouble(t[4]),
                Double.parseDouble(t[5]),
                Double.parseDouble(t[6])
        );

        Vector up = new Vector(
                Double.parseDouble(t[7]),
                Double.parseDouble(t[8]),
                Double.parseDouble(t[9])
        );

        double fov = Double.parseDouble(t[10]);

        scene.setCamera(new Camera(lookFrom, lookAt, up, fov));
    }

    /** Définit la lumière ambiante globale de la scène. */
    private void parseAmbient(String[] t) {
        scene.setAmbient(new Color(
                Double.parseDouble(t[1]),
                Double.parseDouble(t[2]),
                Double.parseDouble(t[3])
        ));
    }

    /** Met à jour la couleur diffuse courante. */
    private void parseDiffuse(String[] t) {
        currentDiffuse = new Color(
                Double.parseDouble(t[1]),
                Double.parseDouble(t[2]),
                Double.parseDouble(t[3])
        );
    }

    /** Met à jour la couleur spéculaire courante. */
    private void parseSpecular(String[] t) {
        currentSpecular = new Color(
                Double.parseDouble(t[1]),
                Double.parseDouble(t[2]),
                Double.parseDouble(t[3])
        );
    }

    /** Ajoute une lumière directionnelle */
    private void parseDirectional(String[] t) {
        Vector dir = new Vector(
                Double.parseDouble(t[1]),
                Double.parseDouble(t[2]),
                Double.parseDouble(t[3])
        );

        Color color = new Color(
                Double.parseDouble(t[4]),
                Double.parseDouble(t[5]),
                Double.parseDouble(t[6])
        );

        scene.getLights().add(new DirectionalLight(dir, color));
    }

    /** Ajoute une lumière ponctuelle. */
    private void parsePointLight(String[] t) {
        Point pos = new Point(
                Double.parseDouble(t[1]),
                Double.parseDouble(t[2]),
                Double.parseDouble(t[3])
        );

        Color color = new Color(
                Double.parseDouble(t[4]),
                Double.parseDouble(t[5]),
                Double.parseDouble(t[6])
        );

        scene.getLights().add(new PointLight(pos, color));
    }

    /** Crée une sphère avec les matériaux courants. */
    private void parseSphere(String[] t) {
        Point center = new Point(
                Double.parseDouble(t[1]),
                Double.parseDouble(t[2]),
                Double.parseDouble(t[3])
        );

        double radius = Double.parseDouble(t[4]);

        Sphere sphere = new Sphere(center, radius);
        sphere.setDiffuse(currentDiffuse);
        sphere.setSpecular(currentSpecular);
        sphere.setShininess(currentShininess);

        scene.getShapes().add(sphere);
    }

    /** Initialise la liste des sommets. */
    private void parseMaxVerts(String[] t) {
        maxVerts = Integer.parseInt(t[1]);
        vertexList = new ArrayList<>(maxVerts);
    }

    /** Ajoute un sommet dans la liste globale. */
    private void parseVertex(String[] t) {
        vertexList.add(new Point(
                Double.parseDouble(t[1]),
                Double.parseDouble(t[2]),
                Double.parseDouble(t[3])
        ));
    }

    /** Crée un triangle en référençant 3 indices de la liste de sommets. */
    private void parseTriangle(String[] t) throws Exception {
        int a = Integer.parseInt(t[1]);
        int b = Integer.parseInt(t[2]);
        int c = Integer.parseInt(t[3]);

        if (a >= maxVerts || b >= maxVerts || c >= maxVerts)
            throw new Exception("Index vertex hors limites");

        Triangle tri = new Triangle(
                vertexList.get(a),
                vertexList.get(b),
                vertexList.get(c)
        );

        tri.setDiffuse(currentDiffuse);
        tri.setSpecular(currentSpecular);
        tri.setShininess(currentShininess);

        scene.getShapes().add(tri);
    }

    /** Crée un plan */
    private void parsePlane(String[] t) {
        Point pos = new Point(
                Double.parseDouble(t[1]),
                Double.parseDouble(t[2]),
                Double.parseDouble(t[3])
        );

        Vector normal = new Vector(
                Double.parseDouble(t[4]),
                Double.parseDouble(t[5]),
                Double.parseDouble(t[6])
        );

        Plane plane = new Plane(pos, normal);
        plane.setDiffuse(currentDiffuse);
        plane.setSpecular(currentSpecular);
        plane.setShininess(currentShininess);

        scene.getShapes().add(plane);
    }
}