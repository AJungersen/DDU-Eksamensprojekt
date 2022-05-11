/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.ddueksamensprojekt;

import Classes.ProductCategory;
import static Classes.ProductCategory.SLIK_OG_SNACKS;
import com.mycompany.ddueksamensprojekt.Algorithms.ProductRecommendations;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.util.Duration;
import repository.Tools;


/**
 * FXML Controller class
 *
 * @author Clara Maj
 */
public class ProductInformationController implements Initializable {

    Product product = new Product();

    private float boarderThikness = 2.5f;
    private float textSpace = 5;

    private float paneSpace_X = 10;
    private float paneSpace_Y = 10;
    private float paneSize_X = 137;
    private float panesPerRow;

    private float imgSize_X = paneSize_X - 2 * boarderThikness;
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

    @FXML
    private TextField textFieldPrice;
    @FXML
    private TextField textFieldStock;
    @FXML
    private ImageView imageViewProduct;
    @FXML
    private ImageView favImg;
    @FXML
    private TextField textFieldNumberOfProduct;
    @FXML
    private VBox vbox;
    @FXML
    private VBox vbox1;
    private Parent fxml;
    private Parent fxml1;
    @FXML
    private Text returnButton;
    @FXML
    private Text textProductName;
    @FXML
    private AnchorPane anchorPaneRelatedProducts;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        product = App.getCurrentProduct();

        textProductName.setText(product.getName());
        textFieldPrice.setText(Float.toString(product.getPrice()));
        textFieldStock.setText(((product.getStock() > 0) ? "på lager" : "ikke på lager"));
        imageViewProduct.setImage(product.getImage());
        //stock.setText((App.currentProduct.getStock()>0 ? "på lager":"ikke på lager"));

