/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.ddueksamensprojekt;

import static Classes.ProductCategory.SLIK_OG_SNACKS;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Clara Maj
 */
public class ProductInformationController implements Initializable {
    Product product = new Product();
    @FXML
    private TextField textFieldPrice;
    @FXML
    private TextField textFieldStock;
    @FXML
    private ImageView imageViewProduct;
    @FXML
    private TextField textFieldNumberOfProduct;
    @FXML
    private Text textProductName;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        product = App.getCurrentProduct();
        
        textProductName.setText(product.getName());
        textFieldPrice.setText(Float.toString(product.getPrice()));
        textFieldStock.setText(((product.getStock()>0) ? "på lager":"ikke på lager"));
        imageViewProduct.setImage(product.getImage());
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
        for(int i = 0; i < Integer.parseInt(textFieldNumberOfProduct.getText()); i++){
            App.currentCart.getProductsAsList().add(product);
        }
    }
}
