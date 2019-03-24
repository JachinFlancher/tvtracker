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
public class Item implements Serializable{
    private int itemCode;
    private String itemName;
    private String category;
    private String description;
    private String rating;


    public Item()  {

    }
    
    public Item(int itemCode, String itemName, String catalogCategory,
             String description, String rating) {

        this.itemCode = itemCode;
        this.itemName = itemName;
        this.category = catalogCategory;
        this.description = description;
        this.rating = rating;
    }


    public int getItemCode() {
        return itemCode;
    }

    public void setItemCode(int itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getImageURL() {
        String imageUrl = "images/"+String.valueOf(itemCode)+".jpg";
        return imageUrl;
    }
}
