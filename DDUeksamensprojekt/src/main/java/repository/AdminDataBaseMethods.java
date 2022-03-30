/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import Classes.Product;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author chris
 */
public class AdminDataBaseMethods {

    private final String connectionString = "jdbc:sqlite:Database.db";

    //------------------------------------
    //---------- Create product ----------
    //------------------------------------
    public void createProduct(Product _product, File _imageFile) throws SQLException, Exception {
        Connection conn = null;
        Class.forName("org.sqlite.JDBC");

        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            System.out.println("\n Database error (insert image (connection)): " + e.getMessage() + "\n");
        }

        String sql = "INSERT INTO Images VALUES (?, '" + _product.getName() + "', ?, "
                + "'" + _product.getPrice() + "', '" + _product.getStock() + "', "
                + "'" + _product.getSubProductCategory().toString() + "' );";

        FileInputStream fis = new FileInputStream(_imageFile);

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setBinaryStream(2, fis, fis.available());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("\n Database error (insert image (insert image): " + e.getMessage() + "\n");
            
            e.printStackTrace();
        }
    }
}
