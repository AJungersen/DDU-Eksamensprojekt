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
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Clara Maj
 */
public class WalletController implements Initializable {

    private ObservableList<CreditCard> creditCards;

    @FXML
    private Button backButton;
    @FXML
    private Button createCard;
    @FXML
    private Button viewCard;
    @FXML
    private ListView<CreditCard> cardview;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            if (App.getLoggedInUser().getWallet() != null && App.getLoggedInUser().getWallet().getCreditCards() != null) {
                System.out.println("wallet not null");

                cardview.setCellFactory(new Callback<ListView<CreditCard>, ListCell<CreditCard>>() {
                    @Override
                    public ListCell<CreditCard> call(ListView<CreditCard> p) {
                        return new ListCell<>() {
                            @Override
                            public void updateItem(CreditCard _creditCard, boolean empty) {
                                super.updateItem(_creditCard, empty);
                                if (empty || createCard == null) {
                                    setText(null);
                                } else {
                                    setText(_creditCard.getNameOfCard());
                                }
                            }
                        };
                    }
                });
                
                creditCards = FXCollections.observableList(App.getLoggedInUser().getWallet().getCreditCards());

                cardview.setItems(creditCards);
                
            } else {
                System.out.println("null");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void openCreateCard(ActionEvent event) throws IOException {

        Stage stage = App.getStage();
        Popup popup = new Popup();

        popup.getContent().addAll(App.loadFXML("walletCreate").getChildrenUnmodifiable());
        popup.setX(stage.getWidth() * 1.3);
        popup.setY(stage.getHeight() / 2);

        App.setPopup(popup);

        App.openPopup();

    }

    @FXML
    private void openProfile() throws IOException {
        App.setRoot("profile");
    }
}
