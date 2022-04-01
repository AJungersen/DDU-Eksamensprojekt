/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ddueksamensprojekt;

import Classes.Coupon;
import Classes.CreditCard;
import Classes.User;
import Classes.Wallet;
import static com.mycompany.ddueksamensprojekt.App.scene;
import repository.*;
import java.io.IOException;
import java.util.ArrayList;
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
    private TextField textFieldName = new TextField();
    @FXML
    private Text textErrorMessage;
    @FXML
    private PasswordField passwordFieldPassword;
    @FXML
    private PasswordField passwordFieldrepeatPassword;
    @FXML
    private TextField textFieldEmail;
    @FXML
    private AnchorPane anchor;
    @FXML
    private Button logInButton = new Button();

    private UserDatabaseMethods udm = new UserDatabaseMethods();
    private SecurityMethods sm = new SecurityMethods();

    @FXML
    private void creatUser(ActionEvent event) throws Exception {
        textErrorMessage.setText("");

        //Check if all fields is filled
        if (!textFieldName.getText().isBlank()
                && !passwordFieldPassword.getText().isBlank()
                && !passwordFieldrepeatPassword.getText().isBlank()
                && !textFieldEmail.getText().isBlank()) {

            //Check if user already exist
            if (!udm.checkForMatchingUser(textFieldName.getText())) {

                //Check if email has at @ and . in it and no whitespace
                if (textFieldEmail.getText().contains("@")
                        && textFieldEmail.getText().contains(".")
                        && !textFieldEmail.getText().contains(" ")) {

                    //Check if password have a special and uppercase character and at least 8 carachters long
                    //length
                    if (passwordFieldPassword.getText().length() >= 8) {

                        //special caracahter
                        Pattern pattern = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
                        if (pattern.matcher(passwordFieldPassword.getText()).find()) {
                            
                            //upper case carachter
                            if (!passwordFieldPassword.getText().equals(passwordFieldPassword.getText().toLowerCase())) {

                                //passwords is identicel
                                if (passwordFieldPassword.getText().equals(passwordFieldrepeatPassword.getText())) {

                                    udm.createUser(new User(textFieldName.getText(), textFieldEmail.getText()),
                                            sm.hexString(passwordFieldPassword.getText()));

                                    App.setLoggedInUser(udm.getLoggedInUser(textFieldEmail.getText()));

                                    //hop vider/login mangler fxml App.setRoot("");
                                } else {
                                    textErrorMessage.setText("Password meets requirements, but donsen't match");
                                }
                            } else {
                                textErrorMessage.setText("Password is missing a uppercase character");
                            }
                        } else {
                            textErrorMessage.setText("Password is missing a special character");
                        }
                    } else {
                        textErrorMessage.setText("Password needs to be at least 8 characters long");

                    }
                } else {
                    textErrorMessage.setText("Pleas insert a valid email");
                }
            } else {
                textErrorMessage.setText("User already exist");
            }
        } else {
            textErrorMessage.setText("All fields need to be filled");
            System.out.println(textErrorMessage.getText());
        }

    }

    @FXML
    private void switchToLoginScreen(ActionEvent event) throws IOException, Exception {
        Parent root = FXMLLoader.load(getClass().getResource("loginUser.fxml"));

        Scene scene = App.scene;

        root.translateYProperty().set(scene.getHeight());

        StackPane parent = (StackPane) scene.getRoot();
        parent.getChildren().add(root);

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(event1 -> {
            parent.getChildren().remove(anchor);
        });
        timeline.play();
    }
}
    