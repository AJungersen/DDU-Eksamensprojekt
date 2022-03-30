/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ddueksamensprojekt;

import Classes.User;
import repository.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import repository.UserDatabaseMethods;

/**
 *
 * @author chris
 */
public class LoginController {

    @FXML
    private TextField textFieldEmail;
    @FXML
    private PasswordField passwordFieldPassword;
    @FXML
    private Text textErrorMessage;
    @FXML
    private Button signUpButton;
    @FXML
    private AnchorPane anchor;
    @FXML
    private StackPane parent;

    private UserDatabaseMethods udm = new UserDatabaseMethods();
    private SecurityMethods sm = new SecurityMethods();

    @FXML
    private void login(ActionEvent event) throws Exception {
        textErrorMessage.setText("");

        //check if user exist
        if (udm.cehckForMatchingUser(textFieldEmail.getText())) {

            //check if user and passowrd match
            if (udm.checkForMatchingPassword(textFieldEmail.getText(),
                    sm.hexString(passwordFieldPassword.getText()))) {

                App.setLoggedInUser(udm.getLoggedInUser(textFieldEmail.getText()));

                //login
            } else {
                textErrorMessage.setText("user dosen't exist or password dont match");
            }
        } else {
            textErrorMessage.setText("user dosen't exist or password dont match");
        }
    }

    @FXML
    private void switchToSignUp(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("signupUser.fxml"));
        Scene scene = signUpButton.getScene();
        
        root.translateYProperty().set(scene.getHeight());
        parent.getChildren().add(root);
        
        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(1),kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(event1 -> {
            parent.getChildren().remove(anchor);
        });
        timeline.play();
    }
}
