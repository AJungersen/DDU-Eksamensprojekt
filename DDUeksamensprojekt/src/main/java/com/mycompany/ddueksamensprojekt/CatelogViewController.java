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
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javax.swing.text.Position;
import repository.StoreDatabaseMethods;

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
    private float namepos_X = 16;
    private float namePos_Y = 158;
    private float priceTextSize = 12;
    private float pricePos_X = 47;
    private float pricePos_Y = 172;
    private float textSpace = 5;
    private float paneSize_X = 137;
    private float paneSize_Y = pricePos_Y + priceTextSize;
    private ArrayList<Product> products = new ArrayList<>();

    @FXML
    private Text textFieldCategory;
    @FXML
    private TextField userInput;
    @FXML
    private AnchorPane anchorPaneProducts;

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
            /*anchorPaneOnScrollPane.setPrefHeight(anchorPaneOnScrollPane.getPrefHeight()
                    - anchorPaneCategories.getPrefHeight() + newHeight);*/

            anchorPaneProducts.setPrefHeight(newHeight);

            //insert categories
            int column = 0;
            int row = 1;

            for (Product p : products) {
                column++;
                StackPane stackPane = new StackPane();

                //pane pos
                stackPane.setLayoutX((paneSpace_X * column) + (paneSize_X * (column - 1)));
                stackPane.setLayoutY((paneSpace_Y * row) + (paneSize_Y * (row - 1)));

                //pane size
                stackPane.setMinSize(paneSize_X, paneSize_Y);
                stackPane.setMaxSize(paneSize_X, paneSize_Y);

                //product image
                ImageView imgView = new ImageView(p.getImage());

                imgView.setFitWidth(imgSize_X);
                imgView.setFitHeight(imgSize_Y);

                //name text
                Text nameText = new Text(p.getName());
                nameText.setFont(Font.font("italic", nameTextSize));

                //nameText.setStyle("-fx-fill-color: #333333");
                //price text
                Text priceText = new Text(pricePos_X, pricePos_Y, Float.toString(p.getPrice()));
                priceText.setId("priceText");
                priceText.setFont(Font.font("italic", priceTextSize));

                //set mouse clicked on image view to switch to category
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

                stackPane.getChildren().add(imgView);
                stackPane.getChildren().add(nameText);
                stackPane.getChildren().add(priceText);

                for (Node sp : stackPane.getChildren()) {
                    if (sp instanceof ImageView) {
                        sp.setOnMouseClicked(clicked);
                        sp.setOnMouseEntered(entered);
                        sp.setOnMouseExited(exited);
                    }
                }

                //stackPane.setAlignment(nameText, Pos.BASELINE_LEFT);
                //stackPane.setAlignment(priceText, Pos.BASELINE_LEFT);

                for (Node n : stackPane.getChildren()) {
                    System.out.println(n.getId());
                    if (n.getId() == "nameText") {
                        n.setLayoutY(namePos_Y);

                    } else if(n.getId() == "priceText") {
                        n.setLayoutY(pricePos_Y);
                    }
                }

                anchorPaneProducts.getChildren().add(stackPane);
                if (column % panesPerRow == 0) {
                    column = 0;
                    row++;
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
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
    private void search() throws Exception{
        ArrayList<Product> allProducts = sdm.getProductsInSpeceficCategory(App.getCurrentCategoryDisplaying());
        ArrayList<Product> products = new ArrayList();
        String search = userInput.getText();
        for(Product P: allProducts){
            if(Pattern.matches(".*" + search + "+.*",P.getName()) == true){
                products.add(P);
            }
        }
        //Clara noget der smider alle produkter P ind i fxml
    }
}
