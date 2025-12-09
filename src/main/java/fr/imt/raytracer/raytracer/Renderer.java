package fr.imt.raytracer.raytracer;

import fr.imt.raytracer.imaging.Color;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * La classe {@code Renderer} est responsable de l'orchestration du processus de rendu.
 * Elle itère sur chaque pixel de l'image de sortie, utilise le {@link RayTracer} pour calculer
 * la couleur de ce pixel, et écrit le résultat dans un fichier image.
 */
public class Renderer {

    /**
     * Lance le processus de rendu de la scène et sauvegarde l'image résultante.
     *
     *
     * @param scene La scène complète à rendre, incluant la caméra, les objets et les paramètres de sortie.
     * @throws Exception Si une erreur survient lors de la création de l'image ou de l'écriture du fichier.
     */
    public void render(Scene scene) throws Exception {

        int width = scene.getWidth();
        int height = scene.getHeight();

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        RayTracer rayTracer = new RayTracer();

        System.out.println("Début du rendu de la scène...");

        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {

                int reversedJ = height - j - 1;

                Color c = rayTracer.getPixelColor(i, j, scene);

                image.setRGB(i, reversedJ, c.toRGB());
            }
        }

        String outputDir = "src/main/resources/output_images/";

        File outputFolder = new File(outputDir);
        if (!outputFolder.exists()) {
            outputFolder.mkdirs();
            System.out.println("Dossier de sortie créé : " + outputDir);
        }

        String fullPath = outputDir + scene.getOutput();
        File outputFile = new File(fullPath);

        ImageIO.write(image, "png", outputFile);

        System.out.println("Image générée avec succès : " + fullPath);

    }
}