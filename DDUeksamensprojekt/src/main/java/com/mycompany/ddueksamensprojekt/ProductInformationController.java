/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.ddueksamensprojekt;

import static Classes.ProductCategory.SLIK_OG_SNACKS;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Clara Maj
 */
public class ProductInformationController implements Initializable {
    @FXML
    TextField price;
    @FXML
    TextField stock;
    Product product = new Product("produkt", 20, SLIK_OG_SNACKS);
    @FXML
    TextField numberOfProduct;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //price.setText(Float.toString(App.currentProduct.getPrice()));
        price.setText(Float.toString(product.getPrice()));
        stock.setText("På lager");
        //stock.setText((App.currentProduct.getStock()>0 ? "på lager":"ikke på lager"));
    }    
     @FXML
    private void openProfile() throws IOException {
        App.setRoot("profile");
    }
    @FXML
    private void openMain() throws IOException {
        App.setRoot("main");
    }
    @FXML
    private void openCart() throws IOException {
        App.setRoot("cart");
    }
    @FXML
    private void addToCart() throws IOException{
        for(int i = 0; i < Integer.parseInt(numberOfProduct.getText()); i++){
            App.currentCart.getProductsAsList().add(product);
            //App.currentCart.getProductsAsList().add(App.currentProduct);
        }
        App.setRoot("cart");
    }
}
