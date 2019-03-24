package dbUtil;


import model.Item;
import model.User;



/**
 *
 * @author nanajjar
 * ITIS 4166 Assignment 4
 * 
 */

public class DbSetup {

    // running this file creates the PRODUCT and USER tables
    // and adds a test entry in each table
    public static void main(String[] args) {

        ItemDB.createItemTable();

        Item testItem = new Item(1, "itemName", "catalogCategory",
                 "description", "imageUrl");
        ItemDB.addItem(testItem);

        UserDB uDB = new UserDB();
        uDB.createUserTable();

        User testUser = new User(0001, "firstName", "lastName", "email",
                "address1", "address2", "city", "state",
                "zipcode", "country","a", "b", "c", "d", false);
        uDB.addUser(testUser);

    }

}
