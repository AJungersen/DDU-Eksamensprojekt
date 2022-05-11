/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import com.mycompany.ddueksamensprojekt.Product;
import java.sql.*;
import Classes.*;
import com.mycompany.ddueksamensprojekt.App;
import static com.mycompany.ddueksamensprojekt.App.getLoggedInUser;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 *
 * @author chris
 */
public class UserDatabaseMethods {

    private final String connectionString = "jdbc:sqlite:Database.db";

    //----------------------------------------------
    //---------- check for matching email ----------
    //----------------------------------------------
    public boolean checkForMatchingEmail(String _email) throws SQLException, Exception {
        String databaseEmail = "";
        _email = _email.toLowerCase();

        Connection conn = null;
        Class.forName("org.sqlite.JDBC");

        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            System.out.println("\n Database error (check for matching email (connection): " + e.getMessage() + "\n");
        }

        try {
            Statement stat = conn.createStatement();

            ResultSet rs = stat.executeQuery("select email from Users WHERE email = ('" + _email + "');");

            rs.next();

            databaseEmail = rs.getString("email");

            rs.close();
        } catch (SQLException e) {
            //Skrive fejlhåndtering her
            System.out.println("\n Database error (check for matching email (resultset): " + e.getMessage() + "\n");
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

            ResultSet rs = stat.executeQuery("select Password from Users WHERE Email = ('" + _email + "');");

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

        //create wallet
        sql = "INSERT INTO Wallets (funds) VALUES ('" + 0 + "')";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("\n Database error (create user (create wallet)): " + e.getMessage() + "\n");
        }

        //get id
        int wallet_ID = -1;
        try {
            Statement stat = conn.createStatement();

            ResultSet rs = stat.executeQuery("SELECT MAX(Wallet_ID) FROM wallets;");

            wallet_ID = rs.getInt("MAX(Wallet_ID)");

        } catch (SQLException e) {
            System.out.println("\n Database error (create user (get wallet_ID)): " + e.getMessage() + "\n");
        }

        sql = "INSERT INTO Users(wallet_ID, name, email, password) "
                + "VALUES('" + wallet_ID + "', '" + _newUser.getName() + "','" + _newUser.getEmail() + "', '" + _password + "');";

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

