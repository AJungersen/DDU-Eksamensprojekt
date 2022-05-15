/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ddueksamensprojekt;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 *
 * @author Clara Maj
 */
public class TermsAndConditionsController {
    
    @FXML
    private void closePopUp(ActionEvent event) throws Exception{
        App.closePopup();
    }
    @FXML
    private void acceptTerms() throws Exception{
        
        App.closePopup();
    }
}
