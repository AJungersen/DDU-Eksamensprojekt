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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

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
    @FXML private ImageView productImage;
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
        
        productImage.setImage(new Image(new FileInputStream(selectedFiles)));
    }
    @FXML
    private void createProduct(ActionEvent event) throws Exception{
        
        
        
        
        App.closePopup();
    }
}