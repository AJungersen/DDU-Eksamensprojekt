package com.mycompany.ddueksamensprojekt;

import Classes.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.stage.Popup;

/**
 * JavaFX App
 */
public class App extends Application {
    
    private static User loggedInUser = new User();
    private static Scene scene;
    private static Stage stage;
    private static Popup popup;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("loginUser"));
        stage.setScene(scene);
        stage.show();
        
        this.stage = stage;
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
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
}