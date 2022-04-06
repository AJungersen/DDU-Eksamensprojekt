/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import com.mycompany.ddueksamensprojekt.Product;
import Classes.ProductCategory;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
}
