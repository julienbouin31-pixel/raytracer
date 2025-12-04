package fr.imt.raytracer.raytracer;

import fr.imt.raytracer.imaging.Color;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Renderer {

    public void render(Scene scene) throws Exception {

        int width = scene.getWidth();
        int height = scene.getHeight();

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        RayTracer rayTracer = new RayTracer();

        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {

                int reversedJ = height - j - 1;

                Color c = rayTracer.getPixelColor(i, j, scene);
                image.setRGB(i, reversedJ, c.toRGB());
            }
        }

        ImageIO.write(image, "png", new File(scene.getOutput()));
        System.out.println("Image generated : " + scene.getOutput());
    }
}
