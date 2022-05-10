/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.ddueksamensprojekt;

import Classes.User;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Clara Maj
 */
public class SettingsController implements Initializable {
    
    private User user = App.getLoggedInUser();
    
    @FXML
    private TextField textFieldEmail;
    @FXML
    private TextField textFieldName;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        textFieldEmail.setText(user.getEmail());
        textFieldName.setText(user.getName());
    }    
    
    @FXML
    private void openWallet() throws IOException {
        App.setRoot("wallet");
    }
    @FXML
    private void openProfile() throws IOException {
        App.setRoot("profile");
    }
}
