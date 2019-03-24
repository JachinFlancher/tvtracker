package dbUtil;

import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;
import org.apache.tomcat.jdbc.pool.ConnectionPool;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.errors.IntrusionException;
import org.owasp.esapi.errors.ValidationException;

/**
 *
 * @author nanajjar
 * ITIS 4166 Assignment 4
 * Class to handle the USER table.
 */

public class UserDB {

    public static void createUserTable() {

        Statement statement = DbConnection.getNewStatement();

        try {
            statement.execute("CREATE TABLE users("
                    + "userID int(11),firstName VARCHAR(50),"
                    + "lastName VARCHAR(50), email VARCHAR(50),"
                    + "addressField1 VARCHAR(50), addressField2 VARCHAR(50),"
                    + "city VARCHAR(50),state VARCHAR(50),zipCode VARCHAR(50),"
                    + "country VARCHAR(50),"
                    + "PRIMARY KEY (userID))");

            System.out.println("Created a new table: " + "USER");
        } catch (SQLException se) {
            if (se.getErrorCode() == 30000 && "X0Y32".equals(se.getSQLState())) {
                // we got the expected exception when the table is already there
            } else {
                // if the error code or SQLState is different, we have an unexpected exception
                System.out.println("ERROR: Could not create USER table: " + se);
            }
        }
    }

    public static boolean addUser(String firstName, String lastName, String email,
            String addressField1, String addressField2, String city, String state,
            String zipCode, String country, String username, String password, String salt) {

        Connection connection = DbConnection.getConnection();
        PreparedStatement insertRow;
        // insert the new row into the table
        try {
            insertRow = connection.prepareStatement("INSERT INTO user INSERT INTO user (username, password, salt, lastName, firstName, email, addressField1, addressField2, city, state, zipCode, country) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            
            insertRow.setString(1, firstName);
            insertRow.setString(2, lastName);
            insertRow.setString(3, email);
            insertRow.setString(4, addressField1);
            insertRow.setString(5, addressField2);
            insertRow.setString(6, city);
            insertRow.setString(7, state);
            insertRow.setString(8, zipCode);
            insertRow.setString(9, country);
            insertRow.setString(10, username);
            insertRow.setString(11, password);
            insertRow.setString(12, salt);
            insertRow.executeUpdate();

        } catch (SQLException se) {
            if (((se.getErrorCode() == 30000) && ("23505".equals(se.getSQLState())))) {
                System.out.println("ERROR: Could not insert record into USER; dup primary key");
            } else {
                System.out.println("ERROR: Could not add row to USER table: "  + se.getCause());
            }
            return false;
        } catch (Exception e) {
            System.out.println("ERROR: Could not add row to USER table");
            return false;
        }
        System.out.println("Added user to USER table");

        // user added successfully 
        return true;
    }

    public static boolean addUser(User user) {
        
        Connection connection = DbConnection.getConnection();
        PreparedStatement ps;
        String query;
        // insert the new row into the table
        try {
            query = "INSERT INTO user (username, password, salt, firstName, lastName, country, city, addressField1, state, zipCode, email, securityQ1) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            ps = connection.prepareStatement(query);
            
            
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getSalt());
            ps.setString(4, user.getFirstName());
            ps.setString(5, user.getLastName());
            ps.setString(6, user.getCountry());
            ps.setString(7, user.getCity());
            ps.setString(8, user.getAddressField1());
            ps.setString(9, user.getState());
            ps.setString(10, user.getZipCode());
            ps.setString(11, user.getEmail());
            ps.setString(12, user.getSecurityQ1());
            ps.executeUpdate();

        } catch (SQLException se) {
            if (((se.getErrorCode() == 30000) && ("23505".equals(se.getSQLState())))) {
                System.out.println("ERROR: Could not insert record into USER; dup primary key: " + user.getUserID());
            } else {
                System.out.println("ERROR: Could not add row to USER table: " + user.getUserID() + " " + se.getCause());
            }
            return false;
        } catch (Exception e) {
            System.out.println("ERROR: Could not add row to USER table: " + user.getUserID());
            return false;
        }
        System.out.println("Added user to USER table: " + user.getUserID());

        // user added successfully 
        return true;
    }

