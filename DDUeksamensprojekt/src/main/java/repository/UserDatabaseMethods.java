/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import java.sql.*;
import Classes.*;
import static com.mycompany.ddueksamensprojekt.App.getLoggedInUser;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author chris
 */
public class UserDatabaseMethods {

    private final String connectionString = "jdbc:sqlite:Database.db";

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
    public User getLoggedInUser(String _email) throws SQLException, Exception {
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

            ResultSet rs = stat.executeQuery("SELECT *, Wallets.fonds FROM Users "
                    + "INNER JOIN Wallets ON Users.wallet_ID = Wallets.wallet_ID "
                    + "WHERE email = = ('" + _email + "');");
            
            rs.next();

            loggedInUser = new User(rs.getInt("user_ID"), rs.getString("name"), rs.getString("email"), 
                    new Wallet(rs.getInt("wallet_ID"), rs.getInt("funds"), null, null));

            rs.close();
            
            try {
                rs = stat.executeQuery("SELECT * FROM Creditcards "
                        + "WHERE wallet_ID = ('"+ loggedInUser.getWallet().getWallet_ID() +"')");
                
                ArrayList<CreditCard> creditCards = new ArrayList<>();
                
                while(rs.next()){
                    creditCards.add(new CreditCard(rs.getInt("creditCard_ID"), 
                            LocalDate.parse(rs.getString("experationDate")), 
                            rs.getString("cardNumber"), rs.getString("cvv")));
                }
                
                loggedInUser.getWallet().setCreditCards(creditCards);
                
            } catch (SQLException e) {
                System.out.println("\n Database error (get logged ind user (get wallet creditcards)): " + e.getMessage() + "\n");
            }
            
            try {
                rs = stat.executeQuery("SELEC * FROM Coupons "
                        + "WHERE wallet_ID = ('" + loggedInUser.getWallet().getWallet_ID() + "') ");
                
                ArrayList<Coupon> coupons = new ArrayList<>();
                
                while(rs.next()) {
                    coupons.add(new Coupon());
                }
                
                loggedInUser.getWallet().setCupons(coupons);

            } catch (SQLException e) {
                System.out.println("\n Database error (get logged ind user (get wallet coupons)): " + e.getMessage() + "\n");
            }

        } catch (SQLException e) {
            System.out.println("\n Database error (get logged ind user (result set get user)): " + e.getMessage() + "\n");
        }
        conn.close();
        return loggedInUser;
    }
    public Wallet getUsersWallet() throws SQLException, Exception{
        Wallet loggedInUserWallet = new Wallet();

        Connection conn = null;
        Class.forName("org.sqlite.JDBC");

        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            System.out.println("\n Database error (get logged ind users wallet (connection)): " + e.getMessage() + "\n");
        }

        try {
            Statement stat = conn.createStatement();

            ResultSet rs = stat.executeQuery("Select FROM Users WHERE user_ID = " getLoggedInUser.getUser_ID();" wallet_ID");
            
            rs.next();

            loggedInUser = new User(rs.getInt("user_ID"), rs.getString("name"), rs.getString("email"), 
                    new Wallet(rs.getInt("wallet_ID"), rs.getInt("funds"), null, null));

            rs.close();
            
            try {
                rs = stat.executeQuery("SELECT * FROM Creditcards "
                        + "WHERE wallet_ID = ('"+ loggedInUser.getWallet().getWallet_ID() +"')");
                
                ArrayList<CreditCard> creditCards = new ArrayList<>();
                
                while(rs.next()){
                    creditCards.add(new CreditCard(rs.getInt("creditCard_ID"), 
                            LocalDate.parse(rs.getString("experationDate")), 
                            rs.getString("cardNumber"), rs.getString("cvv")));
                }
                
                loggedInUser.getWallet().setCreditCards(creditCards);
                
            } catch (SQLException e) {
                System.out.println("\n Database error (get logged ind user (get wallet creditcards)): " + e.getMessage() + "\n");
            }
            
            try {
                rs = stat.executeQuery("SELEC * FROM Coupons "
                        + "WHERE wallet_ID = ('" + loggedInUser.getWallet().getWallet_ID() + "') ");
                
                ArrayList<Coupon> coupons = new ArrayList<>();
                
                while(rs.next()) {
                    coupons.add(new Coupon());
                }
                
                loggedInUser.getWallet().setCupons(coupons);

            } catch (SQLException e) {
                System.out.println("\n Database error (get logged ind user (get wallet coupons)): " + e.getMessage() + "\n");
            }

        } catch (SQLException e) {
            System.out.println("\n Database error (get logged ind user (result set get user)): " + e.getMessage() + "\n");
        }
        conn.close();
        return loggedInUser;
    }
}
