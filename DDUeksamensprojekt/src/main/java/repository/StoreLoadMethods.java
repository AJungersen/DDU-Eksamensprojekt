/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import com.mycompany.ddueksamensprojekt.Product;
import Classes.ProductCategory;
import java.net.ConnectException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import javax.imageio.ImageIO;

/**
 *
 * @author chris
 */
public class StoreLoadMethods {

    //-----------------------------------
    //---------- load products ----------
    //-----------------------------------
    public static ArrayList<Product> loadProducts(ResultSet _rs) throws SQLException, Exception {
        ArrayList<Product> products = new ArrayList<>();

        while (_rs.next()) {
            products.add(new Product(_rs.getInt("product_ID"), _rs.getString("name"),
                    Tools.convertBufferedImageToFxImage(ImageIO.read(_rs.getBinaryStream("image"))),
                    _rs.getInt("price"), _rs.getInt("stock"), ProductCategory.valueOf(_rs.getString("ProductCategory"))));
        }

        return products;
    }

    //------------------------------------
    //---------- load purchases ----------
    //------------------------------------
    public static HashMap<Product, Integer> loadPurchasedProducts(Connection conn, int _purchase_ID) throws SQLException, Exception {
        HashMap<Product, Integer> purchasedProducts = new HashMap<>();
        try {
            Statement stat = conn.createStatement();
            
            //get product
            ResultSet rs = stat.executeQuery("SELECT * FROM Products WHERE product_ID IN"
                    + "(SELECT product_ID FROM PurchasedShoppingCartsProducts "
                    + "WHERE purchasedShoppingCarts_ID = ('" + _purchase_ID + "'));");

            ArrayList<Product> products = StoreLoadMethods.loadProducts(rs);
            
            //get the amounts
            for (Product p : products) {
                rs = stat.executeQuery("SELECT amount FROM PurchasedShoppingCartsProducts "
                        + "WHERE product_ID = ('" + p.getItem_ID() + "');");
                //set the products whit amounts
                purchasedProducts.put(p, rs.getInt("amount"));
            }
        } catch (SQLException e) {
            System.out.println("\n Database error (load purchased products (connection): " + e.getMessage() + "\n");
        }

        return purchasedProducts;
    }
}
