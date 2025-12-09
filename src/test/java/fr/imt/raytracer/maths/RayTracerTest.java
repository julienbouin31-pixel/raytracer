package fr.imt.raytracer.maths;

import fr.imt.ImageComparator;
import fr.imt.raytracer.parsing.SceneFileParser;
import fr.imt.raytracer.raytracer.Renderer;
import fr.imt.raytracer.raytracer.Scene;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class RayTracerTest {

    private static final String RESOURCES_DIR = "src/main/resources/scenes/";

    private static final String GENERATED_OUTPUT_DIR = "src/main/resources/output_images/";

    private static final int DEFAULT_TOLERANCE = 1000;


    private void assertRenderMatchesReference(String sceneFileName, String expectedImageName, int tolerance) {
        try {
            String scenePath = RESOURCES_DIR + sceneFileName;

            System.out.println("=== Test : " + sceneFileName + " ===");

            SceneFileParser parser = new SceneFileParser();
            Scene scene = parser.parse(scenePath);

            Renderer renderer = new Renderer();
            renderer.render(scene);

            String expectedPath = RESOURCES_DIR + expectedImageName;

            String generatedImagePath = GENERATED_OUTPUT_DIR + scene.getOutput();

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


    @ParameterizedTest(name = "Rendu de {0} comparé à {1}")
    @CsvSource({
            "jalon5/tp51-specular.test, jalon5/tp51-specular.png",
            "jalon5/tp51-diffuse.test,  jalon5/tp51-diffuse.png",
            "jalon5/tp52.test,          jalon5/tp52.png",
            "jalon5/tp54.test,          jalon5/tp54.png",
            "jalon5/tp55.test,          jalon5/tp55.png"
    })
    void testSceneJalon5(String sceneFile, String referenceImage) {
        assertRenderMatchesReference(sceneFile, referenceImage, DEFAULT_TOLERANCE);
    }

    @ParameterizedTest(name = "Rendu de {0} comparé à {1}")
    @CsvSource({
            "jalon6/tp61.test,          jalon6/tp61.png",
            "jalon6/tp61-dir.test,          jalon6/tp61-dir.png",
            "jalon6/tp62-1.test,          jalon6/tp62-1.png",
            "jalon6/tp62-2.test,          jalon6/tp62-2.png",
            "jalon6/tp62-3.test,          jalon6/tp62-3.png",
            "jalon6/tp62-4.test,          jalon6/tp62-4.png",
            "jalon6/tp62-5.test,          jalon6/tp62-5.png",
            "jalon6/tp63.test,          jalon6/tp63.png",
    })
    void testScenesJalon6(String sceneFile, String referenceImage) {
        assertRenderMatchesReference(sceneFile, referenceImage, DEFAULT_TOLERANCE);
    }
}