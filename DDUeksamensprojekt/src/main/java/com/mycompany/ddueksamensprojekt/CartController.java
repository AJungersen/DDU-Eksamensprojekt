/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.ddueksamensprojekt;

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
import javafx.stage.Popup;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Clara Maj
 */
public class CartController implements Initializable {

    ArrayList<TableViewDisplayPurchase> tableViewDispalyData = new ArrayList<>();
    @FXML
    TextField goodNumber;
    @FXML
    TextField price;
    @FXML
    TableView goods;
    @FXML
    private TableColumn<TableViewDisplayPurchase, ImageView> tableColumnImage;
    @FXML
    private TableColumn<TableViewDisplayPurchase, String> tableColumnName;
    @FXML
    private TableColumn<TableViewDisplayPurchase, Integer> tableColumnPrice;
    @FXML
    private TableColumn<TableViewDisplayPurchase, Integer> tableColumnAmount;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        int goodNumb = 0;
        float allPrice = 0;
        HashMap<Product, Integer> hp = App.getCurrentCart().getProducts();

        for (Product p : hp.keySet()) {
            tableViewDispalyData.add(new TableViewDisplayPurchase(hp.get(p), p));
        }
        
        for (TableViewDisplayPurchase tvdp : tableViewDispalyData){
            goodNumb += tvdp.getAmount();
            allPrice += tvdp.getPrice()*tvdp.getAmount();
        }

        tableColumnImage.setCellValueFactory(new PropertyValueFactory<>("displayImage"));
        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableColumnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        tableColumnAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        
        goods.getItems().setAll(tableViewDispalyData);
        
        goodNumber.setText(Integer.toString(goodNumb));
        price.setText(Float.toString(allPrice));
        
        App.numberOfGoods = goodNumb;
        App.priceOfGoods = allPrice;
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
    private void goBack(ActionEvent event) throws Exception{
        App.switchToLastScene();
        App.setLastSceneFxml("cart");
    }
}
