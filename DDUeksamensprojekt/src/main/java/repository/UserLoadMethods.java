/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import Classes.Cart;
import Classes.Coupon;
import Classes.CreditCard;
import Classes.User;
import Classes.UserType;
import Classes.Wallet;
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
                    + "WHERE savedShoppingCart_ID = ('" + _savedCart_ID + "'));");

            ArrayList<Product> products = StoreLoadMethods.loadProducts(rs);

            //get the amounts
            for (Product p : products) {
                rs = stat.executeQuery("SELECT amount FROM SavedShoppingCartsProducts "
                        + "WHERE product_ID = ('" + p.getItem_ID() + "') AND savedShoppingCart_ID = ('" + _savedCart_ID + "');");
                //set the products whit amounts
                orderedProducts.put(p, rs.getInt("amount"));
            }
        } catch (SQLException e) {
            System.out.println("\n Database error (load saved carts products (get carts): " + e.getMessage() + "\n");
        }

        return orderedProducts;
    }

    //--------------------------------------
    //---------- load creditcards ----------
    //--------------------------------------
    public static ArrayList<Cart> loadCarts(ResultSet rs, Connection conn) throws SQLException, Exception {
        ArrayList<Cart> carts = new ArrayList<>();

        while (rs.next()) {
            carts.add(new Cart(rs.getInt("savedShoppingCart_ID"),
                    UserLoadMethods.loadSavedCartsProducts(conn, rs.getInt("savedShoppingCart_ID"))));
        }

        return carts;
    }

    //--------------------------------------
    //---------- load creditcards ----------
    //--------------------------------------
    public static ArrayList<CreditCard> loadCreditCards(ResultSet rs) throws SQLException, Exception {
        ArrayList<CreditCard> creditCards = new ArrayList<>();

        while (rs.next()) {
            creditCards.add(new CreditCard(rs.getInt("creditCard_ID"),
                    rs.getString("experationDate"),
                    rs.getString("cardNumber"), rs.getString("cvv"), rs.getString("nameOfCardHolder"),
                    rs.getString("nameOfCard")));
        }

        return creditCards;
    }

    public static User loadUser(ResultSet rs, Connection conn, Statement stat) throws SQLException, Exception {

        User user = new User(rs.getInt("user_ID"), rs.getString("name"), rs.getString("email"),
                new Wallet(rs.getInt("wallet_ID"), rs.getInt("funds"), null, null), null, null, UserType.valueOf(rs.getString("userType")));

        //get wallet credit cards
        try {
            rs = stat.executeQuery("SELECT * FROM Creditcards "
                    + "WHERE wallet_ID = ('" + user.getWallet().getWallet_ID() + "')");

            user.getWallet().setCreditCards(UserLoadMethods.loadCreditCards(rs));

        } catch (SQLException e) {
            System.out.println("\n Database error (get logged ind user (get wallet creditcards)): " + e.getMessage() + "\n");
        }

        //get wallet coupons
        try {
            rs = stat.executeQuery("SELECT * FROM Coupons "
                    + "WHERE wallet_ID = ('" + user.getWallet().getWallet_ID() + "') ");

            ArrayList<Coupon> coupons = new ArrayList<>();

            while (rs.next()) {
                coupons.add(new Coupon());
            }

            user.getWallet().setCupons(coupons);

        } catch (SQLException e) {
            System.out.println("\n Database error (get logged ind user (get wallet coupons)): " + e.getMessage() + "\n");
        }

        //get favorites
        try {

            rs = stat.executeQuery("SELECT * FROM products "
                    + "WHERE product_ID IN(SELECT product_ID FROM Favorites "
                    + "WHERE user_ID = ('" + user.getUser_ID() + "'))");

            user.setFavorites(StoreLoadMethods.loadProducts(rs));

        } catch (SQLException e) {
            System.out.println("\n Database error (get logged ind user (get user favorites)): " + e.getMessage() + "\n");
        }

        //get saved shoppingCarts
        try {
            ArrayList<Cart> savedCarts = new ArrayList<>();

            rs = stat.executeQuery("SELECT savedShoppingCart_ID FROM savedShoppingCarts "
                    + "WHERE user_ID = ('" + user.getUser_ID() + "') ;");

            savedCarts = UserLoadMethods.loadCarts(rs, conn);

            user.setSavedCarts(savedCarts);
        } catch (SQLException e) {
            System.out.println("\n Database error (get logged ind user (get saved carts)): " + e.getMessage() + "\n");
        }
        return user;
    }
}