    public static User getUser(String username) {

        Connection connection = DbConnection.getConnection();
        PreparedStatement ps;
        ResultSet resultSet = null;

        String firstName = "";
        String lastName = "";
        String email = "";
        String addressField1 = "";
        String addressField2 = "";
        String city = "";
        String state = "";
        String zipCode = "";
        String country = "";
        String password = "";
        String salt = "";
        int userID =0;
        String query = "";
        String securityQ1="";
        boolean admin = false;
        

        
        try {
            // Find the speciic row in the table
             query = "SELECT password, salt, userID, firstName, lastName, email, addressField1, addressField2, city, state, zipCode, country, securityQ1, admin FROM user WHERE username = ?  ORDER BY userID";
           ps = connection.prepareStatement(query);
           ps.setString(1, username);
           resultSet = ps.executeQuery();
            if (!resultSet.next()) {
                System.out.println("WARNING: Could not find user in USER table: " + username);
                return null;
            } else {
                try{
                    
                
                password = ESAPI.validator().getValidInput("password", resultSet.getString("password") ,"Database", 200, false);
                salt = ESAPI.validator().getValidInput("salt", resultSet.getString("salt") ,"Database", 200, false);
                userID = ESAPI.validator().getValidInteger("userID", resultSet.getString("userID")  ,0, 99999, false);
                firstName = ESAPI.validator().getValidInput("firstname", resultSet.getString("firstName") ,"Database", 200, false);        
                lastName = ESAPI.validator().getValidInput("lastname", resultSet.getString("lastName") ,"Database", 200, false);
                email = ESAPI.validator().getValidInput("email", email = resultSet.getString("email") ,"Email", 200, false);
                addressField1 = ESAPI.validator().getValidInput("replace ME with addressField1", resultSet.getString("addressField1") ,"Database", 200, false);
                addressField2 = resultSet.getString("addressField2");
                city = ESAPI.validator().getValidInput("city", resultSet.getString("city") ,"Database", 200, false);
                state = ESAPI.validator().getValidInput("sate", resultSet.getString("state") ,"Database", 200, false);
                zipCode = ESAPI.validator().getValidInput("zipCode", resultSet.getString("zipcode") ,"Database", 200, false);
                country = ESAPI.validator().getValidInput("country", country = resultSet.getString("country") ,"Database", 200, false);
                securityQ1 = ESAPI.validator().getValidInput("securityQ1", resultSet.getString("securityQ1") ,"Database", 200, false);
                admin = resultSet.getBoolean("admin");

                
                } catch (ValidationException ex) {
                    Logger.getLogger(ItemDB.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IntrusionException ex) {
                    Logger.getLogger(ItemDB.class.getName()).log(Level.SEVERE, null, ex);
                }

                System.out.println("Found user in user table: " + username);
            }
        } catch (SQLException se) {
            System.out.println("ERROR: Could not exicute SQL statement: " + query);
            System.out.println("SQL error: " + se);
            return null;
        }

        return new User(userID, firstName, lastName, email, addressField1, addressField2, city, state, zipCode, country, username, password, salt, securityQ1, admin);
    }

    public static  ArrayList<User> getAllUsers() {
        ArrayList<User> users = new ArrayList();

        Connection connection = DbConnection.getConnection();
        PreparedStatement ps;
        ResultSet resultSet = null;

        int userID = 0;
        String firstName = "";
        String lastName = "";
        String email = "";
        String addressField1 = "";
        String addressField2 = "";
        String city = "";
        String state = "";
        String zipCode = "";
        String country = "";
        String username = "";
        String password = "";
        String salt = "";
        String securityQ1="";
        boolean admin=false;
        
        
        try {
            // Find the speciic row in the table
            
            String query =  "SELECT userID, username, salt, password, firstName, lastName, email, addressField1, addressField2, city, state, zipCode, country, securityQ1, admin FROM user ORDER BY userID";
            ps = connection.prepareStatement(query);
            resultSet = ps.executeQuery();
                   
            while (resultSet.next()) {
                
                try{
                userID = ESAPI.validator().getValidInteger("userID", resultSet.getString("userID") ,0, 99999, false);   
                firstName = ESAPI.validator().getValidInput("firstName", resultSet.getString("firstName") ,"Database", 200, false);        
                lastName = ESAPI.validator().getValidInput("lastName", resultSet.getString("lastName") ,"Database", 200, false);
                email = ESAPI.validator().getValidInput("email", email = resultSet.getString("email") ,"Email", 200, false);
                addressField1 = ESAPI.validator().getValidInput("addressField1", resultSet.getString("addressField1") ,"Database", 200, false);
                addressField2 = resultSet.getString("addressField2");
                city = ESAPI.validator().getValidInput("city", resultSet.getString("city") ,"Database", 200, false);
                state = ESAPI.validator().getValidInput("state", resultSet.getString("state") ,"Database", 200, false);
                zipCode = ESAPI.validator().getValidInput("zipcode", resultSet.getString("zipcode") ,"Database", 200, false);
                country = ESAPI.validator().getValidInput("country", resultSet.getString("country") ,"Database", 200, false);
                username = ESAPI.validator().getValidInput("username", resultSet.getString("username") ,"Database", 200, false);
                 password = ESAPI.validator().getValidInput("password", resultSet.getString("password") ,"Database", 200, false);
                salt = ESAPI.validator().getValidInput("salt", resultSet.getString("salt") ,"Database", 200, false);
                securityQ1 = ESAPI.validator().getValidInput("securityq1", resultSet.getString("securityQ1") ,"Database", 200, false);
                admin = resultSet.getBoolean("admin");
                
                } catch (ValidationException ex) {
                    Logger.getLogger(ItemDB.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IntrusionException ex) {
                    Logger.getLogger(ItemDB.class.getName()).log(Level.SEVERE, null, ex);
                }
                User user = new User(userID, firstName, lastName, email, addressField1, addressField2, city, state, zipCode, country, username, password, salt, securityQ1, admin);
                users.add(user);
                System.out.println("Found user in USER table: " + userID);
            }
        } catch (SQLException se) {
            System.out.println("ERROR: Could not exicute SQL statement in: " + "UserDB.getAllUsers()");
            System.out.println("ERROR: Could not exicute SQL statement: " + se);
            return null;
        }

        return users;
    }

        public static boolean exists(String username) {
        User p = getUser(username);
        if (p != null) {
            return true;
        } else {
            return false;
        }
    }
        
    public static boolean updateUser(User user) {

        Connection connection = DbConnection.getConnection();
        PreparedStatement ps;
        ResultSet resultSet = null;
        String query = "";
        
        try {
            query = "UPDATE user SET firstName = ?, lastName = ?, email = ?, addressField1 = ?, addressField2 = ?, city = ?, state = ?, zipCode = ?, securityQ1 = ?, country = ?, email = ? WHERE userID = ?";
            ps = connection.prepareStatement(query);
            
                      
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getLastName());
            ps.setString(4, user.getAddressField1());
            ps.setString(5, user.getAddressField2());
            ps.setString(6, user.getCity());
            ps.setString(7, user.getState());
            ps.setString(8, user.getZipCode());
            ps.setString(9, user.getSecurityQ1());
            ps.setString(10, user.getCountry());
            ps.setString(11, user.getEmail());
            ps.setInt(12, user.getUserID());
            ps.executeUpdate();

        } catch (SQLException se) {
            if (((se.getErrorCode() == 30000) && ("23505".equals(se.getSQLState())))) {
                System.out.println("ERROR: Could not update into USER; dup primary key: " + user.getUserID());
            } else {
                System.out.println("ERROR: Could not update to USER table: " + user.getUserID() + " " + se.getCause());
            }
            return false;
        } catch (Exception e) {
            System.out.println("ERROR: Could update to USER table: " + user.getUserID());
            return false;
        }
        System.out.println("Update user to USER table: " + user.getUserID());

        // user updates successfully 
        return true;
         
    }
    
    
        public static boolean updateUserPassword(User user) {

        Connection connection = DbConnection.getConnection();
        PreparedStatement ps;
        ResultSet resultSet = null;
        String query = "";
        
        try {
            query = "UPDATE user SET password = ?, salt = ? WHERE userID = ?";
            ps = connection.prepareStatement(query);
            
            
           
            ps.setString(1, user.getPassword());
            ps.setString(2, user.getSalt());
            ps.setInt(3, user.getUserID());

            ps.executeUpdate();

        } catch (SQLException se) {
            if (((se.getErrorCode() == 30000) && ("23505".equals(se.getSQLState())))) {
                System.out.println("ERROR: Could not update into USER; dup primary key: " + user.getUserID());
            } else {
                System.out.println("ERROR: Could not update to USER table: " + user.getUserID() + " " + se.getCause());
            }
            return false;
        } catch (Exception e) {
            System.out.println("ERROR: Could update to USER table: " + user.getUserID());
            return false;
        }
        System.out.println("Update user to USER table: " + user.getUserID());

        // user updates successfully 
        return true;
         
    }
        
       public static boolean checkAdmin(int userID) {

        Connection connection = DbConnection.getConnection();
        PreparedStatement ps = null;
        
        boolean Admin = false;
        String query = "SELECT * FROM Admin WHERE userID = ?";
        ResultSet rs = null;
        
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, userID);
            rs = ps.executeQuery();
            
            if(rs.next()) {
                Admin = true;
            }
            
        } catch(SQLException se) {
            System.out.println("ERROR: Could not execute SQL statement in: ");
            System.out.println("ERROR: Could not execute SQL statement: " + se);
            
        } 
        
        return Admin;
    }
        
}


