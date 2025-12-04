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

public class SceneFileParser {

    private Scene scene = new Scene();

    private List<Point> vertexList = new ArrayList<>();
    private Color currentDiffuse = new Color(0,0,0);
    private Color currentSpecular = new Color(0,0,0);
    private int maxVerts = 0;
    private double currentShininess = 0;



    public Scene parse(String filename) throws Exception {

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {

            String line;

            while ((line = br.readLine()) != null) {
                line = line.trim();

                // ignore comments
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

    private void parseShininess(String[] t) {
        currentShininess = Double.parseDouble(t[1]);
    }

    private void parseMaxDepth(String[] t) {
        scene.setMaxdepth(Integer.parseInt(t[1]));
    }


    private void parseSize(String[] t) {
        scene.setWidth(Integer.parseInt(t[1]));
        scene.setHeight(Integer.parseInt(t[2]));
    }

    private void parseOutput(String[] t) {
        scene.setOutput(t[1]);
    }

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

    private void parseAmbient(String[] t) {
        scene.setAmbient(new Color(
                Double.parseDouble(t[1]),
                Double.parseDouble(t[2]),
                Double.parseDouble(t[3])
        ));
    }

    private void parseDiffuse(String[] t) {
        currentDiffuse = new Color(
                Double.parseDouble(t[1]),
                Double.parseDouble(t[2]),
                Double.parseDouble(t[3])
        );
    }

    private void parseSpecular(String[] t) {
        currentSpecular = new Color(
                Double.parseDouble(t[1]),
                Double.parseDouble(t[2]),
                Double.parseDouble(t[3])
        );
    }

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

    private void parseMaxVerts(String[] t) {
        maxVerts = Integer.parseInt(t[1]);
        vertexList = new ArrayList<>(maxVerts);
    }

    private void parseVertex(String[] t) {
        vertexList.add(new Point(
                Double.parseDouble(t[1]),
                Double.parseDouble(t[2]),
                Double.parseDouble(t[3])
        ));
    }

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
