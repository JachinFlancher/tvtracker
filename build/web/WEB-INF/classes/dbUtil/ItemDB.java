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
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.errors.IntrusionException;
import org.owasp.esapi.errors.ValidationException;

/**
 *
 * @author nanajjar
 * ITIS 4166 Assignment 4
 * Class for Item DB access
 */

public class ItemDB {

    public static void createItemTable() {

        Statement statement = DbConnection.getNewStatement();

        try {
            statement.execute("CREATE TABLE item("
                    + "itemCode INT,itemName VARCHAR(50),"
                    + "catalog VARCHAR(50),"
                    + "description VARCHAR(10000),rating VARCHAR(11),"
                    + "PRIMARY KEY (itemCode))");
            System.out.println("Created a new table: " + "ITEM");
        } catch (SQLException se) {
            if (se.getErrorCode() == 30000 && "X0Y32".equals(se.getSQLState())) {
                // we got the expected exception when the table is already there
            } else {
                // if the error code or SQLState is different, we have an unexpected exception
                System.out.println("ERROR: Could not create ITEM table: " + se);
            }
        }
    }

    public static Item addItem(int itemCode, String itemName, String category,
            String description, String rating) {

        Connection connection = DbConnection.getConnection();
        PreparedStatement ps;
        // insert the new row into the table
        try {
            ps = connection.prepareStatement("INSERT INTO item VALUES (?, ?, ?, ?, ?, ?)");
            ps.setInt(1, itemCode);
            ps.setString(2, itemName);
            ps.setString(3, category);
            ps.setString(4, description);
            ps.setString(5, rating);

            ps.executeUpdate();

        } catch (SQLException se) {
            if (((se.getErrorCode() == 30000) && ("23505".equals(se.getSQLState())))) {
                System.out.println("ERROR: Could not insert record into ITEM; dup primary key: " + itemCode);
            } else {
                System.out.println("ERROR: Could not add row to ITEM table: " + itemCode + " " + se.getCause());
            }
            return null;
        } catch (Exception e) {
            System.out.println("ERROR: Could not add row to ITEM table: " + itemCode);
            return null;
        }
        System.out.println("Added item to ITEM table: " + itemCode);

        return new Item(itemCode, itemName, category,
                description, rating);
    }

    public static Item addItem(Item item) {

        Connection connection = DbConnection.getConnection();
        PreparedStatement ps;
        // insert the new row into the table
        try {
            ps = connection.prepareStatement("INSERT INTO ITEM (itemName, category, description, rating) VALUES (?, ?, ?, ?)");
            
            ps.setString(1, item.getItemName());
            ps.setString(2, item.getCategory());
            ps.setString(3, item.getDescription());
            ps.setString(4, item.getRating());

            ps.executeUpdate();

        } catch (SQLException se) {
            if (((se.getErrorCode() == 30000) && ("23505".equals(se.getSQLState())))) {
                System.out.println("ERROR: Could not insert record into ITEM; dup primary key: " + item.getItemCode());
            } else {
                System.out.println("ERROR: Could not add row to ITEM table: " + item.getItemCode() + " " + se.getCause());
            }
            return null;
        } catch (Exception e) {
            System.out.println("ERROR: Could not add row to ITEM table: " + item.getItemCode());
            return null;
        }
        System.out.println("Added item to ITEM table: " + item.getItemCode());

        // return the  item object
        return item;
    }

