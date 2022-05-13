/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import com.mycompany.ddueksamensprojekt.App;
import com.mycompany.ddueksamensprojekt.Product;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;

/**
 *
 * @author chris
 */
public class Purchase {

    private int purchase_ID;
    private LocalDateTime purchaseDate;
    private HashMap<Product, Integer> purchasedProducts;
    
    public Purchase() {
    }

    public Purchase(int purchase_ID, LocalDateTime purchaseDate) {
        this.purchase_ID = purchase_ID;
        this.purchaseDate = purchaseDate;
    }

    public Purchase(int shoppingCart_ID, LocalDateTime purchaseDate, HashMap<Product, Integer> purchasedProducts) {
        this.purchase_ID = shoppingCart_ID;
        this.purchaseDate = purchaseDate;
        this.purchasedProducts = purchasedProducts;
    }

    public int getPurchase_ID() {
        return purchase_ID;
    }

    public void setPurchase_ID(int purchase_ID) {
        this.purchase_ID = purchase_ID;
    }

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public HashMap<Product, Integer> getPurchasedProducts() {
        return purchasedProducts;
    }

    public void setPurchasedProducts(HashMap<Product, Integer> purchasedProducts) {
        this.purchasedProducts = purchasedProducts;
    }
    
}
