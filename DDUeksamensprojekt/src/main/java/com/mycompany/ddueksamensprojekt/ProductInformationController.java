/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.ddueksamensprojekt;

import static Classes.ProductCategory.SLIK_OG_SNACKS;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.layout.VBox;
import javafx.util.Duration;


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
    private VBox vbox;
    @FXML
    private Parent fxml;
    @FXML 
    private Text returnButton;
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
        //stock.setText((App.currentProduct.getStock()>0 ? "på lager":"ikke på lager"));
        
        try {
            fxml = FXMLLoader.load(getClass().getResource("confirmation.fxml"));
            //vbox.getChildren().removeAll();
            vbox.getChildren().setAll(fxml);            
        } catch (Exception ex) {
            System.out.println("Error");
            //Logger.getLogger(ProductInformationController.class.getName()).log(Level.SEVERE, null, ex);
        }

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
       try{
        for(int i = 0; i < Integer.parseInt(textFieldNumberOfProduct.getText()); i++){
            App.currentCart.getProductsAsList().add(product);
        }
           openConfirmation();
       }catch(Exception e){
           
       }
       
        
    }
    private void openConfirmation () {
        //returnButton.setVisible(true);
        
        // returnvbox.setVisible(false);
       TranslateTransition t = new TranslateTransition(Duration.seconds(1),vbox);
       t.setToY(vbox.getLayoutX()*0.8);
       t.play();
       t.setOnFinished((e)->{
       }); 
       TranslateTransition s = new TranslateTransition(Duration.seconds(1),returnButton);
       s.setToY(38);
       s.play();
       s.setOnFinished((e)->{     
       });
       //returnvbox.setVisible(false);
    }
    
    @FXML
    public void closeConfirmation () {
     TranslateTransition t = new TranslateTransition(Duration.seconds(1),vbox);
       t.setToY(0);
       t.play();
       t.setOnFinished((e)->{
       });
       TranslateTransition s = new TranslateTransition(Duration.seconds(1),returnButton);
       s.setToY(0);
       s.play();
       s.setOnFinished((e)->{     
       });
       
    }
}
