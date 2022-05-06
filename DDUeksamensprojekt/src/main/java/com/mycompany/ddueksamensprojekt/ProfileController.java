/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.ddueksamensprojekt;

import Classes.ProductCategory;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import repository.StoreDatabaseMethods;

/**
 * FXML Controller class
 *
 * @author Clara Maj
 */
public class ProfileController implements Initializable {
    StoreDatabaseMethods sdm = new StoreDatabaseMethods();
    
    @FXML
    private TableColumn<TableViewDisplayPurchase, Image> tableColumnImage;
    @FXML
    private TableColumn<TableViewDisplayPurchase, String> tableColumnName;
    @FXML
    private TableColumn<TableViewDisplayPurchase, Integer> tableColumnPrice;
    @FXML
    private TableColumn<TableViewDisplayPurchase, Integer> tableColumnAmount;
    @FXML
    private TableView<TableViewDisplayPurchase> tableViewLastPurchas;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Product p1 = new Product(0, "test1", null, 1, 4, ProductCategory.DRIKKEVARER);
            Product p2 = new Product(0, "test2", null, 5, 4, ProductCategory.DRIKKEVARER);

            HashMap<Product, Integer> hp = new HashMap<>();

            hp.put(p1, 1);
            hp.put(p2, 2);

            ArrayList<TableViewDisplayPurchase> tableViewDispalyData = new ArrayList<>();

            HashMap<Product, Integer> hm = sdm.getLatestPurchase(App.getLoggedInUser().getUser_ID()).getPurchasedProducts();

            for (Product p : hp.keySet()) {
                tableViewDispalyData.add(new TableViewDisplayPurchase(hp.get(p), p));
            }

            tableColumnImage.setCellValueFactory(new PropertyValueFactory<TableViewDisplayPurchase, Image>("image"));
            tableColumnName.setCellValueFactory(new PropertyValueFactory<TableViewDisplayPurchase, String>("name"));
            tableColumnPrice.setCellValueFactory(new PropertyValueFactory<TableViewDisplayPurchase, Integer>("price"));
            tableColumnAmount.setCellValueFactory(new PropertyValueFactory<TableViewDisplayPurchase, Integer>("amount"));

            tableViewLastPurchas.getItems().setAll(tableViewDispalyData);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    @FXML
    private void goToSelectedProduct(MouseEvent event) {

        if (tableViewLastPurchas.getFocusModel().getFocusedCell().getColumn() == 1) {
            //send to product
            System.out.println(tableViewLastPurchas.getSelectionModel().getSelectedItem().getAmount());
        }
    }
    
    @FXML
    private void openSettings() throws IOException {
        App.setRoot("settings");
}
    @FXML
    private void openWallet() throws IOException {
        App.setRoot("wallet");
    }
    @FXML
    private void openLogin() throws IOException {
        App.setRoot("loginUser");
    }
    @FXML
    private void openCart() throws IOException {
        App.setRoot("cart");
    }
    @FXML
    private void openMain() throws IOException {
        App.setRoot("main");
    }
}
