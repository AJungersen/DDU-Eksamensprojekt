/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ddueksamensprojekt;

import Classes.ProductCategory;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import repository.AdminDataBaseMethods;

/**
 *
 * @author chris
 */
public class CreateProductsController implements Initializable {
    private final FileChooser fc = new FileChooser();
    private File selectedFiles;
    private AdminDataBaseMethods adbm = new AdminDataBaseMethods();
    
    @FXML
    private ImageView imageViewProductPhoto;
    @FXML
    private TextField textFieldName;
    @FXML
    private TextField textFieldPrice;
    @FXML
    private ChoiceBox<ProductCategory> choiceBoxProductCategory;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            choiceBoxProductCategory.getItems().clear();
            choiceBoxProductCategory.getItems().addAll(ProductCategory.values());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }    

    @FXML
    private void addImage(ActionEvent event) throws Exception{
        fc.setTitle("Select files");

        //Starting route for file chooser
        fc.setInitialDirectory(new File(System.getProperty("user.home")));

        //Type filters, since the file we are looking for is json we dont need the others
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("jpg file", "*.jpg"));

        //selectedFiles = fc.showOpenMultipleDialog(null).get(0);
        selectedFiles = fc.showOpenDialog(null);
        
        imageViewProductPhoto.setImage(new Image(new FileInputStream(selectedFiles)));
    }

    @FXML
    private void createProduct(ActionEvent event) throws Exception{
        /*Product product = new Product(textFieldName.getText(), Integer.parseInt(textFieldPrice.getText()), 
                choiceBoxProductCategory.getSelectionModel().getSelectedItem());
        
        adbm.createProduct(product, selectedFiles);*/
    }
}
