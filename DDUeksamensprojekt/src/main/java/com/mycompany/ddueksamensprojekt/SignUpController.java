/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ddueksamensprojekt;

import Classes.User;
import repository.*;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 *
 * @author chris
 */
public class SignUpController {

    @FXML
    private TextField textFieldName;
    @FXML
    private Text textErroMessage = new Text();
    @FXML
    private PasswordField passwordFieldPassword;
    @FXML
    private PasswordField passwordFieldrepeatPassword;
    @FXML
    private TextField textFieldEmail;
    @FXML
    private AnchorPane anchor;
    @FXML
    private Button logInButton;

    private UserDatabaseMethods udm = new UserDatabaseMethods();
    private SecurityMethods sm = new SecurityMethods();

    @FXML
    private void creatUser(ActionEvent event) throws Exception {
        textErroMessage.setText("");

        //Check if all fields is filled
        if (!textFieldName.getText().isBlank()
                && !passwordFieldPassword.getText().isBlank()
                && !passwordFieldrepeatPassword.getText().isBlank()
                && !textFieldEmail.getText().isBlank()) {

            //Check if user already exist
            if (!udm.cehckForMatchingUser(textFieldName.getText())) {

                //Check if email has at @ and . in it and no whitespace
                if (textFieldEmail.getText().contains("@") &&
                        textFieldEmail.getText().contains(".") &&
                        !textFieldEmail.getText().contains(" ")) {

                    //Check if password have a special and uppercase character and at least 8 carachters long
                    Pattern pattern = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
                    Matcher matcher = pattern.matcher(passwordFieldPassword.getText());

                    //length
                    if (passwordFieldPassword.getText().length() >= 8) {

                        //special caracahter
                        if (matcher.find()) {

                            //upper case carachter
                            if (passwordFieldPassword.getText().equals(passwordFieldPassword.getText().toLowerCase())) {

                                //passwords is identicel
                                if (passwordFieldPassword.getText().equals(passwordFieldrepeatPassword.getText())) {


                                        udm.createUser(new User(textFieldName.getText(), textFieldEmail.getText()),
                                                sm.hexString(passwordFieldPassword.getText()));

                                        App.setLoggedInUser(udm.getLoggedInUser(textFieldEmail.getText()));

                                        //hop vider/login mangler fxml App.setRoot("");
                                } else {
                                    textErroMessage.setText("Password meets requirements, but donsen't match");
                                }
                            } else {
                                textErroMessage.setText("Password is missing a uppercase character");
                            }
                        } else {
                            textErroMessage.setText("Password is missing a special character");
                        }
                    } else {
                        textErroMessage.setText("Password needs to be at least 8 characters long");

                    }
                } else {
                    textErroMessage.setText("Pleas insert a valid email");
                }
            } else {
                textErroMessage.setText("User already exist");
            }
        } else {
            textErroMessage.setText("All fields need to be filled");
        }

    }

    @FXML
    private void switchToLoginScreen(ActionEvent event) throws IOException, Exception {
        Parent root = FXMLLoader.load(getClass().getResource("loginUser.fxml"));
        Scene scene = logInButton.getScene();
        
        root.translateYProperty().set(scene.getHeight());
        
        StackPane parent = (StackPane) scene.getRoot();
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
