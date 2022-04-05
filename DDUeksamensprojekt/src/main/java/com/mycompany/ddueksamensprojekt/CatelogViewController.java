/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.ddueksamensprojekt;

import Classes.ProductCategory;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Clara Maj
 */
public class CatelogViewController implements Initializable {

    AnchorPane view = new AnchorPane();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            /*for (ProductCategory ) {
                Image img;
                ImageView imgView = new ImageView(img);

                imgView.setFitWidth(0);
                imgView.setFitHeight(0);
            }*/
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

}
