/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.ddueksamensprojekt;

import Classes.ProductCategory;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
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
    private float paneSpace_X = 10;
    private float paneSpace_Y = 10;
    private float panesPerRow;
    private float imgSize_X = 137;
    private float imgSize_Y = 143;
    private float nameTextSize = 14;
    private float namepos_X = 0;
    private float namePos_Y = 158;
    private float priceTextSize = 12;
    private float pricePos_X = 0;
    private float pricePos_Y = 172;
    private float textSpace = 5;
    private float paneSize_X = 137;
    private float paneSize_Y = pricePos_Y + priceTextSize;
    private Font nameFont = Font.font("italic", nameTextSize);
    private Font priceFont = Font.font("italic", priceTextSize);
    private ArrayList<Product> products = new ArrayList<>();

    @FXML
    private Text textFieldCategory;
    @FXML
    private TextField userInput;
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

                //pane pos
                pane.setLayoutX((paneSpace_X * column) + (paneSize_X * (column - 1)));
                pane.setLayoutY((paneSpace_Y * row) + (paneSize_Y * (row - 1)));

                //pane size
                pane.setMinSize(paneSize_X, paneSize_Y);
                pane.setMaxSize(paneSize_X, paneSize_Y);

                pane.setStyle("-fx-border-color: #666666");

                //product image
                ImageView imgView = new ImageView(p.getImage());

                imgView.setFitWidth(imgSize_X);
                imgView.setFitHeight(imgSize_Y);

                //name text
                p.setName(Tools.capitalizeFirstLetter(p.getName()));
                Text nameText = new Text(namepos_X, namePos_Y, p.getName());
                nameText.setFont(nameFont);
                nameText.setTextAlignment(TextAlignment.LEFT);

                //price text
                Text priceText = new Text(pricePos_X, pricePos_Y, Float.toString(p.getPrice()));
                priceText.setFont(priceFont);
                priceText.setTextAlignment(TextAlignment.LEFT);

                //set mouse clicked on image view to switch to product view
                EventHandler<MouseEvent> clicked = new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        App.setCurrentProduct(p);
                        try {
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

                for (Node sp : pane.getChildren()) {
                    if (sp instanceof ImageView) {
                        sp.setOnMouseClicked(clicked);
                        sp.setOnMouseEntered(entered);
                        sp.setOnMouseExited(exited);
                    }
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
    }

    @FXML
    private void openMain() throws IOException {
        App.setRoot("main");
    }

    @FXML
    private void openCart() throws IOException {
        App.setRoot("cart");
    }

    @FXML
    private void updateProductsToSearch(KeyEvent event) {
        loadProducts();
    }
}
