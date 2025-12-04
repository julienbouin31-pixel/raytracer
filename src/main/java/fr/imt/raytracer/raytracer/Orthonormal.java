package fr.imt.raytracer.raytracer;

import fr.imt.raytracer.geometry.Vector;


public class Orthonormal {

    private final Vector u;
    private final Vector v;
    private final Vector w;

    public Orthonormal(Camera camera) {
        this.w = camera.getLookFrom().sub(camera.getLookAt()).normalize();
        this.u = camera.getUp().cross(w).normalize();
        this.v = w.cross(u).normalize();
    }

    public Vector getU() {
        return u;
    }

    public Vector getV() {
        return v;
    }

    public Vector getW() {
        return w;
    }

    @Override
    public String toString() {
        return "Orthonormal{u=" + u + ", v=" + v + ", w=" + w + '}';
    }
}
