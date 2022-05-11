/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.ddueksamensprojekt;

import Classes.ProductCategory;
import java.awt.Color;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import repository.StoreDatabaseMethods;
import repository.Tools;

/**
 * FXML Controller class
 *
 * @author Clara Maj
 */
public class CatelogViewController implements Initializable {

    private StoreDatabaseMethods sdm = new StoreDatabaseMethods();
    private float boarderThikness = 2.5f;
    private float textSpace = 5;
    
    private float paneSpace_X = 10;
    private float paneSpace_Y = 10;
    private float paneSize_X = 137;
    private float panesPerRow;

    private float imgSize_X = paneSize_X - 2*boarderThikness;
    private float imgSize_Y = 143 - boarderThikness;

    private float nameTextSize = 14;
    private float namepos_X = 0;
    private float namePos_Y = 158;

    private float priceTextSize = 12;
    private float pricePos_X = 0;
    private float pricePos_Y = namePos_Y + nameTextSize;

    private float lagerstatusPos_X = 0;
    private float lagerstatusPos_Y = pricePos_Y + priceTextSize;
    private float lagerstatusSize = 12;
    private float lagerstatusCirkel_X = 0;
    private float lagerstatusCirkel_Y = lagerstatusPos_Y;
    private float lagerStatusCirkelSize = 7;

    private float paneSize_Y = lagerstatusPos_Y + lagerstatusSize;
    private Font nameFont = Font.font("italic", nameTextSize);
    private Font priceFont = Font.font("italic", priceTextSize);

    private int fewProductsRemaning = 10;
    private ArrayList<Product> products = new ArrayList<>();

    @FXML
    private Text textFieldCategory;
    @FXML
    private AnchorPane anchorPaneProducts;
    @FXML
    private TextField textFieldSearchBar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            textFieldCategory.setText(App.getCurrentCategoryDisplaying().asFormatedString());

            //load products
            products = sdm.getProductsInSpeceficCategory(App.getCurrentCategoryDisplaying());

            //calc panes per row
            panesPerRow = (int) Math.floor((anchorPaneProducts.getPrefWidth() - paneSpace_X) / (paneSize_X + paneSpace_X));

            //update pane space x
            paneSpace_X = (float) (anchorPaneProducts.getPrefWidth()
                    - (paneSize_X * panesPerRow))
                    / (2 + panesPerRow - 1);

            //update anchor pane height
            float newHeight = (float) ((paneSize_Y + paneSpace_Y)
                    * Math.ceil(ProductCategory.values().length / panesPerRow) + paneSpace_Y);

