/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ddueksamensprojekt;

import Classes.ProductCategory;
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
    private ProductCategory ProductCategory;

    public Product() {
    }
    
    //create
    public Product(String name, float price, ProductCategory ProductCategory) {
        this.name = name;
        this.price = price;
        this.ProductCategory = ProductCategory;
    }

    public Product(int item_ID, String name, Image image, float price, int stock, ProductCategory productCategory) {
        this.item_ID = item_ID;
        this.name = name;
        this.image = image;
        this.price = price;
        this.stock = stock;
        this.ProductCategory = productCategory;
    }
    
    //used for tableview class
    public Product(Product product) {
        this.item_ID = product.getItem_ID();
        this.name = product.getName();
        this.image = product.getImage();
        this.price = product.getPrice();
        this.stock = product.getStock();
        this.ProductCategory = product.getProductCategory();
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

    public ProductCategory getProductCategory() {
        return ProductCategory;
    }

    public void setProductCategory(ProductCategory ProductCategory) {
        this.ProductCategory = ProductCategory;
    }
}
