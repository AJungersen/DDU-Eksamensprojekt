/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.ddueksamensprojekt;

import Classes.CreditCard;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Popup;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Clara Maj
 */
public class WalletController implements Initializable {

    @FXML
    private Button backButton;
    @FXML
    private Button createCard;
    @FXML
    private Button viewCard;
    @FXML
    private ListView cardview;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(App.getLoggedInUser().getWallet().getCreditCards().size() != 0){
        ObservableList Cards = FXCollections.observableArrayList();
        for(CreditCard C: App.getLoggedInUser().getWallet().getCreditCards()){
            Cards.add(C.getCardNumber());
            cardview.setItems(Cards);
        }
        }
    }
    @FXML
    public void openCreateCard(ActionEvent event) throws IOException {

        Stage stage = App.getStage();
        Popup popup = new Popup();

        popup.getContent().addAll(App.loadFXML("walletCreate").getChildrenUnmodifiable());
        popup.setX(stage.getWidth()*1.3);
        popup.setY(stage.getHeight()/2);

        App.setPopup(popup);

        App.openPopup();

    }

}
