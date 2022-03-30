package com.mycompany.ddueksamensprojekt;

import java.io.IOException;
import javafx.fxml.FXML;

public class SignupUserController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}