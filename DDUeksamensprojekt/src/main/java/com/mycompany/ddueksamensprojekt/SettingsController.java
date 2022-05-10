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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Popup;
import javafx.stage.Stage;

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

    @FXML
    public void changePassword(ActionEvent event) throws IOException {

        Stage stage = App.getStage();
        Popup popup = new Popup();

        popup.getContent().addAll(App.loadFXML("changePassword").getChildrenUnmodifiable());
        popup.setX(stage.getWidth() * 1.3);
        popup.setY(stage.getHeight() / 2);

        for (Node n : popup.getContent()) {
            System.out.println("test 1");
            if (n instanceof Pane) {
                System.out.println("test 2");
                for (Node nP : ((Pane) n).getChildren()) {
                    if (nP.getId() == "buttonCancel") {
                        ((Button) nP).setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent aevent) {
                                App.closePopup();
                            }
                        });
                    } else if (nP.getId() == "buttonChangePassword") {
                        ((Button) nP).setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent aevent) {
                            }
                        });
                    }
                }
            }
        }

        App.setPopup(popup);

        App.openPopup();

    }
}
