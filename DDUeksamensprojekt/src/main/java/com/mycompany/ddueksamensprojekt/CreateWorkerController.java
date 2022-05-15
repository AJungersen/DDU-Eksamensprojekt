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
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import repository.*;
import static com.mycompany.ddueksamensprojekt.App.scene;

/**
 *
 * @author Clara Maj
 */
public class CreateWorkerController implements Initializable {

    /**
     * Initializes the controller class.
     */
    z
    @FXML
    private ImageView workerImage;
    private final FileChooser fc = new FileChooser();
    private File selectedFiles;
    private UserDatabaseMethods udm = new UserDatabaseMethods();
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    @FXML
    private void closePopUp(ActionEvent event) throws Exception{
        App.closePopup();
    }
    @FXML
    private void selectImage(ActionEvent event) throws Exception {
        fc.setTitle("Select files");

        //Starting route for file chooser
        fc.setInitialDirectory(new File(System.getProperty("user.home")));

        //Type filters, since the file we are looking for is json we dont need the others
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("image files", "*.jpg", "*.jpeg", "*.png"));

        //selectedFiles = fc.showOpenMultipleDialog(null).get(0);
        selectedFiles = fc.showOpenDialog(null);

        productImage.setImage(new Image(new FileInputStream(selectedFiles)));
    }
     @FXML
    private void checkIfKeyTypedIsFloat(KeyEvent event) {
        if (!Tools.isFloat(((TextField) event.getTarget()).getText()) || ((TextField) event.getTarget()).getText().contains("d") 
                || ((TextField) event.getTarget()).getText().contains("f")) {
            
            if (event.getCharacter().equals(".")) {
                String regex = "\\.";
                ((TextField) event.getTarget()).setText(((TextField) event.getTarget()).getText().replaceFirst(regex, ""));
            } else {
                ((TextField) event.getTarget()).setText(((TextField) event.getTarget()).getText().replaceFirst(event.getCharacter(), ""));
            }
            //update courser position to end
            ((TextField) event.getTarget()).positionCaret(((TextField) event.getTarget()).getText().length());
        }
    }
    @FXML
    private void createWorker(ActionEvent event) throws Exception {
        textErrorMessage.setText("");

        //Check if all fields is filled
        if (!textFieldName.getText().isBlank()
                && !textFieldNumber.getText().isBlank()
                && !passwordFieldPassword.getText().isBlank()
                && !passwordFieldrepeatPassword.getText().isBlank()
                && !textFieldEmail.getText().isBlank()) {

            //Check if email already exist
            if (!udm.checkForMatchingEmail(textFieldEmail.getText())) {

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
                                    textErrorMessage.setText("Koden møder kravene men matcher ikke");
                                }
                            } else {
                                textErrorMessage.setText("Koden mangler et stort bogstav");
                            }
                        } else {
                            textErrorMessage.setText("Koden mangler et specielt tegn");
                        }
                    } else {
                        textErrorMessage.setText("Koden skal være mindst 8 tegn langt");

                    }
                } else {
                    textErrorMessage.setText("Vær venlig at indsætte en valid email");
                }
            } else {
                textErrorMessage.setText("Medarbejderen findes allerede");
            }
        } else {
            textErrorMessage.setText("Alle felterne skal være udfyldt");
            System.out.println(textErrorMessage.getText());
    }
}