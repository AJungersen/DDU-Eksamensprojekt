/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.ddueksamensprojekt;

import Classes.CreditCard;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import repository.UserDatabaseMethods;

/**
 * FXML Controller class
 *
 * @author Clara Maj
 */
public class WalletCreateController implements Initializable {
    @FXML
    TextField cardHolder = new TextField();
    TextField cardNumber = new TextField();
    TextField expirationDate = new TextField();
    TextField CSV = new TextField();
    TextField cardName = new TextField();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        System.out.println(App.getLoggedInUser().getWallet());
    }    
    
    @FXML
    private void closePopUp(ActionEvent event) throws Exception{
        App.closePopup();
    }

    @FXML
    private void saveCard() throws IOException, Exception {
        UserDatabaseMethods udm = new UserDatabaseMethods();
        if(!cardHolder.getText().isBlank() && Pattern.matches("[0-9]{16}", cardNumber.getText()) && Pattern.matches("[\\d]{2}[/][\\d]{2}", expirationDate.getText()) && Pattern.matches("[\\d]{3}", CSV.getText()) && !cardName.getText().isBlank()){
            udm.saveCreditCard(new CreditCard(-1, expirationDate.getText(), cardNumber.getText(), CSV.getText(), cardHolder.getText(), cardName.getText()),App.getLoggedInUser());
        } else {
            System.out.println("ikke oprettet");
        }
        App.closePopup();
    }
}
