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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
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

                App.setLoggedInUser(udm.getoggedInUser(textFieldEmail.getText()));

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
        App.setRoot("SignUp");
    }
}
