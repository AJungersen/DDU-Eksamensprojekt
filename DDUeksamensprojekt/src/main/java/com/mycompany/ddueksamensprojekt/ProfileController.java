/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.ddueksamensprojekt;

import Classes.Cart;
import Classes.ProductCategory;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import repository.StoreDatabaseMethods;
import repository.UserDatabaseMethods;

/**
 * FXML Controller class
 *
 * @author Clara Maj
 */
public class ProfileController implements Initializable {

    private StoreDatabaseMethods sdm = new StoreDatabaseMethods();
    private UserDatabaseMethods udm = new UserDatabaseMethods();

    @FXML
    private TableColumn<AdminProductViewController, ImageView> tableColumnImage;
    @FXML
    private TableColumn<AdminProductViewController, String> tableColumnName;
    @FXML
    private TableColumn<AdminProductViewController, Integer> tableColumnPrice;
    @FXML
    private TableColumn<AdminProductViewController, Integer> tableColumnAmount;
    @FXML
    private TableView<AdminProductViewController> tableViewLastPurchas;
    @FXML
    private TextField textFieldNumberOfUsersPurchases;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            //get purchases
            Product p1 = new Product(0, "test1", null, 1, 4, ProductCategory.DRIKKEVARER);
            Product p2 = new Product(0, "test2", null, 5, 4, ProductCategory.DRIKKEVARER);

            HashMap<Product, Integer> hp = new HashMap<>();

            hp.put(p1, 1);
            hp.put(p2, 2);

            ArrayList<AdminProductViewController> tableViewDispalyData = new ArrayList<>();

            HashMap<Product, Integer> hm = sdm.getLatestPurchase(App.getLoggedInUser().getUser_ID()).getPurchasedProducts();
            
            for (Product p : hm.keySet()) {
                System.out.println(p.getImage());
                tableViewDispalyData.add(new AdminProductViewController(hm.get(p), p));
            }

            tableColumnImage.setCellValueFactory(new PropertyValueFactory<>("displayImage"));
            tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
            tableColumnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
            tableColumnAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));

            tableViewLastPurchas.getItems().setAll(tableViewDispalyData);
            
            //get number of purchases
            textFieldNumberOfUsersPurchases.setText(Integer.toString(sdm.getNumberOfUseresPurchases(App.getLoggedInUser().getUser_ID())));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
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
        App.setLastSceneFxml("profile");
    }

    @FXML
    private void openMain() throws IOException {
        App.setRoot("main");
    }

    @FXML
    private void goBack(ActionEvent event) throws Exception {
        App.switchToLastScene();
        App.setLastSceneFxml("profile");
    }

    @FXML
    private void saveLastPurchase(ActionEvent event) throws Exception {
        udm.saveCartToUser(new Cart(-1, sdm.getLatestPurchase(App.getLoggedInUser().getUser_ID()).getPurchasedProducts()),
                App.getLoggedInUser().getUser_ID());
    }
}
