/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.ddueksamensprojekt;

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
public class WalletCreateController implements Initializable {
    @FXML
    TextField cardHolder;
    TextField cardNumber;
    TextField expirationDate;
    TextField CSV;
    TextField cardName;
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
    private void saveCard() throws IOException {
        
        App.closePopup();
    }
}
