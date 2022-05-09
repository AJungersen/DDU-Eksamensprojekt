/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import com.mycompany.ddueksamensprojekt.Product;
import Classes.ProductCategory;
import Classes.Purchase;
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
    public Purchase getLatestPurchase(int _user_ID) throws SQLException, Exception {
        Purchase purchase = new Purchase();

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

            purchase = new Purchase(rs.getInt("purchasedShoppingCarts_ID"),
                    LocalDate.parse(rs.getString("date")), null);

        } catch (SQLException e) {
            System.out.println("\n Database error (get latest purchase (connection): " + e.getMessage() + "\n");
        }

        purchase.setPurchasedProducts(StoreLoadMethods.loadPurchasedProducts(conn, purchase.getPurchase_ID()));

        conn.close();
        return purchase;
    }

    //--------------------------------------
    //---------- get all purchase ----------
    //--------------------------------------
    public ArrayList<Purchase> getAllPurchase() throws SQLException, Exception {
        ArrayList<Purchase> allPurchases = new ArrayList<>();

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

            ResultSet rs = stat.executeQuery("SELECT * FROM PurchasedShoppingCarts ");
            while (rs.next()) {
                allPurchases.add(new Purchase(rs.getInt("purchasedShoppingCarts_ID"),
                        LocalDate.parse(rs.getString("date")), null));
            }
        } catch (SQLException e) {
            System.out.println("\n Database error (get latest purchase (connection): " + e.getMessage() + "\n");
        }
        for (Purchase p : allPurchases) {
            p.setPurchasedProducts(StoreLoadMethods.loadPurchasedProducts(conn, p.getPurchase_ID()));
        }

        conn.close();

        return allPurchases;
    }

    //-------------------------------------------------------
    //---------- get products in specefic category ----------
    //-------------------------------------------------------
    public ArrayList<Product> getProductsInSpeceficCategory(ProductCategory _category) throws SQLException, Exception {
        ArrayList<Product> products = new ArrayList<>();

        Connection conn = null;
        Class.forName("org.sqlite.JDBC");

        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            System.out.println("\n Database error (get products in specefic category (connection): " + e.getMessage() + "\n");
        }

        try {
            Statement stat = conn.createStatement();

            ResultSet rs = stat.executeQuery("SELECT * FROM Products "
                    + "WHERE ProductCategory = ('" + _category.toString() + "')");

            products = StoreLoadMethods.loadProducts(rs);

        } catch (SQLException e) {
            System.out.println("\n Database error (get products in specefic category (get products): " + e.getMessage() + "\n");
        }

        conn.close();

        return products;
    }

}
