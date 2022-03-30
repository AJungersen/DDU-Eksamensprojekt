/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.ddueksamensprojekt;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    public void openCreateCard(MouseEvent event) throws IOException{
        Stage stage = App.getStage();

            Popup popup = new Popup();
            popup.getContent().addAll(App.loadFXML("walletCreate").getChildrenUnmodifiable());
            popup.setX(stage.getWidth()/1.4);
            popup.setY(stage.getHeight()/2);

            App.setPopup(popup);

            App.openPopup();
    }
    
    
}
