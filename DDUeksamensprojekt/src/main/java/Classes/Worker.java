/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import com.mycompany.ddueksamensprojekt.Product;
import java.util.ArrayList;

/**
 *
 * @author Christoffer
 */
public class Worker extends User{
    String phoneNumber;

    public Worker(String phoneNumber, String name, String email, UserType userType) {
        super(name, email, userType);
        this.phoneNumber = phoneNumber;
    }
    
    public Worker(String phoneNumber, int user_ID, String name, String email, Wallet wallet, ArrayList<Product> favorites, ArrayList<Cart> savedCarts, UserType userType) {
        super(user_ID, name, email, wallet, favorites, savedCarts, userType);
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