            anchorPaneProducts.setPrefHeight(newHeight);
            loadProducts();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private void loadProducts() {
        anchorPaneProducts.getChildren().clear();

        //insert products
        int column = 0;
        int row = 1;

        for (Product p : products) {
            //if (textFieldSearchBar.getText().isBlank() || (p.getName().toLowerCase().indexOf(textFieldSearchBar.getText().toLowerCase(), 0) == 0)) {
            if (textFieldSearchBar.getText().isBlank()
                    || (Pattern.matches(".*" + textFieldSearchBar.getText().toLowerCase() + "+.*", p.getName().toLowerCase()))) {

                column++;
                Pane pane = new Pane();

                //Pane pane = new StackPane();
                //pane pos
                pane.setLayoutX((paneSpace_X * column) + (paneSize_X * (column - 1)));
                pane.setLayoutY((paneSpace_Y * row) + (paneSize_Y * (row - 1)));

                //pane size
                pane.setMinSize(paneSize_X, paneSize_Y);
                pane.setMaxSize(paneSize_X, paneSize_Y);

                pane.setStyle("-fx-border-color: #666666;" +
                        "-fx-border-width: " + boarderThikness + ";" +
                        "-fx-background-color: #ffffff");

                //product image
                ImageView imgView = new ImageView(p.getImage());

                imgView.setFitWidth(imgSize_X);
                imgView.setFitHeight(imgSize_Y);
                
                imgView.setLayoutX(boarderThikness);
                imgView.setLayoutY(boarderThikness);

                //name text
                p.setName(Tools.capitalizeFirstLetter(p.getName()));
                Text nameText = new Text(namepos_X, namePos_Y, p.getName());
                nameText.setFont(nameFont);
                nameText.setLayoutX(paneSize_X / 2 - nameText.getBoundsInLocal().getWidth() / 2);
                //nameText.setTextOrigin(VPos.CENTER);

                //price text
                Text priceText = new Text(pricePos_X, pricePos_Y, Float.toString(p.getPrice()) + " DKK");
                priceText.setFont(priceFont);
                priceText.setLayoutX(paneSize_X / 2 - priceText.getBoundsInLocal().getWidth() / 2);
                //priceText.setTextOrigin(VPos.CENTER);

                //Lagerstatus text and circel
                Text lagerstatusText = new Text(lagerstatusPos_X, lagerstatusPos_Y, "lagerstatus");
                lagerstatusText.setFont(priceFont);
                lagerstatusText.setLayoutX(paneSize_X / 2 - lagerstatusText.getBoundsInLocal().getWidth() / 2 - lagerStatusCirkelSize - 10);
                lagerstatusText.setTextOrigin(VPos.CENTER);

                Circle lagerstatusCircle = new Circle(lagerstatusCirkel_X, lagerstatusCirkel_Y, lagerStatusCirkelSize);
                if (p.getStock() != 0) {
                    if (p.getStock() <= fewProductsRemaning) {
                        //few
                        lagerstatusCircle.setFill(Paint.valueOf("#F7DC6F"));
                    } else {
                        //in stock
                        lagerstatusCircle.setFill(Paint.valueOf("#82E0AA"));
                    }
                } else {
                    //empty
                    lagerstatusCircle.setFill(Paint.valueOf("#F1948A"));
                    //lagerstatusCircle.setFill(Paint.valueOf("#82E0AA"));
                    lagerstatusCircle.setFill(Paint.valueOf("#F7DC6F"));
                }
                lagerstatusCircle.setCenterX(lagerstatusText.getLayoutX() + lagerstatusText.getBoundsInLocal().getWidth() + lagerStatusCirkelSize + 10);

                //set mouse clicked on, to switch to product view
                EventHandler<MouseEvent> clicked = new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        App.setCurrentProduct(p);
                        try {
                            App.setLastSceneFxml("catelogView");
                            App.setRoot("productInformation");
                        } catch (Exception e) {
                            System.out.println("Error in " + e.getMessage());;
                        }
                    }
                };

                EventHandler<MouseEvent> entered = new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        App.getStage().getScene().setCursor(Cursor.HAND);
                    }
                };

                EventHandler<MouseEvent> exited = new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        App.getStage().getScene().setCursor(Cursor.DEFAULT);
                    }
                };

                pane.getChildren().add(imgView);
                pane.getChildren().add(nameText);
                pane.getChildren().add(priceText);
                pane.getChildren().add(lagerstatusText);
                pane.getChildren().add(lagerstatusCircle);

                pane.setOnMouseClicked(clicked);
                pane.setOnMouseEntered(entered);
                pane.setOnMouseExited(exited);

                for (Node sp : pane.getChildren()) {
                    sp.setOnMouseClicked(clicked);
                    sp.setOnMouseEntered(entered);
                    sp.setOnMouseExited(exited);
                }

                anchorPaneProducts.getChildren().add(pane);
                if (column % panesPerRow == 0) {
                    column = 0;
                    row++;
                }
            }
        }
    }

    @FXML
    private void openProfile() throws IOException {
        App.setRoot("profile");
        App.setLastSceneFxml("catelogView");
    }

    @FXML
    private void openMain() throws IOException {
        App.setRoot("main");
    }

    @FXML
    private void openCart() throws IOException {
        App.setRoot("cart");
        App.setLastSceneFxml("catelogView");
    }

    @FXML
    private void updateProductsToSearch(KeyEvent event) {
        loadProducts();
    }

    @FXML
    private void deleteSearch(ActionEvent event) {
        textFieldSearchBar.clear();
        loadProducts();
    }
}
