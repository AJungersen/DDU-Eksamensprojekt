/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.util.ArrayList;

/**
 *
 * @author chris
 */
public class User {

    private int user_ID;
    private String name;
    private String email;
    private Wallet wallet;
    private ArrayList<Product> favorites;

    public User(int user_ID, String name, String email, Wallet wallet, ArrayList<Product> favorites) {
        this.user_ID = user_ID;
        this.name = name;
        this.email = email;
        this.wallet = wallet;
        this.favorites = favorites;
    }
    
    //sign up
    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public User() {
    }

    public int getUser_ID() {
        return user_ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public ArrayList<Product> getFavorites() {
        return favorites;
    }

    public void setFavorites(ArrayList<Product> favorites) {
        this.favorites = favorites;
    }
}
