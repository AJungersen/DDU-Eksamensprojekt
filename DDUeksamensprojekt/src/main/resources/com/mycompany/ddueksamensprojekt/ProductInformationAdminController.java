/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ddueksamensprojekt;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
/**
 * FXML Controller class
 *
 * @author Christoffer
 */
public class ProductInformationAdminController implements Initializable {


    @FXML
    private ImageView imageViewProduct;
    @FXML
    private TextField textFieldStock;
    @FXML
    private TextField textFieldPrice;
    @FXML
    private Text returnButton;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void saveProduct(ActionEvent event) {
    }

    @FXML
    private void goBack(ActionEvent event) {
    }

    @FXML
    private void checkIfKeyTypedIsInteger(KeyEvent event) {
    }

    @FXML
    private void deleteProduct(ActionEvent event) {
    }

    @FXML
    private void choseImage(ActionEvent event) {
    }

    @FXML
    private void closeConfirmation(MouseEvent event) {
    }

    @FXML
    private void openMain(MouseEvent event) {
    }

    @FXML
    private void openProfile(MouseEvent event) {
    }

    @FXML
    private void openCart(MouseEvent event) {
    }

}
