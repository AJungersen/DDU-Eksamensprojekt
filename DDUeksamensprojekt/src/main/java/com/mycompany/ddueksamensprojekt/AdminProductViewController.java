package com.mycompany.ddueksamensprojekt;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author chris
 */
public class AdminProductViewController {

    @FXML
    void openProfile() throws Exception{
        App.setRoot("profileAdmin");
    }
    @FXML
    void openShop() throws Exception {
        App.setRoot("main");
    }
    
    @FXML
    void openMain() throws Exception{
        App.setRoot("mianAdmin");
    }
}
