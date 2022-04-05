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
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javax.imageio.plugins.tiff.TIFFDirectory;
import repository.Tools;

/**
 *
 * @author chris
 */
public class MainController implements Initializable {

    Pane pane = new Pane();
    ArrayList<StackPane> stackPanes = new ArrayList<>();

    private float paneSize_X = 286;
    private float paneSize_Y = 286;
    private float PaneSpace_X = 30;
    private float paneSPace_Y = 30;
    private int panesPerRow = 0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            int column = 0;
            int row = 1;
            for (ProductCategory pc : ProductCategory.values()) {
                column++;
                StackPane stackPane = new StackPane();

                //pos
                stackPane.setLayoutX((PaneSpace_X * column) + (paneSize_X  * column-1));
                stackPane.setLayoutY((paneSPace_Y * row) + (paneSize_Y * row-1));

                //size
                stackPane.setMinSize(paneSize_X, paneSize_Y);
                stackPane.setMaxSize(paneSize_X, paneSize_Y);

                ImageView imgView = new ImageView(pc.getImage());
                
                imgView.setFitWidth(paneSize_X);
                imgView.setFitHeight(paneSize_Y);

                Text text = new Text(pc.asFormatedString());
                
                text.setStyle("-fx-fill-color: #333333");
                
                stackPane.getChildren().add(imgView);
                stackPane.getChildren().add(text);
                
                stackPane.setAlignment(text, Pos.BOTTOM_CENTER);
                
                pane.getChildren().add(stackPane);
                if(column % panesPerRow == 0) {
                    column = 0;
                    row ++;
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
