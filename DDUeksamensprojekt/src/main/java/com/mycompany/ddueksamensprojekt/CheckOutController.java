/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.ddueksamensprojekt;

import Classes.CreditCard;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Popup;
import javafx.stage.Stage;
import repository.UserDatabaseMethods;

/**
 * FXML Controller class
 *
 * @author Clara Maj
 */
public class CheckOutController implements Initializable {

    /**
     * Initializes the controller class.
     */
   @FXML
   TextField goodNumber;
   @FXML
   TextField totalPrice;
   @FXML
   TextField cardHolder = new TextField();
   @FXML
   TextField cardNumber = new TextField();
   @FXML
   TextField expirationDate = new TextField();
   @FXML
   TextField CSV = new TextField();
   @FXML
   TextField cardName = new TextField();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        goodNumber.setText(Integer.toString(App.numberOfGoods));
        totalPrice.setText(Float.toString(App.priceOfGoods));
    }    
    @FXML
    private void closePopUp(ActionEvent event) throws Exception{
        App.closePopup();
    }
    
    void saveCard(){
       /* UserDatabaseMethods udm = new UserDatabaseMethods();
        if(cardHolder.getText() != null && Pattern.matches("[0-9]{16}", cardNumber.getText()) && Pattern.matches("[\\d]{2}[/][\\d]{2}", expirationDate.getText()) && Pattern.matches("[\\d]{3}", CSV.getText()) && cardName != null){
            udm.saveCreditCard(new CreditCard(-1, expirationDate.getText(), cardNumber.getText(), CSV.getText()), App.getLoggedInUser());
        } else {
            //send error message
        }*/
    }
}
