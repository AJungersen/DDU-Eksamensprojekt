/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import com.mycompany.ddueksamensprojekt.Product;
import java.time.LocalDate;
import java.util.HashMap;

/**
 *
 * @author chris
 */
public class ShoppingCart {

    private int shoppingCart_ID;
    private LocalDate purchaseDate;
    private HashMap<Product, Integer> purchasedProducts;

    public ShoppingCart() {
    }

    public ShoppingCart(int shoppingCart_ID, LocalDate purchaseDate, HashMap<Product, Integer> purchasedProducts) {
        this.shoppingCart_ID = shoppingCart_ID;
        this.purchaseDate = purchaseDate;
        this.purchasedProducts = purchasedProducts;
    }

    public int getShoppingCart_ID() {
        return shoppingCart_ID;
    }

    public void setShoppingCart_ID(int shoppingCart_ID) {
        this.shoppingCart_ID = shoppingCart_ID;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public HashMap<Product, Integer> getPurchasedProducts() {
        return purchasedProducts;
    }

    public void setPurchasedProducts(HashMap<Product, Integer> purchasedProducts) {
        this.purchasedProducts = purchasedProducts;
    }
}
