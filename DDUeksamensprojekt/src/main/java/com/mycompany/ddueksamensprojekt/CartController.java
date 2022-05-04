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
import javafx.scene.control.TableView;
import javafx.stage.Popup;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Clara Maj
 */
public class CartController implements Initializable {
    @FXML
    TableView goods;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    @FXML
    void openMain() throws Exception {
        App.setRoot("main");
    }
    @FXML
    void openProfile() throws Exception {
        App.setRoot("profile");
    }
    @FXML
    void openCatelog() throws Exception {
        App.setRoot("catelogView");
    }
    @FXML
    void openCheckOut(ActionEvent event) throws IOException {

        Stage stage = App.getStage();
        Popup popup = new Popup();

        popup.getContent().addAll(App.loadFXML("checkOut").getChildrenUnmodifiable());
        popup.setX(stage.getWidth()*1.3);
        popup.setY(stage.getHeight()/2);

        App.setPopup(popup);

        App.openPopup();
    }
}
