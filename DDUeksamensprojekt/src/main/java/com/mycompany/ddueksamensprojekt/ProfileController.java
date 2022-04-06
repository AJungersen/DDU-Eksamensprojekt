/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.ddueksamensprojekt;

import Classes.ProductCategory;
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
    private TableColumn<TableViewDispaly, Image> tableColumnImage;
    @FXML
    private TableColumn<TableViewDispaly, String> tableColumnName;
    @FXML
    private TableColumn<TableViewDispaly, Integer> tableColumnPrice;
    @FXML
    private TableColumn<TableViewDispaly, Integer> tableColumnAmount;
    @FXML
    private TableView<TableViewDispaly> tableViewLastPurchas;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
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
    private void goToSelectedProduct(MouseEvent event) {

        if (tableViewLastPurchas.getFocusModel().getFocusedCell().getColumn() == 1) {
            //send to product
            System.out.println(tableViewLastPurchas.getSelectionModel().getSelectedItem().getAmount());
        }
    }
}
