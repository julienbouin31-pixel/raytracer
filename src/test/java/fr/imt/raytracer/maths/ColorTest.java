package fr.imt.raytracer.maths;

import fr.imt.raytracer.imaging.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ColorTest {

    @Test
    void testDefaultConstructorIsBlack() {
        Color c = new Color();
        assertEquals(new Color(0, 0, 0), c);
    }

    @Test
    void testClampValues() {
        Color c = new Color(2, -1, 0.5);

        assertEquals(new Color(1, 0, 0.5), c);
    }

    @Test
    void testSchurProduct() {
        Color c1 = new Color(1, 0.5, 0.2);
        Color c2 = new Color(0.5, 0.5, 0.5);

        assertEquals(new Color(0.5, 0.25, 0.1), c1.schur(c2));
    }

    @Test
    void testScale() {
        Color c = new Color(0.2, 0.4, 0.6);
        assertEquals(new Color(0.4, 0.8, 1.0), c.scale(2));
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
    void testEqualsFloating() {
        Color c1 = new Color(0.500000001, 0.2, 0.3);
        Color c2 = new Color(0.500000002, 0.2, 0.3);

        assertEquals(c1, c2);
    }
}
