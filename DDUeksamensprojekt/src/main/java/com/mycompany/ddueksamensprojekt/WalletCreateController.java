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
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import repository.UserDatabaseMethods;

/**
 * FXML Controller class
 *
 * @author Clara Maj
 */
public class WalletCreateController implements Initializable {

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        System.out.println(App.getLoggedInUser().getWallet());

        System.out.println(App.getStage().getScene().getRoot());

        for (Node n : ((Pane) App.getStage().getScene().getRoot().getChildrenUnmodifiable().get(0)).getChildrenUnmodifiable()) {
            if (n instanceof ListView) {
                System.out.println("test");
            }
        }
    }

    @FXML
    private void closePopUp(ActionEvent event) throws Exception {
        App.closePopup();
    }

    @FXML
    private void saveCard() throws IOException, Exception {
        UserDatabaseMethods udm = new UserDatabaseMethods();
        if (!cardHolder.getText().isBlank() && Pattern.matches("[\\d]{16}", cardNumber.getText()) && Pattern.matches("[\\d]{2}[/][\\d]{2}", expirationDate.getText()) && Pattern.matches("[\\d]{3}", CSV.getText()) && !cardName.getText().isBlank()) {
            udm.saveCreditCard(new CreditCard(-1, expirationDate.getText(), cardNumber.getText(), CSV.getText(), cardHolder.getText(), cardName.getText()), App.getLoggedInUser());

            App.getLoggedInUser().getWallet().setCreditCards(udm.getAllUsersCreditCards(App.getLoggedInUser().getUser_ID()));

            //this is gonna be uckly be prepared
        } else {
            System.out.println(!cardHolder.getText().isBlank());
            System.out.println(Pattern.matches("[\\d]{16}", cardNumber.getText()));
            System.out.println(Pattern.matches("[\\d]{2}[/][\\d]{2}", expirationDate.getText()));
            System.out.println(Pattern.matches("[\\d]{3}", CSV.getText()));
            System.out.println(!cardName.getText().isBlank());
            System.out.println("ikke oprettet");
        }
        App.closePopup();
    }
}
