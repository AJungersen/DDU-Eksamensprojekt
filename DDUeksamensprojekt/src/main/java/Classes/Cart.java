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

    User user;
    private ArrayList<Product> products;

    public Cart(User user, HashMap<Product, Integer> products) {
        this.user = user;
        for (Product p : products.keySet()) {
            for (int i = 0; i < products.get(p); i++) {
                this.products.add(p);
            }
        }
    }

    public Cart(User user, ArrayList<Product> products) {
        this.user = user;
        this.products = products;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public HashMap<Product, Integer> getProductsAsMap() {
        HashMap<Product, Integer> map = new HashMap<>();

        for (Product p : products) {
            if (map.size() > 0) {
                for (Product mp : map.keySet()) {
                    if (p.getItem_ID() == mp.getItem_ID()) {
                        map.put(mp, map.get(mp) + 1);

                    } else {
                        map.put(p, 1);
                    }

                }
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
