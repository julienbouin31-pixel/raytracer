package fr.imt.raytracer.maths;

import fr.imt.raytracer.geometry.Point;
import fr.imt.raytracer.geometry.Vector;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PointTest {

    @Test
    void testAddVector() {
        Point p = new Point(1, 2, 3);
        Vector v = new Vector(4, 5, 6);
        assertEquals(new Point(5, 7, 9), p.add(v));
    }

    @Test
    void testSubPoint() {
        Point p1 = new Point(4, 5, 6);
        Point p2 = new Point(1, 2, 3);
        assertEquals(new Vector(3, 3, 3), p1.sub(p2));
    }

    @Test
    void testScale() {
        Point p = new Point(1, 2, 3);
        assertEquals(new Point(2, 4, 6), p.scale(2));
    }

    @Test
    void testEquals() {
        assertEquals(new Point(1, 2, 3), new Point(1, 2, 3));
    }
}