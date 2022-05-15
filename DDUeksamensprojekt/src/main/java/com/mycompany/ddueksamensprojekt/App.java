package com.mycompany.ddueksamensprojekt;

import Classes.Cart;
import Classes.ProductCategory;
import Classes.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.stage.Popup;
import repository.AdminDataBaseMethods;

/**
 * JavaFX App
 */
public class App extends Application {
    
    private static User loggedInUser = new User();
    public static Scene scene;
    public static int numberOfGoods = 0;
    public static float priceOfGoods = 0;
    private static Stage stage;
    private static Popup popup;
    private static ProductCategory currentCategoryDisplaying;
    public static Cart currentCart = new Cart(-1, new ArrayList<Product>());
    public static Product currentProduct;
    public static String lastSceneFxml;
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy/ HH:mm:ss");

    @Override
    public void start(Stage stage) throws IOException, Exception {
        AdminDataBaseMethods.setProductCategorysImages();

        //scene = new Scene(loadFXML("loginUser"));
        //scene = new Scene(loadFXML("mainAdmin"));
        //scene = new Scene(loadFXML("AdminAddImageToCategorys"));
        //scene = new Scene(loadFXML("main"));
        scene = new Scene(loadFXML("mainAdmin"));
        
        stage.setScene(scene);
        stage.show();
        
        this.stage = stage;
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
        stage.sizeToScene();
        stage.centerOnScreen();
    }
    
    static void switchToLastScene() throws IOException{
        setRoot(lastSceneFxml);
    }

    static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load(); 
    }

    public static void main(String[] args) {
        launch();
    }
    
    public static Stage getStage(){
        return stage;
    }

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static void setLoggedInUser(User loggedInUser) {
        App.loggedInUser = loggedInUser;
    }
    
    public static void setPopup(Popup popup) {
        App.popup = popup;
    }

    public static Popup getPopup() {
        return popup;
    }

    public static void closePopup() {
        popup.hide();
    }

    public static void openPopup() {
        popup.show(stage);
    }

    public static void setCurrentCategoryDisplaying(ProductCategory currentCategoryDisplaying) {
        App.currentCategoryDisplaying = currentCategoryDisplaying;
    }

    public static ProductCategory getCurrentCategoryDisplaying() {
        return currentCategoryDisplaying;
    }

    public static Product getCurrentProduct() {
        return currentProduct;
    }

    public static void setCurrentProduct(Product currentProduct) {
        App.currentProduct = currentProduct;
    }

    public static Cart getCurrentCart() {
        return currentCart;
    }

    public static void setCurrentCart(Cart currentCart) {
        App.currentCart = currentCart;
    }

    public static void setLastSceneFxml(String lastSceneFxml) {
        App.lastSceneFxml = lastSceneFxml;
    }

    public static String getLastSceneFxml() {
        return lastSceneFxml;
    }

    public static DateTimeFormatter getDtf() {
        return dtf;
    }
}