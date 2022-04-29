/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import com.mycompany.ddueksamensprojekt.Product;
import Classes.ProductCategory;
import Classes.ShoppingCart;
import java.awt.image.BufferedImage;
import java.net.ConnectException;
import java.security.interfaces.RSAKey;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;

/**
 *
 * @author chris
 */
public class StoreDatabaseMethods {

    private final String connectionString = "jdbc:sqlite:Database.db";

    //-----------------------------------------
    //---------- get latest purchase ----------
    //-----------------------------------------
    public ShoppingCart getLatestPurchase(int _user_ID) throws SQLException, Exception {
        ShoppingCart shoppingCart = new ShoppingCart();

        Connection conn = null;
        Class.forName("org.sqlite.JDBC");

        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            System.out.println("\n Database error (get latest purchase (connection): " + e.getMessage() + "\n");
        }

        //get info
        try {
            Statement stat = conn.createStatement();

            ResultSet rs = stat.executeQuery("SELECT * FROM PurchasedShoppingCarts "
                    + "WHERE user_ID = ('" + _user_ID + "') ORDER BY purchasedShoppingCarts_ID LIMIT '1'");

            shoppingCart = new ShoppingCart(rs.getInt("purchasedShoppingCarts_ID"),
                    LocalDate.parse(rs.getString("date")), null);

        } catch (SQLException e) {
            System.out.println("\n Database error (get latest purchase (connection): " + e.getMessage() + "\n");
        }

        //get items
        try {
            Statement stat = conn.createStatement();

            ResultSet rs = stat.executeQuery("SELECT * FROM Products WHERE product_ID IN"
                    + "(SELECT product_ID FROM PurchasedShoppingCartsProducts "
                    + "WHERE purchasedShoppingCarts_ID = ('" + shoppingCart.getShoppingCart_ID() + "'));");

            ArrayList<Product> products = StoreLoadMethods.loadProducts(rs);

            HashMap<Product, Integer> shoppingCartProducts = new HashMap();
            for (Product p : products) {
                rs = stat.executeQuery("SELECT amount FROM PurchasedShoppingCartsProducts "
                        + "WHERE product_ID = ('" + p.getItem_ID() + "');");

                shoppingCartProducts.put(p, rs.getInt("amount"));
            }

            shoppingCart.setPurchasedProducts(shoppingCartProducts);

        } catch (SQLException e) {
            System.out.println("\n Database error (get latest purchase (connection): " + e.getMessage() + "\n");
        }

        conn.close();
        return shoppingCart;
    }
    
}
