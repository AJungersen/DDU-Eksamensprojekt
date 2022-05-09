/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ddueksamensprojekt;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Popup;
import javafx.stage.Stage;

/**
 *
 * @author Clara Maj
 */
public class ProfileAdminController implements Initializable {

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
    void openMain() throws Exception{
        App.setRoot("mainAdmin");
    }
    @FXML
    void createWorker() throws Exception {
        Stage stage = App.getStage();
        Popup popup = new Popup();

        popup.getContent().addAll(App.loadFXML("createWorker").getChildrenUnmodifiable());
        popup.setX(stage.getWidth()/2);
        popup.setY(stage.getHeight()/2);

        App.setPopup(popup);

        App.openPopup();
    }
}