        try {
            fxml = FXMLLoader.load(getClass().getResource("confirmation.fxml"));
            fxml1 = FXMLLoader.load(getClass().getResource("confirmationFavorits.fxml"));
            //vbox.getChildren().removeAll();
            vbox.getChildren().setAll(fxml);
            vbox1.getChildren().setAll(fxml1);
        } catch (Exception ex) {
            System.out.println("Error");
            //Logger.getLogger(ProductInformationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        ProductRecommendations pr = new ProductRecommendations();
        ArrayList<Product> relatedProducts = new ArrayList<>();
        try {
            relatedProducts = pr.getBestProduct(product, 10);
        } catch (Exception ex) {
            Logger.getLogger(ProductInformationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        //upload hele listen (den har de 5 bedste)

        anchorPaneRelatedProducts.getChildren().clear();

        //calc panes per row
        panesPerRow = (int) Math.floor((anchorPaneRelatedProducts.getPrefWidth() - paneSpace_X) / (paneSize_X + paneSpace_X));

        //update pane space x
        paneSpace_X = (float) (anchorPaneRelatedProducts.getPrefWidth()
                - (paneSize_X * panesPerRow))
                / (2 + panesPerRow - 1);

        //update anchor pane height
        float newHeight = (float) ((paneSize_Y + paneSpace_Y)
                * Math.ceil(ProductCategory.values().length / panesPerRow) + paneSpace_Y);

        anchorPaneRelatedProducts.setPrefHeight(newHeight);

        //insert products
        int column = 0;
        int row = 1;

        for (Product p : relatedProducts) {
            column++;
            Pane pane = new Pane();

            //Pane pane = new StackPane();
            //pane pos
            pane.setLayoutX((paneSpace_X * column) + (paneSize_X * (column - 1)));
            pane.setLayoutY((paneSpace_Y * row) + (paneSize_Y * (row - 1)));

            //pane size
            pane.setMinSize(paneSize_X, paneSize_Y);
            pane.setMaxSize(paneSize_X, paneSize_Y);

            pane.setStyle("-fx-border-color: #666666;"
                    + "-fx-border-width: " + boarderThikness + ";"
                    + "-fx-background-color: #ffffff");

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
            EventHandler<javafx.scene.input.MouseEvent> clicked = new EventHandler<javafx.scene.input.MouseEvent>() {
                @Override
                public void handle(javafx.scene.input.MouseEvent event) {
                    App.setCurrentProduct(p);
                    try {
                        App.setLastSceneFxml("catelogView");
                        App.setRoot("productInformation");
                    } catch (Exception e) {
                        System.out.println("Error in " + e.getMessage());;
                    }
                }
            };

            EventHandler<javafx.scene.input.MouseEvent> entered = new EventHandler<javafx.scene.input.MouseEvent>() {
                @Override
                public void handle(javafx.scene.input.MouseEvent event) {
                    App.getStage().getScene().setCursor(Cursor.HAND);
                }
            };

            EventHandler<javafx.scene.input.MouseEvent> exited = new EventHandler<javafx.scene.input.MouseEvent>() {
                @Override
                public void handle(javafx.scene.input.MouseEvent event) {
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

            anchorPaneRelatedProducts.getChildren().add(pane);
            if (column % panesPerRow == 0) {
                column = 0;
                row++;
            }

        }
    }

    @FXML
    private void openProfile() throws IOException {
        App.setRoot("profile");
        App.setLastSceneFxml("productInformation");
    }

    @FXML
    private void openMain() throws IOException {
        App.setRoot("main");
    }

    @FXML
    private void openCart() throws IOException {
        App.setRoot("cart");
        App.setLastSceneFxml("productInformation");
    }

    @FXML
    private void addToCart() throws IOException {
        try {
            for (int i = 0; i < Integer.parseInt(textFieldNumberOfProduct.getText()); i++) {
                App.getCurrentCart().getProductsAsList().add(product);
            }

            openConfirmation();
        } catch (Exception e) {

        }

    }

    private void openConfirmation() {
        //returnButton.setVisible(true);

        // returnvbox.setVisible(false);
        TranslateTransition t = new TranslateTransition(Duration.seconds(1), vbox);
        t.setToY(vbox.getLayoutX() * 0.8);
        t.play();
        t.setOnFinished((e) -> {
        });
        TranslateTransition s = new TranslateTransition(Duration.seconds(1), returnButton);
        s.setToY(38);
        s.play();
        s.setOnFinished((e) -> {
        });
        //returnvbox.setVisible(false);
        final Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(5000)));
        timeline.play();
        timeline.setOnFinished((e)->{
            closeConfirmation();
        });
    }

    @FXML
    public void closeConfirmation() {
        TranslateTransition t = new TranslateTransition(Duration.seconds(1), vbox);
        t.setToY(0);
        t.play();
        t.setOnFinished((e) -> {
        });
        TranslateTransition t1 = new TranslateTransition(Duration.seconds(1), vbox1);
        t1.setToY(0);
        t1.play();
        t1.setOnFinished((e) -> {
        });
        TranslateTransition s = new TranslateTransition(Duration.seconds(1), returnButton);
        s.setToY(0);
        s.play();
        s.setOnFinished((e) -> {
        });

    }

    @FXML
    public void openConfirmationFavorites() {
        //returnButton.setVisible(true);

        // returnvbox.setVisible(false);
        favImg.setStyle("-fx-effect: coloradjust(brightness, 0.0);");
        
        TranslateTransition t = new TranslateTransition(Duration.seconds(1), vbox1);
        t.setToY(vbox.getLayoutX() * 0.8);
        t.play();
        t.setOnFinished((e) -> {
        });
        TranslateTransition s = new TranslateTransition(Duration.seconds(1), returnButton);
        s.setToY(38);
        s.play();
        s.setOnFinished((e) -> {
        });
        //returnvbox.setVisible(false);
        final Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(5000)));
        timeline.play();
        timeline.setOnFinished((e)->{
            closeConfirmation();
        });
    }

    @FXML
    private void goBack(ActionEvent event) throws Exception {
        App.setRoot("catelogView");
    }
}
