/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import Classes.Product;
import Classes.User;
import java.util.ArrayList;

/**
 *
 * @author danie
 */
public class Cart {
    User user;
    ArrayList<Product> products;

    public Cart(User user, ArrayList<Product> products) {
        this.user = user;
        this.products = products;
    }

    public User getUser() {
        return user;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }
    
}
