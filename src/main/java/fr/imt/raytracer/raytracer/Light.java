package fr.imt.raytracer.raytracer;


import fr.imt.raytracer.imaging.Color;

/**
 * Classe abstraite de base pour toutes les sources de lumière.
 */
public abstract class Light {

    protected Color color;

    /**
     * Initialise une lumière avec une couleur donnée.
     * @param color La couleur
     */
    public Light(Color color) {
        this.color = color;
    }

    public Color getColor() { return color; }
}