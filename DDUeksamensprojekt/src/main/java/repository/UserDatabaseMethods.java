/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import java.sql.*;
import Classes.*;

/**
 *
 * @author chris
 */
public class UserDatabaseMethods {

    private final String connectionString = "jdbc:sqlite:C:\\Users\\chris\\Desktop\\Pogramerings projekter\\Netbeans\\DDU prøve eksamens projekt\\Database.db";

    //---------------------------------------------
    //---------- check for matching user ----------
    //---------------------------------------------
    public boolean cehckForMatchingUser(String _email) throws SQLException, Exception {
        String databaseEmail = "";
        _email = _email.toLowerCase();

        Connection conn = null;
        Class.forName("org.sqlite.JDBC");

        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            System.out.println("\n Database error (check for matching user (connection): " + e.getMessage() + "\n");
        }

        try {
            Statement stat = conn.createStatement();

            ResultSet rs = stat.executeQuery("select email from Users WHERE email = ('" + _email + "');");

            rs.next();

            databaseEmail = rs.getString("email");

            rs.close();
        } catch (SQLException e) {
            //Skrive fejlhåndtering her
            System.out.println("\n Database error (check for matching user (resultset): " + e.getMessage() + "\n");
        }

        if (_email.equalsIgnoreCase(databaseEmail)) {
            conn.close();
            return true;
        }

        conn.close();
        return false;
    }

    //--------------------------------------------------
    //---------- check for matching password -----------
    //--------------------------------------------------
    public boolean checkForMatchingPassword(String _email, String _password) throws SQLException, Exception {
        String databasePassword = "";
        _email = _email.toLowerCase();

        Connection conn = null;
        Class.forName("org.sqlite.JDBC");

        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            System.out.println("\n Database error (check for matching password (connection)): " + e.getMessage() + "\n");
        }

        try {
            Statement stat = conn.createStatement();

            ResultSet rs = stat.executeQuery("select Password from Users WHERE Username = ('" + _email + "');");

            rs.next();

            databasePassword = rs.getString("Password");

            rs.close();

        } catch (SQLException e) {
            //Skrive fejlhåndtering her
            System.out.println("\n Database error (check for matching password (result set)): " + e.getMessage() + "\n");
        }

        if (_password.equals(databasePassword)) {
            conn.close();
            return true;
        }

        conn.close();
        return false;
    }

    //---------------------------------
    //---------- create user ----------
    //---------------------------------
    public void createUser(User _newUser, String _password) throws SQLException, Exception {
        Connection conn = null;
        Class.forName("org.sqlite.JDBC");
        String sql;

        _newUser.setName(_newUser.getName().toLowerCase());

        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            System.out.println("\n Database error (create user (connection)): " + e.getMessage() + "\n");
        }

        sql = "INSERT INTO Users(name, email, password) "
                + "VALUES('" + _newUser.getName() + "','" + _newUser.getEmail() + "', '" + _password + "');";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("\n Database error (create user (insert user)): " + e.getMessage() + "\n");
        }
        conn.close();
    }

    //----------------------------------------
    //---------- get logged in user ----------
    //----------------------------------------
    public User getoggedInUser(String _email) throws SQLException, Exception {
        User loggedInUser = new User();

        _email = _email.toLowerCase();

        Connection conn = null;
        Class.forName("org.sqlite.JDBC");

        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            System.out.println("\n Database error (get logged ind user (connection)): " + e.getMessage() + "\n");
        }

        try {
            Statement stat = conn.createStatement();

            ResultSet rs = stat.executeQuery("select * from Users WHERE email = ('" + _email + "');");

            rs.next();

            loggedInUser = new User(rs.getInt("user_ID"), rs.getString("name"), rs.getString("email"), null);

            rs.close();

            try {
                rs = stat.executeQuery("SELECT creditCard_ID, cardNumber, cvv, experationDate, "
                        + "Wallets.fonds, Coupons.Coupon_ID FROM CreditCards "
                        + "INNER JOIN Wallets ON CreditCards.wallet_ID = Wallets.wallet_ID "
                        + "INNER JOIN Coupons ON Coupons.Wallet_ID = Wallets.wallet_ID "
                        + "WHERE (CreditCards.wallet_ID = ('" + rs.getInt("wallet_ID") + "') AND "
                        + "Coupons.Coupon_ID = ('" + rs.getInt("wallet_ID") + "'))");

            } catch (Exception e) {
            }

        } catch (SQLException e) {
            System.out.println("\n Database error (get logged ind user (result set get user)): " + e.getMessage() + "\n");
        }
        conn.close();
        return loggedInUser;
    }

    //---------------------------------------------------
    //---------- chekc for existing school key ----------
    //---------------------------------------------------
    public boolean checkForExistingKey(String _key) throws SQLException, Exception {
        String databasekey = "";

        Connection conn = null;
        Class.forName("org.sqlite.JDBC");

        //Skab forbindelse til databasen...
        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            //Skrive fejlhåndtering her
            System.out.println("\n Database error (check for matching key (connection)): " + e.getMessage() + "\n");
        }

        try {
            Statement stat = conn.createStatement();

            ResultSet rs = stat.executeQuery("select key from Schools WHERE key = ('" + _key + "');");

            rs.next();

            databasekey = rs.getString("key");

            rs.close();
        } catch (SQLException e) {
            //Skrive fejlhåndtering her
            System.out.println("\n Database error (check for matching key (result set)): " + e.getMessage() + "\n");
        }

        if (_key.equals(databasekey)) {
            return true;
        }

        return false;
    }
}
