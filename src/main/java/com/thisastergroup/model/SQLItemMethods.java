package com.thisastergroup.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.thisastergroup.model.Item;

/*
 * 
 * What we need
 * 
 * The idea of the Item DB is to create a catalogue or compendium of the items available in the store.
 * This way it the shop doesn't have to be updated every time a new item is added nor be hard coded into the app. 
 * Each item has certain attributes that you can check in the class:
 * @see Item.java
 * 
 * Same as the user each item will have an indepent ID that will be used to identify it in the DB.
 * 
 * The DB also needs a relation with the users table to keep track of the items that each user has bought.
 * 
 */
public class SQLItemMethods extends SQLConnection {

    PreparedStatement ps = null;
    ResultSet rs = null;
    Connection conn = getConnection();

    /**
     * Function to read an item from the DB
     * 
     * @return item - It gets the name, clothingType, price, discount and collection of the item
     * based on the name.
     * 
     * @param item - to get the item name
     * 
     */
    public Item getItem(Item item) {
        try {
            String query = "SELECT name, clothingType, price, discount, collection FROM Items WHERE name = ?";
            ps = conn.prepareStatement(query);
            ps.setString(1, item.getName());
            rs = ps.executeQuery();
            if (rs.next()) {
                return new Item(rs.getString("name"), rs.getString("clothingType"), rs.getInt("price"),
                        rs.getDouble("discount"), rs.getString("collection"));
            }
            return null;
        } catch (SQLException exSQL) {
            System.err.println("Error: " + exSQL.getMessage());
        } finally {
            try {
                conn.close();
            } catch (SQLException ex2SQL) {
                System.err.println(ex2SQL);
            }
        }
        return null;
    }

    

}
