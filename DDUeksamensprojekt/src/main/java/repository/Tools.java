/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

/**
 *
 * @author chris
 */
public class Tools {

    public static Image convertBufferedImageToFxImage(java.awt.image.BufferedImage image) {
        WritableImage wr = null;
        if (image != null) {
            wr = new WritableImage(image.getWidth(), image.getHeight());
            PixelWriter pw = wr.getPixelWriter();
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    pw.setArgb(x, y, image.getRGB(x, y));
                }
            }
        }

        return new ImageView(wr).getImage();
    }

    public static String capitalizeFirstLetter(String _stirngToBeCorveted) {
        return _stirngToBeCorveted.substring(0, 1).toUpperCase()
                + _stirngToBeCorveted.substring(1).toLowerCase();
    }

    public static boolean isInteger(String _stringToCheck) {
        if (_stringToCheck == null) {
            return false;
        }
        try {
            int a = Integer.parseInt(_stringToCheck);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    
    public static boolean isFloat(String _stringToCheck) {
        if (_stringToCheck == null) {
            return false;
        }
        try {
            float a = Float.parseFloat(_stringToCheck);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
