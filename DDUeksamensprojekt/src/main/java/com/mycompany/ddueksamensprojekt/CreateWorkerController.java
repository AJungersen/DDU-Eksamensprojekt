/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ddueksamensprojekt;

import Classes.User;
import Classes.UserType;
import Classes.Worker;
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
import java.io.FileInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDateTime;
import java.util.regex.Pattern;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.util.Callback;

/**
 *
 * @author Clara Maj
 */
public class CreateWorkerController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextField textFieldName;
    @FXML
    private TextField textFieldEmail;
    @FXML
    private TextField textFieldNumber;
    @FXML
    private PasswordField passwordFieldPassword;
    @FXML
    private PasswordField passwordFieldPasswordRepeat;
    @FXML
    private Text textErrorMessage;
    @FXML
    private ImageView workerImage;
    private final FileChooser fc = new FileChooser();
    private File selectedFiles;
    private UserDatabaseMethods udm = new UserDatabaseMethods();
    private SecurityMethods sm = new SecurityMethods();
    private AdminDataBaseMethods adbm = new AdminDataBaseMethods();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void closePopUp(ActionEvent event) throws Exception {
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

        workerImage.setImage(new Image(new FileInputStream(selectedFiles)));
    }

    @FXML
    private void checkIfKeyTypedIsInteger(KeyEvent event) {
        //check if int
        if (!Tools.isInteger(((TextField) event.getTarget()).getText())) {
            //if not remove that char
            ((TextField) event.getTarget()).setText(((TextField) event.getTarget()).getText().replace(event.getCharacter(), ""));

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
                && !passwordFieldPasswordRepeat.getText().isBlank()
                && !textFieldEmail.getText().isBlank()) {

            //Check if email already exist
            if (!udm.checkForMatchingEmail(textFieldEmail.getText())) {

                //Check if email has at @ and . in it and no whitespace
                if (textFieldEmail.getText().contains("@")
                        && textFieldEmail.getText().contains(".")
                        && !textFieldEmail.getText().contains(" ")) {
                    //check if phone number is correct
                    if (textFieldNumber.getText().length() == 8) {
                        //Check if password have a special and uppercase character and at least 8 carachters long
                        //length
                        if (passwordFieldPassword.getText().length() >= 8) {

                            //special caracahter
                            Pattern pattern = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
                            if (pattern.matcher(passwordFieldPassword.getText()).find()) {

                                //upper case carachter
                                if (!passwordFieldPassword.getText().equals(passwordFieldPassword.getText().toLowerCase())) {

                                    //passwords is identicel
                                    if (passwordFieldPassword.getText().equals(passwordFieldPasswordRepeat.getText())) {

                                        Worker newWorker = new Worker(textFieldNumber.getText(), textFieldName.getText(), textFieldEmail.getText(), UserType.WORKER);
                                        
                                        adbm.createWorker(newWorker, sm.hexString(passwordFieldPassword.getText()));

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
                        textErrorMessage.setText("Indtast venligst et validt telefon nummer");
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
}
