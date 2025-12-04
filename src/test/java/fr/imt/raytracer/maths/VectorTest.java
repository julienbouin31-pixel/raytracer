package fr.imt.raytracer.maths;

import fr.imt.raytracer.geometry.Vector;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VectorTest {

    @Test
    void testAdd() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(4, 5, 6);

        assertEquals(new Vector(5, 7, 9), v1.add(v2));
    }

    @Test
    void testSub() {
        Vector v1 = new Vector(4, 5, 6);
        Vector v2 = new Vector(1, 2, 3);

        assertEquals(new Vector(3, 3, 3), v1.sub(v2));
    }

    @Test
    void testDotProduct() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(4, -5, 6);

        assertEquals(12, v1.dot(v2)); // 1*4 + 2*(-5) + 3*6
    }

    @Test
    void testCrossProduct() {
        Vector v1 = new Vector(1, 0, 0);
        Vector v2 = new Vector(0, 1, 0);

        assertEquals(new Vector(0, 0, 1), v1.cross(v2));
    }

    @Test
    void testCrossNotCommutative() {
        Vector v1 = new Vector(1, 0, 0);
        Vector v2 = new Vector(0, 1, 0);

        assertNotEquals(v1.cross(v2), v2.cross(v1));
    }

    @Test
    void testLength() {
        Vector v = new Vector(3, 4, 0);
        assertEquals(5, v.length());
    }

    @Test
    void testNormalize() {
        Vector v = new Vector(0, 0, 10);
        assertEquals(new Vector(0, 0, 1), v.normalize());
    }

    @Test
    void testNormalizeZeroVector() {
        Vector v = new Vector(0, 0, 0);
        assertEquals(new Vector(0, 0, 0), v.normalize());
    }

    @Test
    void testScale() {
        Vector v = new Vector(1, 2, 3);
        assertEquals(new Vector(2, 4, 6), v.scale(2));
    }

    @Test
    void testSchurProduct() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(4, 5, 6);

        assertEquals(new Vector(4, 10, 18), (Vector) v1.schur(v2));
    }

    @Test
    void testEqualsWithFloatingPrecision() {
        Vector v1 = new Vector(1.000000001, 2.0, 3.0);
        Vector v2 = new Vector(1.000000002, 2.0, 3.0);

        assertEquals(v1, v2);
    }
}