    public static Item getItem(int pcode) {

        Item item = new Item();
        item.setItemCode(pcode);
        Connection connection = DbConnection.getConnection();
        PreparedStatement ps;
        ResultSet resultSet = null;
 
        try {
           String query = "SELECT itemName, category, description, rating"
                + " FROM ITEM WHERE ITEM.itemCode =  ?";
           ps = connection.prepareStatement(query);
           ps.setInt(1, pcode);
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
            try {
                item.setItemName(ESAPI.validator().getValidInput("replace ME with validation context", resultSet.getString("itemName") ,"Database", 200, false));
                item.setCategory(ESAPI.validator().getValidInput("replace ME with validation context", resultSet.getString("category") ,"Database", 200, false));
                item.setDescription(ESAPI.validator().getValidInput("replace ME with validation context", resultSet.getString("description") ,"Database", 10000, false));
                item.setRating(ESAPI.validator().getValidInput("replace ME with validation context", resultSet.getString("rating") ,"Database", 200, false));
                } catch (ValidationException ex) {
                    Logger.getLogger(ItemDB.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IntrusionException ex) {
                    Logger.getLogger(ItemDB.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return item;

    }

    public static ArrayList<Item> getAllItems() {
        ArrayList<Item> items = new ArrayList<Item>();
        Connection connection = DbConnection.getConnection();
        PreparedStatement ps;
        ResultSet resultSet = null;

        int itemCode = 0;
        String itemName = "";
        String category = "";
        String description = "";
        String rating = "";

        try {
            String query =  "SELECT itemCode, itemName, category, description, rating FROM ITEM ORDER BY itemCode";
            ps = connection.prepareStatement(query);
            resultSet = ps.executeQuery();    
            while (resultSet.next()) {
                
        try {
                    itemCode = ESAPI.validator().getValidInteger("replace ME with validation context", resultSet.getString("itemCode"), 0, 99999, false);
                
                    itemName = ESAPI.validator().getValidInput("replace ME with validation context", resultSet.getString("itemName") ,"Database", 200, false);
               
                    category = ESAPI.validator().getValidInput("replace ME with validation context", resultSet.getString("category") ,"Database", 200, false);
               
                    description = ESAPI.validator().getValidInput("replace ME with validation context", resultSet.getString("description") ,"Database", 10000, false);
                            
                    rating =  ESAPI.validator().getValidInput("replace ME with validation context", resultSet.getString("rating") ,"Database", 200, false);
                } catch (ValidationException ex) {
                    Logger.getLogger(ItemDB.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IntrusionException ex) {
                    Logger.getLogger(ItemDB.class.getName()).log(Level.SEVERE, null, ex);
                }

                Item item = new Item(itemCode, itemName, category, description, rating);
                items.add(item);
                System.out.println("Found item in Item table: " + itemCode);
            }
        } catch (SQLException se) {
            System.out.println("ERROR: Could not exicute SQL statement in: " + "ItemDB.getAllItems()");
            System.out.println("ERROR: Could not exicute SQL statement: " + se);
            return null;
        }

        return items;
    }

    public static ArrayList<Item> getAllItems(String category) {
        ArrayList<Item> items = new ArrayList<Item>();
        Connection connection = DbConnection.getConnection();
        PreparedStatement ps;
        ResultSet resultSet = null;


        int itemCode = 0;
        String itemName = "";
        String description = "";
        String rating = "";

        try {
            String query = "SELECT itemCode, itemName, category, description, rating FROM ITEM WHERE category = ? ORDER BY itemCode";
            ps = connection.prepareStatement(query);
            ps.setString(1, category);
            resultSet = ps.executeQuery();  
            while (resultSet.next()) {
                
                try {
                    itemCode = ESAPI.validator().getValidInteger("replace ME with validation context", resultSet.getString("itemCode"), 0, 99999, false);
                
                    itemName = ESAPI.validator().getValidInput("replace ME with validation context", resultSet.getString("itemName") ,"Database", 200, false);
               
                    category = ESAPI.validator().getValidInput("replace ME with validation context", resultSet.getString("category") ,"Database", 200, false);
               
                    description = ESAPI.validator().getValidInput("replace ME with validation context", resultSet.getString("description") ,"Database", 10000, false);
                
                    rating =  ESAPI.validator().getValidInput("replace ME with validation context", resultSet.getString("rating") ,"Database", 200, false);
                } catch (ValidationException ex) {
                    Logger.getLogger(ItemDB.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IntrusionException ex) {
                    Logger.getLogger(ItemDB.class.getName()).log(Level.SEVERE, null, ex);
                }

                        

                Item item = new Item(itemCode, itemName, category, description, rating);
                items.add(item);
                System.out.println("Found item in ITEM table: " + itemCode);
            }
        } catch (SQLException se) {
            System.out.println("ERROR: Could not exicute SQL statement in: " + "ItemDB.getAllItems()");
            System.out.println("ERROR: Could not exicute SQL statement: " + se);
            return null;
        }

        return items;
    }

    public static List getCategories() {
        List categoryCodes = new ArrayList();
        Connection connection = DbConnection.getConnection();
        PreparedStatement ps;
        ResultSet resultSet = null;

        String catalogCategory = "";
        int i = 0;
        try {     
            String query = "SELECT DISTINCT catalogCategory FROM ITEM";
            ps = connection.prepareStatement(query);
            resultSet = ps.executeQuery();
            
            while (resultSet.next()) {
                try {
                    catalogCategory = ESAPI.validator().getValidInput("replace ME with validation context", resultSet.getString("category") ,"Database", 200, false);
                } catch (ValidationException ex) {
                    Logger.getLogger(ItemDB.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IntrusionException ex) {
                    Logger.getLogger(ItemDB.class.getName()).log(Level.SEVERE, null, ex);
                }
                categoryCodes.add(catalogCategory);
                System.out.println("Found category in ITEM table: " + catalogCategory);
            }
        } catch (SQLException se) {
            System.out.println("ERROR: Could not exicute SQL statement in: " + "ItemDB.getAllItems()");
            System.out.println("ERROR: Could not exicute SQL statement: " + se);
            return null;
        }

        return categoryCodes;
    }

    public static boolean exists(int itemCode) {
        Item p = getItem(itemCode);
        if (p != null) {
            return true;
        } else {
            return false;
        }
    }
    
    
    public static boolean updateItem(Item item) {

        Connection connection = DbConnection.getConnection();
        PreparedStatement ps;
        ResultSet resultSet = null;
        String query = "";
        
        try {
            query = "UPDATE item SET itemname = ?, category = ?,  description = ? WHERE itemCode = ?";
            ps = connection.prepareStatement(query);
            
            
           
            ps.setString(1, item.getItemName());
            ps.setString(2, item.getCategory());
            ps.setString(3, item.getDescription());
            ps.setInt(4, item.getItemCode());

            ps.executeUpdate();

        } catch (SQLException se) {
            if (((se.getErrorCode() == 30000) && ("23505".equals(se.getSQLState())))) {
                System.out.println("ERROR: Could not update into USER; dup primary key: " );
            } else {
                System.out.println("ERROR: Could not update to USER table: " +  " " + se.getCause());
            }
            return false;
        } catch (Exception e) {
            System.out.println("ERROR: Could update to USER table: " );
            return false;
        }
        System.out.println("Update user to USER table: ");

        // item updates successfully 
        return true;
         
    }
    
    
           public static boolean deleteItem(Item item) {

        Connection connection = DbConnection.getConnection();
        PreparedStatement ps;
        ResultSet resultSet = null;
        String query = "";
        
        try {
            query = "DELETE FROM item WHERE itemCode = ?";
            ps = connection.prepareStatement(query);
            
            ps.setInt(1, item.getItemCode());
            ps.executeUpdate();

        } catch (SQLException se) {
            if (((se.getErrorCode() == 30000) && ("23505".equals(se.getSQLState())))) {
                System.out.println("ERROR: Could not update into USER; dup primary key: " );
            } else {
                System.out.println("ERROR: Could not update to USER table madeit " );
            }
            return false;
        } catch (Exception e) {
            System.out.println("ERROR: Could update to USER table: " );
            return false;
        }
        System.out.println("Update user to USER madeit: " );

        // item deletes successfully 
        return true;
         
    }
}
