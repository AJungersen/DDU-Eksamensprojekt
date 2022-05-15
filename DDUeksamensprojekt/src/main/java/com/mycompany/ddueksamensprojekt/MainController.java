/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ddueksamensprojekt;

import Classes.ProductCategory;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;
import repository.StoreDatabaseMethods;

/**
 *
 * @author chris
 */
public class MainController implements Initializable {

    StoreDatabaseMethods sdm = new StoreDatabaseMethods();
    ArrayList<StackPane> stackPanes = new ArrayList<>();

    private float boarderWith = 4f;
    private float paneSize_X = 286;
    private float paneSize_Y = 286;
    private float PaneSpace_X = 30;
    private float paneSPace_Y = 30;
    private float panesPerRow = 0;
    @FXML
    private Text textWelcomeBackUser;
    @FXML
    private TableColumn<TableViewDisplayPurchase, ImageView> tableColumnImage;
    @FXML
    private TableColumn<TableViewDisplayPurchase, String> tableColumnName;
    @FXML
    private TableColumn<TableViewDisplayPurchase, Integer> tableColumnPrice;
    @FXML
    private TableColumn<TableViewDisplayPurchase, Integer> tableColumnAmount;
    @FXML
    private TableView<TableViewDisplayPurchase> tableViewLastPurchas;
    @FXML
    private AnchorPane anchorPaneCategories;
    @FXML
    private AnchorPane anchorPaneOnScrollPane;
    @FXML
    private VBox vbox;
    @FXML
    private Text returnButton;
    private Parent fxml;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            fxml = FXMLLoader.load(getClass().getResource("guide.fxml"));
            vbox.getChildren().setAll(fxml);

        } catch (Exception ex) {
            System.out.println("Error");
        }
        try {
            textWelcomeBackUser.setText("Velkommen tilbage " + App.getLoggedInUser().getName());

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

                imgView.setFitWidth(paneSize_X - boarderWith * 2);
                imgView.setFitHeight(paneSize_Y - boarderWith * 2);

                stackPane.setStyle("-fx-border-color: #333333;" + "-fx-border-width: " + boarderWith + ";");

                //category text
                Text text = new Text(pc.asFormatedString());

                text.setStyle("-fx-fill-color: #333333;" + "-fx-font-size:30px;"
                        + "-fx-font-family: Segoe UI Semibold;"
                        + "-fx-effect: dropshadow(one-pass-box, #5C5C5C, 9.66, 0.62, 1,1);");

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

            //test
            /*Product p1 = new Product(0, "test1", null, 1, 4, ProductCategory.FRUGT_OG_GRØNT);
            Product p2 = new Product(0, "test2", null, 5, 4, ProductCategory.KØD_OG_FISK);

            HashMap<Product, Integer> hp = new HashMap<>();

            hp.put(p1, 1);
            hp.put(p2, 2);
             */
            ArrayList<TableViewDisplayPurchase> tableViewDispalyData = new ArrayList<>();

            HashMap<Product, Integer> hm = sdm.getLatestPurchase(App.getLoggedInUser().getUser_ID()).getPurchasedProducts();

            for (Product p : hm.keySet()) {
                System.out.println(p.getImage());
                tableViewDispalyData.add(new TableViewDisplayPurchase(hm.get(p), p));
            }

            tableColumnImage.setCellValueFactory(new PropertyValueFactory<>("displayImage"));
            tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
            tableColumnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
            tableColumnAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));

            tableViewLastPurchas.getItems().setAll(tableViewDispalyData);

        } catch (Exception e) {
            System.out.println("\n error in main initiliza: " + e.getMessage() + "\n");
        }

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
        timeline.setOnFinished((e) -> {
            closeConfirmation();
        });
        System.out.println("test 10");

    }

    @FXML
    public void closeConfirmation() {
        TranslateTransition t = new TranslateTransition(Duration.seconds(1), vbox);
        t.setToY(0);
        t.play();
        t.setOnFinished((e) -> {
        });
        TranslateTransition s = new TranslateTransition(Duration.seconds(1), returnButton);
        s.setToY(0);
        s.play();
        s.setOnFinished((e) -> {
        });

    }

    @FXML
    void openProfile() throws Exception {
        App.setRoot("profile");
        App.setLastSceneFxml("main");
    }

    @FXML
    void openCart() throws Exception {
        App.setRoot("cart");
        App.setLastSceneFxml("main");
    }

    @FXML
    private void goToSelectedProduct(MouseEvent event) throws Exception {

        if (tableViewLastPurchas.getFocusModel().getFocusedCell().getColumn() <= 1) {
            //send to product
            App.setCurrentProduct(tableViewLastPurchas.getSelectionModel().getSelectedItem());
            App.setRoot("productInformation");
        }
    }

    @FXML
    void openAdmin() throws Exception {
        App.setRoot("mainAdmin");
    }
}
