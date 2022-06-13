/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ddueksamensprojekt;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author Clara Maj
 */
public class FavoritesViewController {
    @FXML
    private void openProfile() throws IOException {
        App.setRoot("profile");
        App.setLastSceneFxml("catelogView");
    }

    @FXML
    private void openMain() throws IOException {
        App.setRoot("main");
    }

    @FXML
    private void openCart() throws IOException {
        App.setRoot("cart");
        App.setLastSceneFxml("FavoritesViewController");
    }

    @FXML
    private void updateProductsToSearch(KeyEvent event) {
        //loadProducts();
    }

}
