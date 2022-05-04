/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ddueksamensprojekt;

import Classes.ProductCategory;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javax.imageio.plugins.tiff.TIFFDirectory;
import repository.StoreDatabaseMethods;
import repository.Tools;

/**
 *
 * @author chris
 */
public class MainController implements Initializable {

    StoreDatabaseMethods sdm = new StoreDatabaseMethods();
    ArrayList<StackPane> stackPanes = new ArrayList<>();

    private float paneSize_X = 286;
    private float paneSize_Y = 286;
    private float PaneSpace_X = 30;
    private float paneSPace_Y = 30;
    private float panesPerRow = 0;
    @FXML
    private Text textWelcomeBackUser;
    @FXML
    private TableColumn<TableViewDispalyPurchase, ImageView> tableColumnImage;
    @FXML
    private TableColumn<TableViewDispalyPurchase, String> tableColumnName;
    @FXML
    private TableColumn<TableViewDispalyPurchase, Integer> tableColumnPrice;
    @FXML
    private TableColumn<TableViewDispalyPurchase, Integer> tableColumnAmount;
    @FXML
    private TableView<TableViewDispalyPurchase> tableViewLastPurchas;
    @FXML
    private AnchorPane anchorPaneCategories;
    @FXML
    private AnchorPane anchorPaneOnScrollPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            for (ProductCategory p : ProductCategory.values()) {
                System.out.println("\n" + p.asFormatedString());
            }

            //calc panes per row
            panesPerRow = (int) Math.floor((anchorPaneCategories.getPrefWidth() - PaneSpace_X) / (paneSize_X + PaneSpace_X));

            //update pane space x
            PaneSpace_X = (float) (anchorPaneCategories.getPrefWidth()
                    - (paneSize_X * panesPerRow))
                    / (2 + panesPerRow - 1);

            //update anchor pane height
            float newHeight = (float) ((paneSize_Y + paneSPace_Y) * Math.ceil(ProductCategory.values().length / panesPerRow) + paneSPace_Y);
            anchorPaneOnScrollPane.setPrefHeight(anchorPaneOnScrollPane.getPrefHeight()
                    - anchorPaneCategories.getPrefHeight() + newHeight);

            anchorPaneCategories.setPrefHeight(newHeight);

            //insert categories
            int column = 0;
            int row = 1;
            for (ProductCategory pc : ProductCategory.values()) {
                column++;
                StackPane stackPane = new StackPane();
                //pos
                stackPane.setLayoutX((PaneSpace_X * column) + (paneSize_X * (column - 1)));
                stackPane.setLayoutY((paneSPace_Y * row) + (paneSize_Y * (row - 1)));

                //size
                stackPane.setMinSize(paneSize_X, paneSize_Y);
                stackPane.setMaxSize(paneSize_X, paneSize_Y);

                //category image
                ImageView imgView = new ImageView(pc.getImage());

                imgView.setFitWidth(paneSize_X);
                imgView.setFitHeight(paneSize_Y);

                //category text
                Text text = new Text(pc.asFormatedString());

                text.setStyle("-fx-fill-color: #333333");

                //set mouse clicked on image view to switch to category
                EventHandler<MouseEvent> clicked = new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        App.setCurrentCategoryDisplaying(pc);
                        try {
                            App.setRoot("CatelogView");
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
                stackPane.getChildren().add(text);
                
                for (Node sp : stackPane.getChildren()) {
                    sp.setOnMouseClicked(clicked);
                    sp.setOnMouseEntered(entered);
                    sp.setOnMouseExited(exited);
                }

                stackPane.setAlignment(text, Pos.BOTTOM_CENTER);

                anchorPaneCategories.getChildren().add(stackPane);
                if (column % panesPerRow == 0) {
                    column = 0;
                    row++;
                }
            }

            //insert last purchase
            Product p1 = new Product(0, "test1", null, 1, 4, ProductCategory.FRUGT_OG_GRØNT);
            Product p2 = new Product(0, "test2", null, 5, 4, ProductCategory.KØD_OG_FISK);

            HashMap<Product, Integer> hp = new HashMap<>();

            hp.put(p1, 1);
            hp.put(p2, 2);

            ArrayList<TableViewDispalyPurchase> tableViewDispalyData = new ArrayList<>();

            HashMap<Product, Integer> hm = sdm.getLatestPurchase(App.getLoggedInUser().getUser_ID()).getPurchasedProducts();

            for (Product p : hp.keySet()) {
                tableViewDispalyData.add(new TableViewDispalyPurchase(hp.get(p), p));
            }

            tableColumnImage.setCellValueFactory(new PropertyValueFactory<>("displayImage"));
            tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
            tableColumnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
            tableColumnAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));

            tableViewLastPurchas.getItems().setAll(tableViewDispalyData);

        } catch (Exception e) {
            System.out.println("\n error in main initiliza: " + e.getMessage() + "\n");
        }
    }

    @FXML
    void openProfile() throws Exception {
        App.setRoot("profile");
    }

    @FXML
    void openCart() throws Exception {
        App.setRoot("cart");
    }

    @FXML
    private void goToSelectedProduct(MouseEvent event) {

        if (tableViewLastPurchas.getFocusModel().getFocusedCell().getColumn() == 1) {
            //send to 
            System.out.println(tableViewLastPurchas.getSelectionModel().getSelectedItem().getAmount());
        }
    }
}
