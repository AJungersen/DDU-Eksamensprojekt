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
    int productID;
    String name;
    Image image;
    float price;
    int stock;

    public Product(int productID, String name, Image image, float price, int stock) {
        this.productID = productID;
        this.name = name;
        this.image = image;
        this.price = price;
        this.stock = stock;
    }

    public int getProductID() {
        return productID;
    }

    public String getName() {
        return name;
    }

    public Image getImage() {
        return image;
    }

    public float getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
    
}
