package dbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Item;
import model.ItemRating;
import model.User;
import model.UserProfile;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.errors.IntrusionException;
import org.owasp.esapi.errors.ValidationException;

/**
 *
 * @author nanajjar
 * ITIS 4166 Assignment 4
 * Class for Item DB access
 */

public class ProfileDB {



    public static boolean addItemRating(ItemRating itemRating) {

        Connection connection = DbConnection.getConnection();
        PreparedStatement ps;
        // insert the new row into the table
        try {
            ps = connection.prepareStatement("INSERT INTO userprofile VALUES (?, ?, ?, ?)");
            
            
            ps.setInt(1, itemRating.getUserID());
            ps.setInt(2, itemRating.getItem().getItemCode());
            ps.setInt(3, itemRating.getRating());
            ps.setBoolean(4, itemRating.getMadeIt());
            ps.executeUpdate();

        } catch (SQLException se) {
            if (((se.getErrorCode() == 30000) && ("23505".equals(se.getSQLState())))) {
                System.out.println("ERROR: Could not insert record into userprofile; dup primary key: " );
            } else {
                System.out.println("ERROR: Could not add row to userprofile table: "  + " " + se.getCause());
            }
            return false;
        } catch (Exception e) {
            System.out.println("ERROR: Could not add row to userprofile table: " );
            return false;
        }
        System.out.println("Added item to userprofile table: ");

        // return the  itemrating object
        return true;
    }

   public static boolean updateRating(ItemRating itemRating) {

        Connection connection = DbConnection.getConnection();
        PreparedStatement ps;
        ResultSet resultSet = null;
        String query = "";
        
        try {
            query = "UPDATE userprofile SET rating = ? WHERE userID = ? AND itemCode = ?";
            ps = connection.prepareStatement(query);
       
            ps.setInt(1, itemRating.getRating());
            ps.setInt(2, itemRating.getUserID());
            ps.setInt(3, itemRating.getItem().getItemCode());
            ps.executeUpdate();

        } catch (SQLException se) {
            if (((se.getErrorCode() == 30000) && ("23505".equals(se.getSQLState())))) {
                System.out.println("ERROR: Could not update into userprofile; dup primary key: " );
            } else {
                System.out.println("ERROR: Could not update to userprofile table: " );
            }
            return false;
        } catch (Exception e) {
            System.out.println("ERROR: Could update to userprofile table: " );
            return false;
        }
        System.out.println("Update user to userprofile rating: " );

        // itemratung updates successfully 
        return true;
         
    }
   
   
    public static boolean updateMadeIt(ItemRating itemRating) {

        Connection connection = DbConnection.getConnection();
        PreparedStatement ps;
        ResultSet resultSet = null;
        String query = "";
        
        try {
            query = "UPDATE userprofile SET madeIt = ? WHERE userID = ? AND itemCode = ?";
            ps = connection.prepareStatement(query);
            
            
           
            ps.setBoolean(1, itemRating.getMadeIt());
            ps.setInt(2, itemRating.getUserID());
            ps.setInt(3, itemRating.getItem().getItemCode());
            ps.executeUpdate();

        } catch (SQLException se) {
            if (((se.getErrorCode() == 30000) && ("23505".equals(se.getSQLState())))) {
                System.out.println("ERROR: Could not update into userprofile; dup primary key: " );
            } else {
                System.out.println("ERROR: Could not update to USER table madeit " );
            }
            return false;
        } catch (Exception e) {
            System.out.println("ERROR: Could update to userprofile table: " );
            return false;
        }
        System.out.println("Update user to userprofile madeit: " );

        // user updates successfully 
        return true;
         
    }
    
       public static boolean deleteItem(ItemRating itemRating) {

        Connection connection = DbConnection.getConnection();
        PreparedStatement ps;
        ResultSet resultSet = null;
        String query = "";
        
        try {
            query = "DELETE FROM userprofile WHERE userID = ? AND itemCode = ?";
            ps = connection.prepareStatement(query);
            
            ps.setInt(1, itemRating.getUserID());
            ps.setInt(2, itemRating.getItem().getItemCode());
            ps.executeUpdate();

        } catch (SQLException se) {
            if (((se.getErrorCode() == 30000) && ("23505".equals(se.getSQLState())))) {
                System.out.println("ERROR: Could not update into userprofile; dup primary key: " );
            } else {
                System.out.println("ERROR: Could not update to userprofile table " );
            }
            return false;
        } catch (Exception e) {
            System.out.println("ERROR: Could update to userprofile table: " );
            return false;
        }
        System.out.println("Update user to userprofile madeit: " );

        // user updates successfully 
        return true;
         
    }

       
        public static UserProfile getProfile(User user) {

        Connection connection = DbConnection.getConnection();
        PreparedStatement ps;
        int itemCode=0;
        int rating=0;
        boolean madeIt=false;
        int userID=0;
        ResultSet resultSet = null;
        UserProfile userProfile = new UserProfile();
        // insert the new row into the table
        try {

            ps = connection.prepareStatement("SELECT userID, itemCode, rating, madeIt FROM userprofile WHERE userID= ?");
            
            
            ps.setInt(1, user.getUserID());
            resultSet = ps.executeQuery();    
            while (resultSet.next()) {
                
                 try {
                    userID = ESAPI.validator().getValidInteger("userId", resultSet.getString("userID"), 0, 99999, false); 
                    itemCode = ESAPI.validator().getValidInteger("itemcode", resultSet.getString("itemCode"), 0, 99999, false);         
                    rating =  ESAPI.validator().getValidInteger("rating", resultSet.getString("rating") ,0, 200, false);
                    madeIt = resultSet.getBoolean("madeIt");
                    
                } catch (ValidationException ex) {
                    Logger.getLogger(ItemDB.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IntrusionException ex) {
                    Logger.getLogger(ItemDB.class.getName()).log(Level.SEVERE, null, ex);
                }

                Item item = ItemDB.getItem(itemCode);
                ItemRating itemRating = new ItemRating();
                itemRating.setItem(item);
                itemRating.setRating(rating);
                itemRating.setMadeIt(madeIt);
                itemRating.setUserID(userID);
                userProfile.addItem(itemRating);
                
                System.out.println("Found userprofile in userprofile table: " + itemCode);
            }
        } catch (SQLException se) {
            System.out.println("ERROR: Could not exicute SQL statement in: " + "ItemDB.getAllItems()");
            System.out.println("ERROR: Could not exicute SQL statement: " + se);
            return null;
        }
        System.out.println("Added item to userprofile table: ");

        // return the  item object
        return userProfile;
    }

  


}
