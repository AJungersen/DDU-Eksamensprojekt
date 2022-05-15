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
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import repository.AdminDataBaseMethods;
import repository.StoreDatabaseMethods;
import repository.Tools;

/**
 *
 * @author clara
 */
public class CreateProductController implements Initializable {

    @FXML
    private TextField productName;
    @FXML
    private TextField productPrice;
    @FXML
    private TextField productSortiment;
    private final FileChooser fc = new FileChooser();
    private File selectedFiles;
    @FXML
    private ImageView productImage;
    @FXML
    private ChoiceBox<ProductCategory> choiceBoxProductCategory;

    /**
     * Initializes the controller class.
     */
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
    private void closePopUp(ActionEvent event) throws Exception {
        App.closePopup();
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

        productImage.setImage(new Image(new FileInputStream(selectedFiles)));
    }

    @FXML
    private void createProduct(ActionEvent event) throws Exception {
        AdminDataBaseMethods adm = new AdminDataBaseMethods();

        if (!productName.getText().isBlank() && !productPrice.getText().isBlank()
                && !productSortiment.getText().isBlank() && !choiceBoxProductCategory.getSelectionModel().isEmpty()) {

            adm.createProduct(new Product(productName.getText(), Float.parseFloat(productPrice.getText()),
                    Integer.parseInt(productSortiment.getText()),
                    choiceBoxProductCategory.getSelectionModel().getSelectedItem()), selectedFiles);

            App.closePopup();
        }
    }

    @FXML
    private void checkIfKeyTypedIsInteger(KeyEvent event) {
        //check if int
        if (!Tools.isInteger(((TextField) event.getTarget()).getText())) {
            //if not remove that char
            ((TextField) event.getTarget()).setText(((TextField) event.getTarget()).getText().replace(event.getCharacter(), ""));

            //update courser position to end
            ((TextField) event.getTarget()).positionCaret(((TextField) event.getTarget()).getText().length());
        }
    }

    @FXML
    private void checkIfKeyTypedIsFloat(KeyEvent event) {
        if (!Tools.isFloat(((TextField) event.getTarget()).getText()) || ((TextField) event.getTarget()).getText().contains("d")
                || ((TextField) event.getTarget()).getText().contains("f")) {

            String text = ((TextField) event.getTarget()).getText();

            if (event.getCharacter().equals(".")) {
                String regex = "\\.";

                String newText = new StringBuilder(new StringBuilder(text).reverse().toString().replaceFirst(regex, "")).reverse().toString();

                ((TextField) event.getTarget()).setText(newText);
            } else {
                ((TextField) event.getTarget()).setText(text.replaceFirst(event.getCharacter(), ""));
            }
            //update courser position to end
            ((TextField) event.getTarget()).positionCaret(((TextField) event.getTarget()).getText().length());
        }
    }
}
