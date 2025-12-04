package fr.imt;


import fr.imt.raytracer.parsing.SceneFileParser;
import fr.imt.raytracer.raytracer.Renderer;
import fr.imt.raytracer.raytracer.Scene;

public class Main {

    public static void main(String[] args) throws Exception {
        SceneFileParser parser = new SceneFileParser();
        Scene scene = parser.parse("src/main/resources/jalon5/tp64.test");

        Renderer renderer = new Renderer();
        renderer.render(scene);
    }

}


