/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import com.mycompany.ddueksamensprojekt.Product;
import Classes.ProductCategory;
import Classes.Shift;
import Classes.User;
import Classes.Worker;
import com.mycompany.ddueksamensprojekt.App;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javafx.util.converter.LocalDateTimeStringConverter;
import javax.imageio.ImageIO;

/**
 *
 * @author chris
 */
public class AdminDataBaseMethods {

    private final String connectionString = "jdbc:sqlite:Database.db";
    private static final String staticConnectionString = "jdbc:sqlite:Database.db";
    private UserDatabaseMethods udm = new UserDatabaseMethods();
    private UserLoadMethods ulm = new UserLoadMethods();

    private Connection getConnection(String _errorString) throws SQLException, Exception {
        Connection conn = null;
        Class.forName("org.sqlite.JDBC");

        try {
            conn = DriverManager.getConnection(staticConnectionString);
        } catch (SQLException e) {
            System.out.println("\n Database error (" + _errorString + " (connection)): " + e.getMessage() + "\n");
        }

        return conn;
    }

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

    //------------------------------------
    //---------- delete product ----------
    //------------------------------------
    public void deleteProduct(int _product_ID) throws SQLException, Exception {
        Connection conn = null;
        Class.forName("org.sqlite.JDBC");

        try {
            conn = DriverManager.getConnection(staticConnectionString);
        } catch (SQLException e) {
            System.out.println("\n Database error (Delete product (connection)): " + e.getMessage() + "\n");
        }

        String sql = "DELETE FORM Products WHERE product_ID = ('" + _product_ID + "');";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("\n Database error (delete product (delete): " + e.getMessage() + "\n");

            e.printStackTrace();
        }
    }

    //----------------------------------------------------
    //------------ Update product information  -----------
    //----------------------------------------------------
    public void updateProductInfo(Product _product, File _imageFile) throws Exception, SQLException {
        Connection conn = null;
        Class.forName("org.sqlite.JDBC");

        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            System.out.println("\n Database error (update user info (connection)): " + e.getMessage() + "\n");
        }

        FileInputStream fis;
        try {
            String sql = "UPDATE Products SET name = '" + _product.getName() + "',"
                    + "' image = ?, " + " Price = '" + _product.getPrice() + "', Stock ='" + _product.getStock() + "', "
                    + "ProductCateogry = '" + _product.getProductCategory().toString() + "' WHERE product_ID = ('" + _product.getItem_ID() + "');";

            fis = new FileInputStream(_imageFile);

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setBinaryStream(2, fis, fis.available());
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println("\n Database error (update product info (update info)): " + e.getMessage() + "\n");
            }
        } catch (NullPointerException e) {
            //Image has not been updated 
            String sql = "UPDATE Products SET name = '" + _product.getName() + "',"
                    + " Price = '" + _product.getPrice() + "', Stock ='" + _product.getStock() + "', "
                    + "ProductCategory = '" + _product.getProductCategory().toString() + "';";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.executeUpdate();
            } catch (SQLException e1) {
                System.out.println("\n Database error (update product info (update info)): " + e1.getMessage() + "\n");
            }

            System.out.println("dont worry its fine");
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
                    System.out.println("\n found no image for the produt category: " + pc.toString() + "\n");
                }
            } catch (SQLException e) {
                System.out.println("\n Database error (set product categorys images (product category images)): " + e.getMessage() + "\n");
            }
        }
        conn.close();
    }

    //-----------------------------------
    //---------- Create worker ----------
    //-----------------------------------
    public void createWorker(Worker _newWorker, String _password) throws SQLException, Exception {
        udm.createUser(_newWorker, _password);

        Connection conn = null;
        Class.forName("org.sqlite.JDBC");

        try {
            conn = DriverManager.getConnection(staticConnectionString);
        } catch (SQLException e) {
            System.out.println("\n Database error (create worker (connection)): " + e.getMessage() + "\n");
        }

        int user_ID = -1;

        try {
            Statement stat = conn.createStatement();

            ResultSet rs = stat.executeQuery("SELECT MAX(User_ID) FROM Users;");

            user_ID = rs.getInt("MAX(User_ID)");

        } catch (SQLException e) {
            System.out.println("\n Database error (create worker (get user_ID)): " + e.getMessage() + "\n");
        }

        String sql = "INSERT INTO Workers VALUES('" + user_ID + "', '" + _newWorker.getPhoneNumber() + "');";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("\n Database error (create worker (create worker)): " + e.getMessage() + "\n");
        }

        conn.close();
    }

    //-------------------------------------
    //---------- Get all workers ----------
    //-------------------------------------
    public ArrayList<Worker> getAllWorkers() throws SQLException, Exception {
        ArrayList<Worker> allWorkers = new ArrayList<>();
        String errorString = "get all workers";

        Connection conn = getConnection(errorString);

        try {
            Statement stat = conn.createStatement();

            ResultSet rs = stat.executeQuery("SELECT *, Wallets.funds FROM Users "
                    + "INNER JOIN Wallets ON Users.wallet_ID = Wallets.wallet_ID;");

            while (rs.next()) {
                allWorkers.add(new Worker(UserLoadMethods.loadUser(rs, conn, stat), null));
            }
        } catch (SQLException e) {
            System.out.println("\n Database error ('" + errorString + "' (get user info)): " + e.getMessage() + "\n");
        }

        for (Worker w : allWorkers) {

            try {
                Statement stat = conn.createStatement();

                ResultSet rs = stat.executeQuery("SELECT phoneNumber FROM Workers WHER user_ID = ('" + "');");

                w.setPhoneNumber(rs.getString("phoneNumber"));

            } catch (Exception e) {
                System.out.println("\n Database error ('" + errorString + "' (get worker info)): " + e.getMessage() + "\n");
            }
        }

        conn.close();

        return allWorkers;
    }

    //----------------------------------
    //---------- create shift ----------
    //----------------------------------
    public void createShift(Shift _shift) throws SQLException, Exception {
        String errorString = "create shift";
        Connection conn = getConnection(errorString);

        String sql = "INSERT INTO Shifts VALUES(?, '" + _shift.getWorkerAssigned().getUser_ID() + "', '" + _shift.getDate().toString() + "',"
                + "'" + _shift.getPeriod() + "', '" + _shift.getDescreption() + "');";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("\n Database error (" + errorString + " (create shift)): " + e.getMessage() + "\n");
        }
        conn.close();
    }

    //----------------------------------------------------------
    //---------- Check if worker is on shift that day ----------
    //----------------------------------------------------------
    public boolean checkIfWotkerIsOnShiftThatDay(LocalDateTime date, int _user_ID) throws SQLException, Exception {

        String errorString = "Check if worker is on shift that day";

        Connection conn = getConnection(errorString);

        LocalDateTime dateOnJob;

        try {
            Statement stat = conn.createStatement();

            ResultSet rs = stat.executeQuery("SELECT shift_ID FROM Shifts where date = ('" + date.toString() + "') AND user_ID = ('" + _user_ID + "');");

            dateOnJob = LocalDateTime.parse(rs.getString("date"), App.getDtf());

        } catch (SQLException e) {
            //System.out.println("\n Database error (" + errorString + " (create shift)): " + e.getMessage() + "\n");

            conn.close();

            return false;
        }
        /*if (rs) {

        } else {*/
        conn.close();

        return true;
        //}
    }
}
