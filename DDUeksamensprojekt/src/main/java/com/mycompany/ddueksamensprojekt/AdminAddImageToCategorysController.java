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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import repository.AdminDataBaseMethods;

/**
 *
 * @author chris
 */
public class AdminAddImageToCategorysController implements Initializable {
    private final FileChooser fc = new FileChooser();
    private File selectedFiles;
    private AdminDataBaseMethods adbm = new AdminDataBaseMethods();
    
    @FXML
    private ChoiceBox<ProductCategory> choiceBoxCat;
    @FXML
    private ImageView CategoryImage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            System.out.println("test");
            choiceBoxCat.getItems().addAll(ProductCategory.values());

            choiceBoxCat.setOnAction(event -> {
                CategoryImage.setImage(choiceBoxCat.getSelectionModel().getSelectedItem().getImage());
            });
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void SetCategoryImage(ActionEvent event) throws Exception {
        choiceBoxCat.getSelectionModel().getSelectedItem().setImage(CategoryImage.getImage());
        
        adbm.bindImageToProductCategory(choiceBoxCat.getSelectionModel().getSelectedItem(), selectedFiles);
        
        choiceBoxCat.getSelectionModel().clearSelection();
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
        
        CategoryImage.setImage(new Image(new FileInputStream(selectedFiles)));
    }

    @FXML
    private void closePopUp(ActionEvent event) {
    }
}
