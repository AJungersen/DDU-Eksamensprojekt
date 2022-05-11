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
import javafx.scene.control.TextField;
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
    private TextField textFieldOldPassword;
    @FXML
    private TextField textFieldNewPassword;
    @FXML
    private TextField textFieldNewPasswordRepeat;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    @FXML
    private void closePopUp(ActionEvent event) throws Exception{
        App.closePopup();
    }

    @FXML
    private void changePassword(ActionEvent event) throws Exception {
        if(udm.checkForMatchingPassword(App.getLoggedInUser().getEmail(), 
                sm.hexString(textFieldOldPassword.getText())) && 
                textFieldNewPassword.getText().equals(textFieldNewPasswordRepeat.getText())){
            udm.updateUserPassword(App.getLoggedInUser().getUser_ID(),
                    sm.hexString(textFieldNewPassword.getText()));
            
            //sucess message
        } else {
            //error message
        }
    }
}
