/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.ddueksamensprojekt;

import Classes.Cart;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Popup;
import javafx.stage.Stage;
import org.w3c.dom.UserDataHandler;
import repository.UserDatabaseMethods;

/**
 * FXML Controller class
 *
 * @author Clara Maj
 */
public class CartController implements Initializable {

    private UserDatabaseMethods udm = new UserDatabaseMethods();

    ArrayList<TableViewDisplayPurchase> tableViewDispalyData = new ArrayList<>();
    @FXML
    TextField goodNumber;
    @FXML
    TextField price;
    @FXML
    TableView<TableViewDisplayPurchase> goods;
    @FXML
    private TableColumn<TableViewDisplayPurchase, ImageView> tableColumnImage;
    @FXML
    private TableColumn<TableViewDisplayPurchase, String> tableColumnName;
    @FXML
    private TableColumn<TableViewDisplayPurchase, Integer> tableColumnPrice;
    @FXML
    private TableColumn<TableViewDisplayPurchase, Integer> tableColumnAmount;
    @FXML
    private TableColumn<TableViewDisplayPurchase, Integer> tableColumnTotalPrice;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            int goodNumb = 0;
            float allPrice = 0;
            HashMap<Product, Integer> hp = App.getCurrentCart().getProductsAsMap();
            System.out.println(hp.size());
            for (Product p : hp.keySet()) {
                //System.out.println(hp.size());
                tableViewDispalyData.add(new TableViewDisplayPurchase(hp.get(p), p));
            }

            for (TableViewDisplayPurchase tvdp : tableViewDispalyData) {
                goodNumb += tvdp.getAmount();
                allPrice += tvdp.getPrice() * tvdp.getAmount();
            }

            tableColumnImage.setCellValueFactory(new PropertyValueFactory<>("displayImage"));
            tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
            tableColumnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
            tableColumnAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
            tableColumnTotalPrice.setCellValueFactory(new PropertyValueFactory<>("total"));

            goods.getItems().setAll(tableViewDispalyData);

            goodNumber.setText(Integer.toString(goodNumb));
            price.setText(Float.toString(allPrice));

            App.numberOfGoods = goodNumb;
            App.priceOfGoods = allPrice;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void openMain() throws Exception {
        App.setRoot("main");
    }

    @FXML
    void openProfile() throws Exception {
        App.setRoot("profile");
        App.setLastSceneFxml("cart");
    }

    @FXML
    void openCheckOut(ActionEvent event) throws IOException {

        Stage stage = App.getStage();
        Popup popup = new Popup();

        popup.getContent().addAll(App.loadFXML("checkOut").getChildrenUnmodifiable());
        popup.setX(stage.getWidth() * 1.3);
        popup.setY(stage.getHeight() / 2);

        App.setPopup(popup);

        App.openPopup();
    }

    @FXML
    private void goBack(ActionEvent event) throws Exception {
        App.switchToLastScene();
        App.setLastSceneFxml("cart");
    }

    @FXML
    private void goToProduct(MouseEvent event) throws Exception {
        if (goods.getFocusModel().getFocusedCell().getColumn() <= 1) {
            //send to product
            App.setCurrentProduct(goods.getSelectionModel().getSelectedItem());
            App.setRoot("productInformation");
        }
    }

    @FXML
    private void saveCart(ActionEvent event) throws Exception {
        Cart cart = new Cart(-1, new ArrayList<Product>(goods.getSelectionModel().getSelectedItems()));

        udm.saveCartToUser(cart, App.getLoggedInUser().getUser_ID());

        App.getLoggedInUser().setSavedCarts(udm.getUsersSavedCarts(App.getLoggedInUser().getUser_ID()));
    }
}
