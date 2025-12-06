package fr.imt.raytracer.maths;

import fr.imt.ImageComparator;
import fr.imt.raytracer.parsing.SceneFileParser;
import fr.imt.raytracer.raytracer.Renderer;
import fr.imt.raytracer.raytracer.Scene;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class RayTracerTest {

    private static final String RESOURCES_DIR = "src/main/resources/jalon5/";

    private static final int DEFAULT_TOLERANCE = 1000;


    private void assertRenderMatchesReference(String sceneFileName, String expectedImageName, int tolerance) {
        try {
            String scenePath = RESOURCES_DIR + sceneFileName;
            String expectedPath = RESOURCES_DIR + expectedImageName;

            System.out.println("=== Test : " + sceneFileName + " ===");

            SceneFileParser parser = new SceneFileParser();
            Scene scene = parser.parse(scenePath);

            String generatedImagePath = scene.getOutput();

            Renderer renderer = new Renderer();
            renderer.render(scene);

            File generatedFile = new File(generatedImagePath);
            File expectedFile = new File(expectedPath);

            if (!generatedFile.exists()) {
                fail("Le fichier généré est introuvable : " + generatedImagePath);
            }
            if (!expectedFile.exists()) {
                fail("L'image de référence est introuvable : " + expectedPath);
            }

            BufferedImage actualImage = ImageIO.read(generatedFile);
            BufferedImage expectedImage = ImageIO.read(expectedFile);

            ImageComparator comparator = new ImageComparator(actualImage, expectedImage);
            int differentPixels = comparator.countDifferentPixels();

            System.out.println("Pixels différents : " + differentPixels + " (Seuil : " + tolerance + ")");

            assertTrue(differentPixels <= tolerance,
                    () -> String.format("Échec du rendu pour %s. %d pixels différents (Max autorisé: %d)",
                            sceneFileName, differentPixels, tolerance));

        } catch (Exception e) {
            fail("Exception durant le test de " + sceneFileName + ": " + e.getMessage());
        }
    }

    @Test
    void testSpecular() {
        assertRenderMatchesReference("tp51-specular.test", "tp51-specularr.png", 1000);
    }

    @Test
    void testDiffuse() {
        assertRenderMatchesReference("tp51-diffuse.test", "tp51-diffuse.png", 1000);
    }

    @Test
    void testShadows() {
        assertRenderMatchesReference("tp52.test", "tp52.png", 1000);
    }

    @ParameterizedTest(name = "Rendu de {0} comparé à {1}")
    @CsvSource({
            "tp51-specular.test, tp51-specularr.png",
            "tp51-diffuse.test,  tp51-diffuse.png",
            "tp54.test,          tp54.png",
            "tp55.test,          tp55.png",
            "tp61.test,          tp61_real.png",
            "tp61-dir.test,          tp61-dir.png",
            "tp62-1.test,          tp62-1.png",
            "tp62-2.test,          tp62-2-real.png",
            "tp62-3.test,          tp62-3.png",
            "tp62-4.test,          tp62-4.png",
            "tp62-5.test,          tp62-5.png",
            "tp63.test,          tp63.png"


    })
    void testAllScenes(String sceneFile, String referenceImage) {
        assertRenderMatchesReference(sceneFile, referenceImage, DEFAULT_TOLERANCE);
    }
}