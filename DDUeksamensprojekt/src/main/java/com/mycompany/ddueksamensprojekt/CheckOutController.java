/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.ddueksamensprojekt;

import Classes.Cart;
import Classes.CreditCard;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Callback;
import repository.StoreDatabaseMethods;
import repository.UserDatabaseMethods;

/**
 * FXML Controller class
 *
 * @author Clara Maj
 */
public class CheckOutController implements Initializable {
    private StoreDatabaseMethods sdm = new StoreDatabaseMethods();
    
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
    @FXML
    private ChoiceBox<CreditCard> choiceBoxUsersCards;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        goodNumber.setText(Integer.toString(App.numberOfGoods));
        totalPrice.setText(Float.toString(App.priceOfGoods));
        
        choiceBoxUsersCards.setItems(FXCollections.observableArrayList(App.getLoggedInUser().getWallet().getCreditCards()));
        choiceBoxUsersCards.getSelectionModel().select(0);
    }    
    @FXML
    private void closePopUp(ActionEvent event) throws Exception{
        App.closePopup();
    }
    
    void saveCard() throws Exception{
       UserDatabaseMethods udm = new UserDatabaseMethods();
        if(cardHolder.getText() != null && Pattern.matches("[0-9]{16}", cardNumber.getText()) && Pattern.matches("[\\d]{2}[/][\\d]{2}", expirationDate.getText()) && Pattern.matches("[\\d]{3}", CSV.getText()) && cardName != null){
            udm.saveCreditCard(new CreditCard(-1, expirationDate.getText(), cardNumber.getText(), CSV.getText(), cardHolder.getText(), cardName.getText()),App.getLoggedInUser());
        } else {
            System.out.println("ikke oprettet");
        }
    }

    @FXML
    private void checkOut(ActionEvent event) throws Exception {
        if(!choiceBoxUsersCards.getSelectionModel().isEmpty() ||
                (!cardHolder.getText().isBlank() && Pattern.matches("[\\d]{16}", cardNumber.getText()) && 
                Pattern.matches("[\\d]{2}[/][\\d]{2}", expirationDate.getText()) && 
                Pattern.matches("[\\d]{3}", CSV.getText()) && !cardName.getText().isBlank())){
            
            sdm.makePurchase(App.getCurrentCart(), App.getLoggedInUser());
            
            App.setCurrentCart(new Cart(-1, new ArrayList<>()));
            
            App.setRoot("cart");
            
            closePopUp(event);
        }
    }
}