        //get user info
        try {
            Statement stat = conn.createStatement();

            ResultSet rs = stat.executeQuery("SELECT *, Wallets.funds FROM Users "
                    + "INNER JOIN Wallets ON Users.wallet_ID = Wallets.wallet_ID "
                    + "WHERE email = ('" + _email + "');");

            rs.next();

            loggedInUser = new User(rs.getInt("user_ID"), rs.getString("name"), rs.getString("email"),
                    new Wallet(rs.getInt("wallet_ID"), rs.getInt("funds"), null, null), null, null);

            rs.close();

            //get wallet credit cards
            try {
                rs = stat.executeQuery("SELECT * FROM Creditcards "
                        + "WHERE wallet_ID = ('" + loggedInUser.getWallet().getWallet_ID() + "')");

                loggedInUser.getWallet().setCreditCards(UserLoadMethods.loadCreditCards(rs));

            } catch (SQLException e) {
                System.out.println("\n Database error (get logged ind user (get wallet creditcards)): " + e.getMessage() + "\n");
            }

            //get wallet coupons
            try {
                rs = stat.executeQuery("SELEC * FROM Coupons "
                        + "WHERE wallet_ID = ('" + loggedInUser.getWallet().getWallet_ID() + "') ");

                ArrayList<Coupon> coupons = new ArrayList<>();

                while (rs.next()) {
                    coupons.add(new Coupon());
                }

                loggedInUser.getWallet().setCupons(coupons);

            } catch (SQLException e) {
                System.out.println("\n Database error (get logged ind user (get wallet coupons)): " + e.getMessage() + "\n");
            }

            //get favorites
            try {

                rs = stat.executeQuery("SELECT * FROM products "
                        + "WHERE product_ID IN(SELECT product_ID FROM Favorites "
                        + "WHERE user_ID = ('" + loggedInUser.getUser_ID() + "'))");

                /*ArrayList<Product> favorites = new ArrayList<>();
                
                while (rs.next()) {
                    byte[] imgBytes = rs.getBytes("image");
                    ByteArrayInputStream bis = new ByteArrayInputStream(imgBytes);
                    BufferedImage bImage = ImageIO.read(bis);

                    favorites.add(new Product(rs.getInt("product_ID"), rs.getString("name"),
                            Tools.convertBufferedImageToFxImage(bImage), rs.getInt("price"),
                            rs.getInt("stock"), ProductCategory.valueOf(rs.getString("ProductCategory"))));
                }*/
                loggedInUser.setFavorites(StoreLoadMethods.loadProducts(rs));

            } catch (SQLException e) {
                System.out.println("\n Database error (get logged ind user (get user favorites)): " + e.getMessage() + "\n");
            }

            //get saved shoppingCarts
            try {
                ArrayList<Cart> savedCarts = new ArrayList<>();

                rs = stat.executeQuery("SELECT savedShoppingCart_ID FROM savedShoppingCarts "
                        + "WHERE user_ID = ('" + loggedInUser.getUser_ID() + "') ;");

                while (rs.next()) {
                    savedCarts.add(new Cart(loggedInUser,
                            UserLoadMethods.loadSavedCartsProducts(conn, rs.getInt("savedShoppingCart_ID"))));
                }

                loggedInUser.setSavedCarts(savedCarts);
            } catch (SQLException e) {
                System.out.println("\n Database error (get logged ind user (get saved carts)): " + e.getMessage() + "\n");
            }

        } catch (SQLException e) {
            System.out.println("\n Database error (get logged ind user (result set get user)): " + e.getMessage() + "\n");
        }
        conn.close();
        return loggedInUser;
    }

    //--------------------------------------
    //---------- update user info ----------
    //--------------------------------------
    public void updateUserInfo(User _user) throws Exception, SQLException {
        Connection conn = null;
        Class.forName("org.sqlite.JDBC");

        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            System.out.println("\n Database error (update user info (connection)): " + e.getMessage() + "\n");
        }

        String sql = "UPDATE Users SET name = '" + _user.getName() + "',"
                + " email = '" + _user.getEmail() + "';";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("\n Database error (update user info (update info)): " + e.getMessage() + "\n");
        }
        conn.close();
    }

    //------------------------------------------
    //---------- update user password ----------
    //------------------------------------------
    public void updateUserPassword(int _user_ID, String _password) throws Exception, SQLException {
        Connection conn = null;
        Class.forName("org.sqlite.JDBC");

        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            System.out.println("\n Database error (set user password (connection)): " + e.getMessage() + "\n");
        }

        String sql = "UPDATE Users SET password = '" + _password + "' "
                + "WHERE user_ID = ('" + _user_ID + "');";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("\n Database error (set user password (set password)): " + e.getMessage() + "\n");
        }
        conn.close();
    }

    //--------------------------------------
    //---------- save credit card ----------
    //--------------------------------------
    public ArrayList<CreditCard> getAllUsersCreditCards(int _user_ID) throws SQLException, Exception {
        ArrayList<CreditCard> creditCards = new ArrayList<>();

        Connection conn = null;
        Class.forName("org.sqlite.JDBC");

        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            System.out.println("\n Database error (get all users creditcards (connection)): " + e.getMessage() + "\n");
        }

        try {
            Statement stat = conn.createStatement();

            ResultSet rs = stat.executeQuery("SELECT * FROM creditCards "
                    + "WHERE wallet_ID = (SELECT wallet_ID FROM Users "
                    + "WHERE user_ID = ('" + _user_ID + "')) ");

            creditCards = UserLoadMethods.loadCreditCards(rs);
            System.out.println(creditCards.size());
        } catch (SQLException e) {
            System.out.println("\n Database error (get all users creditcards (get cards)): " + e.getMessage() + "\n");
        }

        conn.close();

        return creditCards;
    }

    //--------------------------------------
    //---------- save credit card ----------
    //--------------------------------------
    public void saveCreditCard(CreditCard C, User U) throws SQLException, Exception {
        SecurityMethods sm = new SecurityMethods();
        Connection conn = null;
        Class.forName("org.sqlite.JDBC");
        String sql;

        //Skab forbindelse til databasen...
        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            //Skriver fejlhåndtering her
            System.out.println("DB Error: " + e.getMessage());
        }

        sql = "INSERT INTO CreditCards VALUES(?, '" + U.getWallet().getWallet_ID() + "',"
                + "'" + C.getExperationDate() + "','" + sm.hexString((C.getCardNumber())) + "',"
                + "'" + sm.hexString((C.getCvv())) + "', '" + C.getNameOfCardHolder() + "', "
                + "'" + C.getNameOfCard() + "');";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        conn.close();
    }

    //---------------------------------
    //---------- Remove card ----------
    //---------------------------------
    public void removeCard(int _card_ID) throws SQLException, Exception {
        Connection conn = null;
        Class.forName("org.sqlite.JDBC");

        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            System.out.println("\n Database error (remove card (connection): " + e.getMessage() + "\n");
        }

        String sql = "DELETE FROM CreditCards WHERE creditCard_ID = ('" + _card_ID + "');";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("\n Database error (remove card (remove)): " + e.getMessage() + "\n");
        }
        conn.close();
    }

    //---------------------------------------
    //---------- save cart to user ----------
    //---------------------------------------
    public void saveCartToUser(Cart _cart) throws Exception, SQLException {
        Connection conn = null;
        Class.forName("org.sqlite.JDBC");

        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            System.out.println("\n Database error (save cart to user (connection): " + e.getMessage() + "\n");
        }

        //crate shopping cart
        String sql = "INSERT INTO savedShoppingCarts Values(?, '" + _cart.getUser().getUser_ID() + "')";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("\n Database error (save cart to user (create cart)): " + e.getMessage() + "\n");
        }

        //get id of created cart
        int shoppingCart_ID = 0;
        try {
            Statement stat = conn.createStatement();

            ResultSet rs = stat.executeQuery("SELECT MAX(savedShoppingCart_ID) FROM savedShoppingCarts;");

            shoppingCart_ID = rs.getInt("MAX(savedShoppingCart_ID)");

        } catch (SQLException e) {
            System.out.println("\n Database error (save cart to user (get created cart id)): " + e.getMessage() + "\n");
        }

        //insert products
        for (Product p : _cart.getProductsAsMap().keySet()) {
            sql = "INSERT INTO savedShoppingCartsProducts Values (?, '" + shoppingCart_ID + "', "
                    + "'" + p.getItem_ID() + "', '" + _cart.getProductsAsMap().get(p) + "')";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println("\n Database error (save cart to user (insert products)): " + e.getMessage() + "\n");
            }
        }

        conn.close();
    }

    //---------------------------------------
    //---------- remove saved cart ----------
    //---------------------------------------
    public void removedSavedCart(int _savedShoppingCart_ID) throws SQLException, Exception {
        Connection conn = null;
        Class.forName("org.sqlite.JDBC");

        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            System.out.println("\n Database error (remove cart (connection): " + e.getMessage() + "\n");
        }

        String sql = "DELETE FROM savedShoppingCarts WHERE savedShoppingCarts_ID = ('" + _savedShoppingCart_ID + "');"
                + "DELETE FROM savedShoppingCartsProducts WHERE savedShoppingCarts_ID = ('" + _savedShoppingCart_ID + "');";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("\n Database error (remove cart and products (remove)): " + e.getMessage() + "\n");
        }
        conn.close();
    }

    //-------------------------------------
    //---------- add to favorits ----------
    //-------------------------------------
    public void addProductToFavorits(int _product_ID, int _user_ID) throws SQLException, Exception {
        Connection conn = null;
        Class.forName("org.sqlite.JDBC");

        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            System.out.println("\n Database error (add product to favorits (connection): " + e.getMessage() + "\n");
        }

        String sql = "INSERT INTO Favorites VALUES('" + _user_ID + "' , '" + _product_ID + "');";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("\n Database error (add product to favorits (insert): " + e.getMessage() + "\n");
        }
        conn.close();
    }

    //------------------------------------------
    //---------- remove from favorits ----------
    //------------------------------------------
    public void removeProductFromFavorits(int _product_ID, int _user_ID) throws SQLException, Exception {
        Connection conn = null;
        Class.forName("org.sqlite.JDBC");

        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            System.out.println("\n Database error (remove product from favorits (connection): " + e.getMessage() + "\n");
        }

        String sql = "DELETE FROM Favorites "
                + "WHERE (user_ID = ('" + _user_ID + "') AND product_ID = ('" + _product_ID + "'));";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("\n Database error (remove product from favorits (remove): " + e.getMessage() + "\n");
        }
        conn.close();
    }
}
