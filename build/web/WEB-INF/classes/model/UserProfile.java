/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author Jachin
 */
public class UserProfile {

    private int userID;
    ArrayList<ItemRating> ItemRatings = new ArrayList<>();

    public UserProfile() {
    }
    
    
    public void addItem(ItemRating ir){
        boolean foundFlag = false;
        
        for(ItemRating current : ItemRatings) {
            if(current.getItem().getItemCode() == ir.getItem().getItemCode()){
                foundFlag = true;
            }
            
        }
        
        if(foundFlag == false){
        ItemRatings.add(ir);
        }
        
    }

    public ArrayList<ItemRating> getItemRatings() {
        return ItemRatings;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
    

    public int getUserID() {
        return userID;
    }
    
    public void removeItem(ItemRating item){
        ItemRatings.remove(item);
    }

    
}
