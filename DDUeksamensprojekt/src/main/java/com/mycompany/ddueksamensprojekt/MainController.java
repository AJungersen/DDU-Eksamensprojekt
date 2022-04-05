/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ddueksamensprojekt;

import Classes.ProductCategory;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 *
 * @author chris
 */
public class MainController implements Initializable{
    AnchorPane ap = new AnchorPane();
    ArrayList<Pane> panes = new ArrayList<>();
    
    private float paneX = 0;
    private float paneY = 0;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            for (ProductCategory pc : ProductCategory.values()) {
                Pane pane = new Pane();
                
                pane.setLayoutX(paneX);
                pane.setLayoutY(paneY);
                
                ImageView imgView = new ImageView(pc.getImage());
                
                //------------------------------------------------------------
                //----------------- MISSING WIDHT AND HEIGHT -----------------
                //------------------------------------------------------------
                imgView.setFitWidth(0);
                imgView.setFitHeight(0);
                
                Button button = new Button(pc.toString());
                
                button.setLayoutX(0);
                button.setLayoutY(0);
                
                pane.getChildren().add(imgView);
                pane.getChildren().add(button);
                
                panes.add(pane);
            }
            
            ap.getChildren().addAll(panes);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
