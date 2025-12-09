package fr.imt;

import fr.imt.raytracer.parsing.SceneFileParser;
import fr.imt.raytracer.raytracer.Renderer;
import fr.imt.raytracer.raytracer.Scene;

public class Main {

    public static void main(String[] args) throws Exception {

        if (args.length == 0) {
            System.err.println("Erreur : Aucun fichier scène spécifié.");
            System.err.println("Usage : java fr.imt.Main <chemin_vers_fichier.scene>");
            System.exit(1);
        }


        String sceneFilePath = args[0];

        System.out.println("Chargement de la scène : " + sceneFilePath);

        SceneFileParser parser = new SceneFileParser();

        Scene scene = parser.parse(sceneFilePath);

        Renderer renderer = new Renderer();
        renderer.render(scene);
    }

}