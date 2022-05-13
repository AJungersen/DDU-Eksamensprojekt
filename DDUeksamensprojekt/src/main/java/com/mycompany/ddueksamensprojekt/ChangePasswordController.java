/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ddueksamensprojekt;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import repository.SecurityMethods;
import repository.Tools;
import repository.UserDatabaseMethods;

/**
 *
 * @author Clara Maj
 */
public class ChangePasswordController implements Initializable {
    private UserDatabaseMethods udm = new UserDatabaseMethods();
    private SecurityMethods sm = new SecurityMethods();
    
    @FXML
    private Text textErroField;
    @FXML
    private PasswordField passwordFieldNewPassword;
    @FXML
    private PasswordField passwordFielNewPasswordRepeat;
    @FXML
    private PasswordField passwordFieldOldPassword;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        textErroField.setText("");
    }
    @FXML
    private void closePopUp(ActionEvent event) throws Exception{
        App.closePopup();
    }

    @FXML
    private void changePassword(ActionEvent event) throws Exception {
        if(udm.checkForMatchingPassword(App.getLoggedInUser().getEmail(), 
                sm.hexString(passwordFieldOldPassword.getText())) && 
                passwordFieldNewPassword.getText().equals(passwordFielNewPasswordRepeat.getText())){
            udm.updateUserPassword(App.getLoggedInUser().getUser_ID(),
                    sm.hexString(passwordFieldNewPassword.getText()));
            
            //sucess message
            textErroField.setFill(Paint.valueOf("green"));
            textErroField.setText("Pssword er opdateret");
            
            passwordFieldNewPassword.setText("");
            passwordFielNewPasswordRepeat.setText("");
            passwordFieldOldPassword.setText("");
            
        } else {
            //error message
            textErroField.setFill(Paint.valueOf("red"));
            textErroField.setText("Enten er det rigtige password ikke intastet, eller også matcher de to nye passwords ikke");
        }
    }

    @FXML
    private void clearErrorMessage(KeyEvent event) {
        textErroField.setText("");
    }
}
