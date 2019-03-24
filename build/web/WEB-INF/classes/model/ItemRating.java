/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

/**
 *
 * @author Jachin
 */
public class ItemRating implements Serializable{
    
    private Item item;
    private int rating;
    private boolean madeIt = false;
    private int userID;

    
    public ItemRating() {
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public boolean getMadeIt() {
        return madeIt;
    }

    public void setMadeIt(boolean madeIt) {
        this.madeIt = madeIt;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
    
    
    
    
}
