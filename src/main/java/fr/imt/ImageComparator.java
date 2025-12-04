package fr.imt;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class ImageComparator {

    private static final int PIXEL_DIFFERENCE_THRESHOLD = 1000;
    private BufferedImage image1;
    private BufferedImage image2;
    



    public ImageComparator(BufferedImage image1, BufferedImage image2) {
        this.image1 = image1;
        this.image2 = image2;
    }

    public void getDifferentPixels(){
        int width = this.image1.getWidth();
        int height = this.image2.getHeight();
        int differentsPixels =0;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image1.getRGB(x, y);
                int rgb2 = image2.getRGB(x, y);
                if(rgb!=rgb2){
                    differentsPixels++;
                }
            }
        }
        if(differentsPixels>PIXEL_DIFFERENCE_THRESHOLD){
            System.out.println("KO");
            System.out.println("Les deux images diffèrent de "+differentsPixels+" pixels.");
        }else{
            System.out.println("OK");
            System.out.println("Les deux images diffèrent de "+differentsPixels+" pixels.");
        }
    }


    public BufferedImage generateDifferentialImage() {
        int width = this.image1.getWidth();
        int height = this.image2.getHeight();
    
     
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

    // Dans la classe fr.imt.ImageComparator

    public int countDifferentPixels() {
        int width = this.image1.getWidth();
        int height = this.image2.getHeight();

        // Vérification de base
        if (width != image2.getWidth() || height != image2.getHeight()) {
            // Retourner un nombre très élevé pour forcer l'échec si les tailles diffèrent
            return Integer.MAX_VALUE;
        }

        int differentPixels = 0;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // Vous pouvez utiliser une tolérance ici pour les erreurs de flottants (delta),
                // mais pour l'instant on garde une comparaison brute (rgb != rgb2)
                if (image1.getRGB(x, y) != image2.getRGB(x, y)) {
                    differentPixels++;
                }
            }
        }
        return differentPixels;
    }
    

    
    
}
