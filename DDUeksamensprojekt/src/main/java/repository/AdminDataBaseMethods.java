/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import com.mycompany.ddueksamensprojekt.Product;
import Classes.ProductCategory;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.imageio.ImageIO;

/**
 *
 * @author chris
 */
public class AdminDataBaseMethods {

    private final String connectionString = "jdbc:sqlite:Database.db";
    private static final String staticConnectionString = "jdbc:sqlite:Database.db";

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

        String sql = "INSERT INTO Products VALUES (?, '" + _product.getName() + "', ?, "
                + "'" + _product.getPrice() + "', '" + _product.getStock() + "', "
                + "'" + _product.getProductCategory().toString() + "' );";

        FileInputStream fis = new FileInputStream(_imageFile);

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setBinaryStream(2, fis, fis.available());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("\n Database error (insert image (insert image): " + e.getMessage() + "\n");

            e.printStackTrace();
        }

        conn.close();
    }

    //----------------------------------------------------
    //---------- Bind image to product category ----------
    //----------------------------------------------------
    public static void bindImageToProductCategory(ProductCategory _ProductCategory, File _imageFile) throws SQLException, Exception {
        Connection conn = null;
        Class.forName("org.sqlite.JDBC");

        try {
            conn = DriverManager.getConnection(staticConnectionString);
        } catch (SQLException e) {
            System.out.println("\n Database error (Bind image to product category (connection)): " + e.getMessage() + "\n");
        }

        String sql = "UPDATE ProductCategorys SET image = ? "
                + "WHERE (category = '" + _ProductCategory.toString() + "');";

        FileInputStream fis = new FileInputStream(_imageFile);

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setBinaryStream(1, fis, fis.available());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("\n Database error (Bind image to product category (bind image)): " + e.getMessage() + "\n");

            e.printStackTrace();
        }
        conn.close();
    }

    //-------------------------------------------------------
    //---------- Bind image to sub product product ----------
    //-------------------------------------------------------
    //--------------------------------------------------------
    //---------- Set sub/- product categorys images ----------
    //--------------------------------------------------------
    public static void setProductCategorysImages() throws SQLException, Exception {
        Connection conn = null;
        Class.forName("org.sqlite.JDBC");

        try {
            conn = DriverManager.getConnection(staticConnectionString);
        } catch (SQLException e) {
            System.out.println("\n Database error (set product categorys images (connection)): " + e.getMessage() + "\n");
        }

        //categorys
        for (ProductCategory pc : ProductCategory.values()) {
            try {
                Statement stat = conn.createStatement();

                ResultSet rs = stat.executeQuery("SELECT image FROM ProductCategorys "
                        + "WHERE Category =  ('" + pc.toString() + "') ");

                //check if the query found a matching image
                if (rs.getBytes("image") != null) {
                    byte[] imgBytes = rs.getBytes("image");
                    ByteArrayInputStream bis = new ByteArrayInputStream(imgBytes);
                    BufferedImage bImage = ImageIO.read(bis);

                    pc.setImage(Tools.convertBufferedImageToFxImage(bImage));
                } else {
                    System.out.println("\nfound no image for the produt category: " + pc.toString() + "\n");
                }
            } catch (SQLException e) {
                System.out.println("\n Database error (set product categorys images (product category images)): " + e.getMessage() + "\n");
            }
        }
        conn.close();
    }
}
