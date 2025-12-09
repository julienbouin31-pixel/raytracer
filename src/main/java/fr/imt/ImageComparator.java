package fr.imt;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * Classe pour comparer deux objets {@link BufferedImage} pixel par pixel.
 */
public class ImageComparator {

    /** * Seuil maximal de pixels différents tolérés pour considérer les deux images comme identiques ("OK"). */
    private static final int PIXEL_DIFFERENCE_THRESHOLD = 1000;

    /** * La première image à comparer (généralement la référence). */
    private BufferedImage image1;

    /** * La seconde image à comparer (généralement l'image générée par le rendu). */
    private BufferedImage image2;


    /**
     * Construit un comparateur d'images avec deux images à analyser.
     * Assurez-vous que les deux images ont les mêmes dimensions pour une comparaison significative.
     * * @param image1 La première image (référence).
     * @param image2 La seconde image (à tester).
     */
    public ImageComparator(BufferedImage image1, BufferedImage image2) {
        this.image1 = image1;
        this.image2 = image2;
    }

    /**
     * Compare les deux images stockées et affiche un verdict ("OK" ou "KO")
     * basé sur le seuil de différence défini par {@code PIXEL_DIFFERENCE_THRESHOLD}.
     * <p>
     * Pour une comparaison plus robuste, utilisez {@link #countDifferentPixels()}.
     */
    public void getDifferentPixels(){
        int width = this.image1.getWidth();
        int height = this.image2.getHeight();
        int differentsPixels = 0;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image1.getRGB(x, y);
                int rgb2 = image2.getRGB(x, y);
                if(rgb != rgb2){
                    differentsPixels++;
                }
            }
        }

        if(differentsPixels > PIXEL_DIFFERENCE_THRESHOLD){
            System.out.println("KO");
            System.out.println("Les deux images diffèrent de "+differentsPixels+" pixels.");
        }else{
            System.out.println("OK");
            System.out.println("Les deux images diffèrent de "+differentsPixels+" pixels.");
        }
    }


    /**
     * Génère une nouvelle image qui met en évidence les différences entre les deux images source.
     * <p>
     * <ul>
     * <li>Pixel identique : Noir ({@link Color#BLACK}).</li>
     * <li>Pixel différent : Rouge ({@link Color#RED}).</li>
     * </ul>
     *
     * @return Une nouvelle {@link BufferedImage} représentant les différences.
     */
    public BufferedImage generateDifferentialImage() {
        int width = this.image1.getWidth();
        int height = this.image2.getHeight();

        if (width != image2.getWidth() || height != image1.getHeight()) {
            throw new IllegalArgumentException("Les images doivent avoir les mêmes dimensions pour générer l'image différentielle.");
        }

        BufferedImage diffImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb1 = image1.getRGB(x, y);
                int rgb2 = image2.getRGB(x, y);

                if (rgb1 == rgb2) {
                    diffImage.setRGB(x, y, Color.BLACK.getRGB());
                } else {
                    diffImage.setRGB(x, y, Color.RED.getRGB());
                }
            }
        }

        return diffImage;
    }

    /**
     * Compte le nombre exact de pixels où les valeurs RVB des deux images diffèrent.
     *
     * @return Le nombre total de pixels différents, ou {@code Integer.MAX_VALUE} si les dimensions des images ne correspondent pas.
     */
    public int countDifferentPixels() {
        int width = this.image1.getWidth();
        int height = this.image1.getHeight();

        if (width != image2.getWidth() || height != image2.getHeight()) {
            return Integer.MAX_VALUE;
        }

        int differentPixels = 0;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (image1.getRGB(x, y) != image2.getRGB(x, y)) {
                    differentPixels++;
                }
            }
        }
        return differentPixels;
    }
}