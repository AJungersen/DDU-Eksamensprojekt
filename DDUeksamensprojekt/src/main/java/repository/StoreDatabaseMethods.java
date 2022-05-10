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
    
    //--------------------------------------------------
    //---------- get number of users purchase ----------
    //--------------------------------------------------
    public int getNumberOfUseresPurchases(int _user_ID) throws Exception, SQLException {
        Connection conn = null;
        Class.forName("org.sqlite.JDBC");

        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            System.out.println("\n Database error (get number of users purchase (connection): " + e.getMessage() + "\n");
        }
        
        try {
            Statement stat = conn.createStatement();
            
<<<<<<< HEAD
            ResultSet rs = stat.executeQuery("SELECT * FROM purchases WHERE user_ID = ('" + _user_ID + "');");
=======
            ResultSet rs = stat.executeQuery("SELECT user_ID FROM purchases WHERE user_ID = ('" + _user_ID + "');");
>>>>>>> 94a2f5b0fef74b3f331d95bd17d4665834003381
            
            conn.close();
            
            return rs.getFetchSize();
        } catch (SQLException e) {
            System.out.println("\n Database error (get number of users purchase (connection): " + e.getMessage() + "\n");
            
            conn.close();
            
            return 0;
        }
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

            ResultSet rs = stat.executeQuery("SELECT * FROM Purchases ");
            while (rs.next()) {
                allPurchases.add(new Purchase(rs.getInt("purchase_ID"),
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
    
    //--------------------------------------
    //---------- get all products ----------
    //--------------------------------------
    public ArrayList<Product> getAllProducts() throws SQLException, Exception {
        
        ArrayList<Product> allProducts = new ArrayList<>();
        
        Connection conn = null;
        Class.forName("org.sqlite.JDBC");
        
        //Skab forbindelse til databasen...
        
        try {          
          conn = DriverManager.getConnection(connectionString);
        } 
        catch ( SQLException e ) {
          //Skrive fejlhåndtering her
          System.out.println("DB Error: " + e.getMessage());
        }
        
        //Hent alle personer fra databasen v.h.a. SQL
        try{ 
            Statement stat = conn.createStatement();   

            //Læser fra database alt data fra databasetabellen Product.   
            ResultSet rs = stat.executeQuery("Product_ID, Name, Image, Price, Stock, ProductCategory");

            //Løber data igennem via en løkke og skriver det up.    
            while (rs.next()) {
                allProducts.add(new Product(rs.getInt("Product_ID"), rs.getString("name"), Tools.convertBufferedImageToFxImage(ImageIO.read(rs.getBinaryStream("Image"))), rs.getFloat("Price"), rs.getInt("Stock"), ProductCategory.valueOf(rs.getString("ProductCategory"))));
            }
            rs.close();
        }
        catch ( SQLException e ) {
            //Skrive fejlhåndtering her
            System.out.println("DB Error: " + e.getMessage());
        }
        //Luk forbindelsen til databasen
        conn.close();
    
        return allProducts;
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
    /*public ArrayList<Product> getAllCarts() throws SQLException, Exception {
        StoreLoadMethods slm = new StoreLoadMethods();
        ArrayList<Purchase> allCarts = new ArrayList<>();
        
        Connection conn = null;
        Class.forName("org.sqlite.JDBC");
        
        //Skab forbindelse til databasen...
        
        try {          
          conn = DriverManager.getConnection(connectionString);
        } 
        catch ( SQLException e ) {
          //Skrive fejlhåndtering her
          System.out.println("DB Error: " + e.getMessage());
        }
        
        //Hent alle personer fra databasen v.h.a. SQL
        try{ 
            Statement stat = conn.createStatement();   

            //Læser fra database alt data fra databasetabellen Product.   
            ResultSet rs = stat.executeQuery("SELECT * FROM PurchasedShoppingCarts");

            //Løber data igennem via en løkke og skriver det up.    
            while (rs.next()) {
                allCarts.add(new Purchase(rs.getInt("Product_ID"), rs.getString("name"), Tools.convertBufferedImageToFxImage(ImageIO.read(rs.getBinaryStream("Image"))), rs.getFloat("Price"), rs.getInt("Stock"), ProductCategory.valueOf(rs.getString("ProductCategory"))));
            }
            rs.close();
        }
        catch ( SQLException e ) {
            //Skrive fejlhåndtering her
            System.out.println("DB Error: " + e.getMessage());
        }
        //Luk forbindelsen til databasen
        conn.close();
    
        return allProducts;
    }*/
}
