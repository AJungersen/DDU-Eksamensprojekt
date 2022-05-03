/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import javafx.scene.image.Image;

/**
 *
 * @author chris
 */
public enum ProductCategory {
    FRUGT_OG_GRØNT,
    MEJERIPRODUKTER,
    KØD_OG_FISK,
    FROST,
    PÅLÆG,
    DRIKKEVARER,
    BRØD_KIKS_OG_KAGER,
    SLIK_OG_SNACKS;

    private Image image;

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String asFormatedString() {
        String convertedString = "";

        String[] bits = ProductCategory.this.toString().split("_");

        for (int i = 0; i < bits.length; i++) {
            if (i == 0) {
                convertedString = bits[i].substring(0, 1).toUpperCase() + bits[i].substring(1).toLowerCase();
            } else {
                if (ProductCategory.this == BRØD_KIKS_OG_KAGER && i == 1) {
                    convertedString += ", " + bits[i].toLowerCase();
                } else {
                    convertedString += " " + bits[i].toLowerCase();
                }
            }
        }
        return convertedString;
    }
}
