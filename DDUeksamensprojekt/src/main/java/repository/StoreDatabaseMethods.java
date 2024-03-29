/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import Classes.Cart;
import com.mycompany.ddueksamensprojekt.Product;
import Classes.ProductCategory;
import Classes.Purchase;
import Classes.User;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

            ResultSet rs = stat.executeQuery("SELECT * FROM Purchases "
                    + "WHERE user_ID = ('" + _user_ID + "') ORDER BY purchase_ID DESC LIMIT '1'");

            purchase = new Purchase(rs.getInt("purchase_ID"),
                    LocalDateTime.parse(rs.getString("date")), null);
            
            purchase.setPurchasedProducts(StoreLoadMethods.loadPurchasedProducts(conn, purchase.getPurchase_ID()));
            
        } catch (SQLException e) {
            System.out.println("\n Database error (get latest purchase (get info): " + e.getMessage() + "\n");
        }

        conn.close();

        return purchase;
    }

    //--------------------------------------------------
    //---------- get number of users purchase ----------
    //--------------------------------------------------
    public int getNumberOfUseresPurchases(int _user_ID) throws Exception, SQLException {
        int numberOfPurchases = 0;

        Connection conn = null;
        Class.forName("org.sqlite.JDBC");

        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            System.out.println("\n Database error (get number of users purchase (connection): " + e.getMessage() + "\n");
        }

        try {
            Statement stat = conn.createStatement();

            ResultSet rs = stat.executeQuery("SELECT Purchase_ID FROM purchases WHERE user_ID = ('" + _user_ID + "');");

            while (rs.next()) {
                numberOfPurchases++;
            }

        } catch (SQLException e) {
            System.out.println("\n Database error (get number of users purchase (connection): " + e.getMessage() + "\n");
        }

        conn.close();

        return numberOfPurchases;
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
            System.out.println("\n Database error (get all purchase (connection): " + e.getMessage() + "\n");
        }

        //get info
        try {
            Statement stat = conn.createStatement();

            ResultSet rs = stat.executeQuery("SELECT * FROM Purchases ");
            while (rs.next()) {
                allPurchases.add(new Purchase(rs.getInt("purchase_ID"),
                        LocalDateTime.parse(rs.getString("date")), null));
            }
        } catch (SQLException e) {
            System.out.println("\n Database error (get all purchases (get info): " + e.getMessage() + "\n");
        }
        for (Purchase p : allPurchases) {
            p.setPurchasedProducts(StoreLoadMethods.loadPurchasedProducts(conn, p.getPurchase_ID()));
        }

        conn.close();

        return allPurchases;
    }

    //---------------------------------------------
    //---------- get all users purcahses ----------
    //---------------------------------------------
    public ArrayList<Purchase> getAllUsersPurchases(int _user_ID) throws Exception, SQLException {
        ArrayList<Purchase> allPurchases = new ArrayList<>();

        Connection conn = null;
        Class.forName("org.sqlite.JDBC");

        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            System.out.println("\n Database error (get all users purcahses (connection): " + e.getMessage() + "\n");
        }

        //get info
        try {
            Statement stat = conn.createStatement();

            ResultSet rs = stat.executeQuery("SELECT * FROM Purchases WHERE user_ID = ('" + _user_ID + "') ");
            while (rs.next()) {
                allPurchases.add(new Purchase(rs.getInt("purchase_ID"),
                        LocalDateTime.parse(rs.getString("date")), null));
            }
        } catch (SQLException e) {
            System.out.println("\n Database error (get all users purcahses (info): " + e.getMessage() + "\n");
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

        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            System.out.println("\n Database error (get alle products (connection): " + e.getMessage() + "\n");
        }

        try {
            Statement stat = conn.createStatement();

            ResultSet rs = stat.executeQuery("SELECT * FROM Products");

            allProducts = StoreLoadMethods.loadProducts(rs);

        } catch (SQLException e) {
            System.out.println("\n Database error (get alle products (get products): " + e.getStackTrace() + "\n");
        }

        conn.close();

        return allProducts;

        /*
        ArrayList<Product> allProducts = new ArrayList<>();
        
        Connection conn = null;
        Class.forName("org.sqlite.JDBC");

        //Skab forbindelse til databasen...
        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            //Skrive fejlhåndtering her
            System.out.println("DB Error: " + e.getMessage());
        }

        //Hent alle personer fra databasen v.h.a. SQL
        try {
            Statement stat = conn.createStatement();

            //Læser fra database alt data fra databasetabellen Product.   
            ResultSet rs = stat.executeQuery("Product_ID, Name, Image, Price, Stock, ProductCategory");

            //Løber data igennem via en løkke og skriver det up.    
            while (rs.next()) {
                allProducts.add(new Product(rs.getInt("Product_ID"), rs.getString("name"), Tools.convertBufferedImageToFxImage(ImageIO.read(rs.getBinaryStream("Image"))), rs.getFloat("Price"), rs.getInt("Stock"), ProductCategory.valueOf(rs.getString("ProductCategory"))));
            }
            rs.close();
        } catch (SQLException e) {
            //Skrive fejlhåndtering her
            System.out.println("DB Error: " + e.getMessage());
        }
        //Luk forbindelsen til databasen
        
        return allProducts;
        
        conn.close();   
         */
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

    //------------------------------------
    //---------- make purcchase ----------
    //------------------------------------
    public void makePurchase(Cart _cart, User _user) throws SQLException, Exception {
        Connection conn = null;
        Class.forName("org.sqlite.JDBC");

        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            System.out.println("\n Database error (make purchase (connection): " + e.getMessage() + "\n");
        }

        //create purchase
        String sql = "INSERT INTO Purchases VALUES(?, '" + _user.getUser_ID() + "', '" + LocalDateTime.now().toString() + "');";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("\n Database error (make purcahse (create purchase)): " + e.getMessage() + "\n");
        }

        //get purchase ID
        int purchase_ID = 0;

        try {
            Statement stat = conn.createStatement();

            ResultSet rs = stat.executeQuery("SELECT MAX(Purchase_ID) FROM Purchases;");

            purchase_ID = rs.getInt("MAX(Purchase_ID)");

        } catch (SQLException e) {
            System.out.println("\n Database error (make purcahse (get purcahse ID)): " + e.getMessage() + "\n");
        }

        //insert products
        for (Product p : _cart.getProductsAsMap().keySet()) {
            sql = "INSERT INTO PurchasedProducts VALUES('" + purchase_ID + "', '" + p.getItem_ID() + "', '" + _cart.getProductsAsMap().get(p) + "');";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println("\n Database error (make purcahse (insert products)): " + e.getMessage() + "\n");
            }
        }
        conn.close();
    }
}
