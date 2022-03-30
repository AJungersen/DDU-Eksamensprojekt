/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import javafx.scene.image.Image;

/**
 *
 * @author danie
 */
public class Product {
    private int item_ID;
    private String name;
    private Image image;
    private float price;
    private int stock;
    private SubProductCategory subProductCategory;
    
    //create
    public Product(String name, float price, SubProductCategory subProductCategory) {
        this.name = name;
        this.price = price;
        this.subProductCategory = subProductCategory;
    }

    public Product(int item_ID, String name, Image image, float price, int stock, SubProductCategory subProductCategory) {
        this.item_ID = item_ID;
        this.name = name;
        this.image = image;
        this.price = price;
        this.stock = stock;
        this.subProductCategory = subProductCategory;
    }
    
    public int getItem_ID() {
        return item_ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public SubProductCategory getSubProductCategory() {
        return subProductCategory;
    }

    public void setSubProductCategory(SubProductCategory subProductCategory) {
        this.subProductCategory = subProductCategory;
    }
}
