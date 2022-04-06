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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
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
    private int panesPerRow = 0;
    @FXML
    private Text textWelcomeBackUser;
    @FXML
    private TableColumn<TableViewDispaly, Image> tableColumnImage;
    @FXML
    private TableColumn<TableViewDispaly, String> tableColumnName;
    @FXML
    private TableColumn<TableViewDispaly, Integer> tableColumnPrice;
    @FXML
    private TableColumn<TableViewDispaly, Integer> tableColumnAmount;
    @FXML
    private AnchorPane achorPaneCategories;
    @FXML
    private TableView<TableViewDispaly> tableViewLastPurchas;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            /*
            //insert categories
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
                
                achorPaneCategories.getChildren().add(stackPane);
                if(column % panesPerRow == 0) {
                    column = 0;
                    row ++;
                }
            }*/

            //insert last purchase
            Product p1 = new Product(0, "test1", null, 1, 4, ProductCategory.DRIKKEVARER);
            Product p2 = new Product(0, "test2", null, 5, 4, ProductCategory.DRIKKEVARER);

            HashMap<Product, Integer> hp = new HashMap<>();

            hp.put(p1, 1);
            hp.put(p2, 2);

            ArrayList<TableViewDispaly> tableViewDispalyData = new ArrayList<>();

            HashMap<Product, Integer> hm = sdm.getLatestPurchase(App.getLoggedInUser().getUser_ID()).getPurchasedProducts();

            for (Product p : hp.keySet()) {
                tableViewDispalyData.add(new TableViewDispaly(hp.get(p), p));
            }

            tableColumnImage.setCellValueFactory(new PropertyValueFactory<TableViewDispaly, Image>("image"));
            tableColumnName.setCellValueFactory(new PropertyValueFactory<TableViewDispaly, String>("name"));
            tableColumnPrice.setCellValueFactory(new PropertyValueFactory<TableViewDispaly, Integer>("price"));
            tableColumnAmount.setCellValueFactory(new PropertyValueFactory<TableViewDispaly, Integer>("amount"));

            tableViewLastPurchas.getItems().setAll(tableViewDispalyData);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void openProfile() throws Exception {
        // App.setRoot(profile);
    }

    @FXML
    void openCart() throws Exception {
        // App.setRoot(cart);
    }

    @FXML
    private void goToSelectedProduct(MouseEvent event) {

        if (tableViewLastPurchas.getFocusModel().getFocusedCell().getColumn() == 1) {
            //send to 
            System.out.println(tableViewLastPurchas.getSelectionModel().getSelectedItem().getAmount());
        }
    }
}
