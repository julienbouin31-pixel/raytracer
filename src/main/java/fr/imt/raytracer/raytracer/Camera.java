package fr.imt.raytracer.raytracer;


import fr.imt.raytracer.geometry.Point;
import fr.imt.raytracer.geometry.Vector;

public class Camera {

    private Point lookFrom;
    private Point lookAt;
    private Vector up;
    private double fov;

    public Camera(Point lookFrom, Point lookAt, Vector up, double fov) {
        this.lookFrom = lookFrom;
        this.lookAt = lookAt;
        this.up = up;
        this.fov = fov;
    }

    public Point getLookFrom() { return lookFrom; }
    public Point getLookAt() { return lookAt; }
    public Vector getUp() { return up; }
    public double getFov() { return fov; }

    @Override
    public String toString() {
        return "Camera[from=" + lookFrom + ", at=" + lookAt + ", up=" + up + ", fov=" + fov + "]";
    }

}

