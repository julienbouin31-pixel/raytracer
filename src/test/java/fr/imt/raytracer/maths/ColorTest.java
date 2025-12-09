package fr.imt.raytracer.maths;

import fr.imt.raytracer.imaging.Color;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ColorTest {

    @Test
    void testDefaultConstructorIsBlack() {
        Color c = new Color();
        assertEquals(new Color(0, 0, 0), c);
        assertEquals(0.0, c.r());
        assertEquals(0.0, c.g());
        assertEquals(0.0, c.b());
    }

    @Test
    void testClampNegativeValuesOnly() {
        Color c = new Color(2, -1, 0.5);
        assertEquals(new Color(2, 0, 0.5), c, "Les valeurs > 1 ne doivent pas être clampées à la construction");
    }

    @Test
    void testSchurProduct() {
        Color c1 = new Color(1, 0.5, 0.2);
        Color c2 = new Color(0.5, 0.5, 0.5);
        assertEquals(new Color(0.5, 0.25, 0.1), c1.schur(c2));
    }


    @Test
    void testToRGB() {
        Color c = new Color(1, 0.5, 0);
        int rgb = c.toRGB();
        int expected =
                ((255 & 0xff) << 16) |
                        ((128 & 0xff) << 8)  |
                        (0 & 0xff);
        assertEquals(expected, rgb);
    }

    @Test
    void testToRGBWithHDRValues() {
        Color sun = new Color(2.5, 1.0, 0.5);
        int rgb = sun.toRGB();
        int expected = ((255) << 16) | ((255) << 8) | (128);
        assertEquals(expected, rgb, "toRGB devrait ramener les valeurs > 1.0 à 255");
    }

    @Test
    void testEqualsFloating() {
        Color c1 = new Color(0.5000000001, 0.2, 0.3);
        Color c2 = new Color(0.5000000002, 0.2, 0.3);
        assertEquals(c1, c2);
        Color c3 = new Color(0.51, 0.2, 0.3);
        assertNotEquals(c1, c3);
    }
}