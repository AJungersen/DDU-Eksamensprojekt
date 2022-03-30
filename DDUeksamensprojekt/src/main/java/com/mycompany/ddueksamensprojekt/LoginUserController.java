package com.mycompany.ddueksamensprojekt;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.util.Duration;

public class LoginUserController implements Initializable{

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
    
    @FXML
    private void movesOn (ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("scene2.fxml"));
        Scene scene = movingOnButton.getScene();
        
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
