/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.ddueksamensprojekt;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import repository.StoreLoadMethods;

/**
 * FXML Controller class
 *
 * @author Clara Maj
 */
public class SubcatelogViewController implements Initializable {
    @FXML
    private TextField userInput;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    @FXML
    public void toProfile() throws IOException{
        App.setRoot("profile");
    }
    @FXML
    public void toCart() throws IOException{
        App.setRoot("cart");
    }
    @FXML
    public void search(){
        ArrayList<Product> allProducts = new ArrayList(); //senere skal dette udskiftes med databasekald
        ArrayList<Product> products = new ArrayList();
        String search = userInput.getText();
        for(Product P: allProducts){
            if(Pattern.matches(".*" + search + "+.*",P.getName()) == true){
                products.add(P);
            }
        }
        //Clara noget der smider alle produkter P ind i fxml
    }
}
