/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import com.mycompany.ddueksamensprojekt.Product;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Christoffer
 */
public class UserLoadMethods {
    //-----------------------------------------------
    //---------- load saved carts products ----------
    //-----------------------------------------------
    public static HashMap<Product, Integer> loadSavedCartsProducts(Connection conn, int _savedCart_ID) throws SQLException, Exception {
        HashMap<Product, Integer> orderedProducts = new HashMap<>();
        try {
            Statement stat = conn.createStatement();
            
            //get product
            ResultSet rs = stat.executeQuery("SELECT * FROM Products WHERE product_ID IN"
                    + "(SELECT product_ID FROM SavedShoppingCartsProducts "
                    + "WHERE purchasedShoppingCarts_ID = ('" + _savedCart_ID + "'));");

            ArrayList<Product> products = StoreLoadMethods.loadProducts(rs);
            
            //get the amounts
            for (Product p : products) {
                rs = stat.executeQuery("SELECT amount FROM SavedShoppingCartsProducts "
                        + "WHERE product_ID = ('" + p.getItem_ID() + "');");
                //set the products whit amounts
                orderedProducts.put(p, rs.getInt("amount"));
            }
        } catch (SQLException e) {
            System.out.println("\n Database error (load saved carts products (connection): " + e.getMessage() + "\n");
        }

        return orderedProducts;
    }
}
