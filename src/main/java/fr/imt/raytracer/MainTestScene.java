package fr.imt.raytracer;

import fr.imt.raytracer.parsing.SceneFileParser;
import fr.imt.raytracer.raytracer.Scene;

public class MainTestScene {

    public static void main(String[] args) throws Exception {

        SceneFileParser parser = new SceneFileParser();

        String file = "src/main/resources/scenes/jalon2/test1.scene";

        Scene scene = parser.parse(file);

        System.out.println("===== SCENE PARSING RESULT =====\n");

        System.out.println("Image Size : " + scene.getWidth() + " x " + scene.getHeight());
        System.out.println("Output     : " + scene.getOutput());

        System.out.println("\n--- CAMERA ---");
        System.out.println(scene.getCamera());

        System.out.println("\n--- AMBIENT COLOR ---");
        System.out.println(scene.getAmbient());

        System.out.println("\n--- LIGHTS (" + scene.getLights().size() + ") ---");
        scene.getLights().forEach(l -> System.out.println("  " + l));

        System.out.println("\n--- SHAPES (" + scene.getShapes().size() + ") ---");
        scene.getShapes().forEach(s -> System.out.println("  " + s));

        System.out.println("\n===== END OF PARSING =====");
    }
}
