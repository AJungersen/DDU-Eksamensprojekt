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
}
