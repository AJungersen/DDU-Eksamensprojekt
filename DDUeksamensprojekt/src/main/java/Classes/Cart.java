/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import Classes.User;
import com.mycompany.ddueksamensprojekt.Product;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author danie
 */
public class Cart {

    int cart_ID;
    private ArrayList<Product> products = new ArrayList<>();

    public Cart(int cart_ID, HashMap<Product, Integer> products) {
        this.cart_ID = cart_ID;
        for (Product p : products.keySet()) {
            for (int i = 0; i < products.get(p); i++) {
                this.products.add(p);
            }
        }
    }

    public Cart(int cart_ID, ArrayList<Product> products) {
        this.cart_ID = cart_ID;
        this.products = products;
    }

    public int getCart_ID() {
        return cart_ID;
    }
    
    public HashMap<Product, Integer> getProductsAsMap() {
        HashMap<Product, Integer> map = new HashMap<>();
        HashMap<Product, Integer> temp = new HashMap<>();
        
        
        for (Product p : products) {
            if (map.size() > 0) {
                for (Product mp : map.keySet()) {
                    if (p.getItem_ID() == mp.getItem_ID()) {
                        temp.put(mp, map.get(mp) + 1);

                    } else {
                        temp.put(p, 1);
                    }

                }
                map = temp;
            } else {
                map.put(p, 1);
            }
        }
        return map;
    }

    public ArrayList<Product> getProductsAsList() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }
}
