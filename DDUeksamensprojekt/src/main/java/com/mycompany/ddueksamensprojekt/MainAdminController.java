/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.ddueksamensprojekt;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Popup;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Clara Maj
 */
public class MainAdminController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
    
    @FXML
    void openShop() throws Exception {
        App.setRoot("main");
    }
    @FXML
    void openShifts() throws Exception {
        Stage stage = App.getStage();
        Popup popup = new Popup();

        popup.getContent().addAll(App.loadFXML("totalShifts").getChildrenUnmodifiable());
        popup.setX(stage.getWidth()/2);
        popup.setY(stage.getHeight()/2);

        App.setPopup(popup);

        App.openPopup();
    }
    @FXML
    void openFreeShifts() throws Exception {
        Stage stage = App.getStage();
        Popup popup = new Popup();

        popup.getContent().addAll(App.loadFXML("freeShift").getChildrenUnmodifiable());
        popup.setX(stage.getWidth()/2);
        popup.setY(stage.getHeight()/2);

        App.setPopup(popup);

        App.openPopup();
    }
    @FXML
    void openInbox() throws Exception {
        App.setRoot("inbox");
    }
    @FXML
    void createShifts() throws Exception{
        Stage stage = App.getStage();
        Popup popup = new Popup();

        popup.getContent().addAll(App.loadFXML("createShift").getChildrenUnmodifiable());
        popup.setX(stage.getWidth()/2);
        popup.setY(stage.getHeight()/2);

        App.setPopup(popup);

        App.openPopup();
    }
    @FXML
    void openProfile() throws Exception{
        App.setRoot("profileAdmin");
    }
}
