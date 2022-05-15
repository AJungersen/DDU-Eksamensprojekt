/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ddueksamensprojekt;

import Classes.ProductCategory;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;

import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import repository.AdminDataBaseMethods;
import repository.Tools;
/**
 * FXML Controller class
 *
 * @author Christoffer
 */
public class ProductInformationAdminController implements Initializable {


    @FXML
    private ImageView imageViewProduct;
    @FXML
    private TextField textFieldName;
    @FXML
    private TextField textFieldStock;
    @FXML
    private TextField textFieldPrice;
    @FXML
    private Text returnButton;
    @FXML
    private ChoiceBox<ProductCategory> comboboxCategory;
    private final FileChooser fc = new FileChooser();
    private File selectedFiles;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            comboboxCategory.getItems().addAll(ProductCategory.values());

            comboboxCategory.setOnAction(event -> {
                imageViewProduct.setImage(comboboxCategory.getSelectionModel().getSelectedItem().getImage());
            });
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }    
    
    @FXML
    private void saveProduct(ActionEvent event) throws Exception {
        AdminDataBaseMethods adm = new AdminDataBaseMethods();
        
        if(!textFieldName.getText().isBlank() && textFieldPrice.getText().isBlank()
                && textFieldStock.getText().isBlank()){
            adm.updateProductInfo(App.getCurrentProduct(), selectedFiles);
        }
    }

    @FXML
    private void goBack(ActionEvent event) throws IOException {
        App.switchToLastScene();
        App.setLastSceneFxml("productInformationAdmin");
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
            
            if (event.getCharacter().equals(".")) {
                String regex = "\\.";
                ((TextField) event.getTarget()).setText(((TextField) event.getTarget()).getText().replaceFirst(regex, ""));
            } else {
                ((TextField) event.getTarget()).setText(((TextField) event.getTarget()).getText().replaceFirst(event.getCharacter(), ""));
            }
            //update courser position to end
            ((TextField) event.getTarget()).positionCaret(((TextField) event.getTarget()).getText().length());
        }
    }

    @FXML
    private void deleteProduct(ActionEvent event) throws Exception {
        AdminDataBaseMethods adm = new AdminDataBaseMethods();
        adm.deleteProduct(App.getCurrentProduct().getItem_ID());
    }
    

    @FXML
    private void choseImage(ActionEvent event) throws FileNotFoundException {
        fc.setTitle("Select files");

        //Starting route for file chooser
        fc.setInitialDirectory(new File(System.getProperty("user.home")));

        //Type filters, since the file we are looking for is json we dont need the others
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("image files", "*.jpg", "*.jpeg", "*.png"));
        
        //selectedFiles = fc.showOpenMultipleDialog(null).get(0);
        selectedFiles = fc.showOpenDialog(null);
        
        imageViewProduct.setImage(new Image(new FileInputStream(selectedFiles)));
    }

    @FXML
    private void openMain(MouseEvent event) throws IOException {
        App.setRoot("mainAdmin");
    }

    @FXML
    private void openProfile(MouseEvent event) throws IOException {
        App.setRoot("profileAdmin");
    }

    @FXML
    private void openCart(MouseEvent event) throws IOException {
        App.setRoot("main");
    }

}
