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
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import repository.AdminDataBaseMethods;

/**
 *
 * @author clara
 */
public class CreateCategoryController implements Initializable {

    private final FileChooser fc = new FileChooser();
    private File selectedFiles;
    private AdminDataBaseMethods adbm = new AdminDataBaseMethods();
    private String comboBoxCategoryPrompText;

    @FXML
    private ImageView CategoryImage;
    @FXML
    private ComboBox<ProductCategory> comboboxCategory;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            comboboxCategory.setCellFactory(new Callback<ListView<ProductCategory>, ListCell<ProductCategory>>() {
                @Override
                public ListCell<ProductCategory> call(ListView<ProductCategory> p) {
                    return new ListCell<>() {
                        @Override
                        public void updateItem(ProductCategory item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty || item == null) {
                                setText(null);
                            } else {
                                setText(item.asFormatedString());
                            }
                        }
                    };
                }
            });

            comboboxCategory.setButtonCell(
                    new ListCell<ProductCategory>() {
                @Override
                protected void updateItem(ProductCategory item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(comboBoxCategoryPrompText);
                    } else {
                        setText(item.asFormatedString());
                    }
                }
            });

            comboboxCategory.getItems().addAll(ProductCategory.values());
            comboBoxCategoryPrompText = comboboxCategory.getPromptText();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void SetCategoryImage(ActionEvent event) throws Exception {

        try {
            comboboxCategory.getSelectionModel().getSelectedItem().setImage(CategoryImage.getImage());

            FileInputStream fis = new FileInputStream(selectedFiles);

            adbm.bindImageToProductCategory(comboboxCategory.getSelectionModel().getSelectedItem(), selectedFiles);

            comboboxCategory.getSelectionModel().clearSelection();
            CategoryImage.setImage(null);
            
        } catch (NullPointerException e) {
            System.out.println("nyt image ikke valgt");
        }
    }

    @FXML
    private void SelectImage(ActionEvent event) throws Exception {
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
    private void displayImage(ActionEvent event) {
        if (!comboboxCategory.getSelectionModel().isEmpty()) {
            CategoryImage.setImage(comboboxCategory.getSelectionModel().getSelectedItem().getImage());
        }
    }

    @FXML
    private void closePopUp(ActionEvent event) throws Exception {
        App.closePopup();
    }
}
