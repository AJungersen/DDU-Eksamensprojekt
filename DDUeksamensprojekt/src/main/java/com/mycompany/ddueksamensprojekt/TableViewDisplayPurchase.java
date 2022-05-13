package com.mycompany.ddueksamensprojekt;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author chris
 */
public class TableViewDisplayPurchase extends Product {

    private Integer amount;
    private ImageView displayImage;
    private float total;

    public TableViewDisplayPurchase(Integer amount, Product product) {
        super(product);
        this.amount = amount;
    }

    public Integer getAmount() {
        return amount;
    }

    public ImageView getDisplayImage() {
        ImageView iv = new ImageView(getImage());
        
        iv.setFitWidth(50);
        iv.setFitHeight(50);
        
        return iv;
    }

    public float getTotal() {
        return amount * getPrice();
    }
}
