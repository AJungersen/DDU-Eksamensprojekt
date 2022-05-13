/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ddueksamensprojekt;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import repository.StoreDatabaseMethods;

/**
 *
 * @author clara
 */
public class CreateProductController implements Initializable {
    
    @FXML private TextField productName;
    @FXML private TextField productPrice;
    @FXML private TextField productSortiment;
    private final FileChooser fc = new FileChooser();
    private File selectedFiles;
    private Image selectedImage;
    @FXML private ImageView productImage;
    @FXML private TextField productCategory;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    @FXML
    private void closePopUp(ActionEvent event) throws Exception{
        App.closePopup();
    }
    @FXML
    private void SelectImage(ActionEvent event) throws Exception{
        fc.setTitle("Select files");

        //Starting route for file chooser
        fc.setInitialDirectory(new File(System.getProperty("user.home")));

        //Type filters, since the file we are looking for is json we dont need the others
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("image files", "*.jpg", "*.jpeg", "*.png"));
        
        //selectedFiles = fc.showOpenMultipleDialog(null).get(0);
        selectedFiles = fc.showOpenDialog(null);
        
        selectedImage = new Image(new FileInputStream(selectedFiles));
        
        productImage.setImage(selectedImage);
    }
    @FXML
    private void createProduct(ActionEvent event) throws Exception{
        StoreDatabaseMethods sdm = new StoreDatabaseMethods();
        if (!productName.getText().isBlank() && Pattern.matches("[\\d]", productPrice.getText()) && Pattern.matches("[\\d]", productSortiment.getText()) && !productCategory.getText().isBlank() ){
            sdm.saveProduct(new Product(-1, productName.getText(), selectedImage, Float.parseFloat(productPrice.getText()), Integer.parseInt(productSortiment.getText()), productCategory.getText()));

        
        
        
        App.closePopup();
    }
}