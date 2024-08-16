package com.thisastergroup.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLJournalMethods extends SQLConnection{
    PreparedStatement ps = null;
    ResultSet rs = null;
    Connection conn = getConnection();

    // Method to create a new journal entry
    public void JournalEntry(JournalEntry je){
        try{
            String query = "INSERT INTO Journal (Username, Entry, Type, Date) VALUES (?, ?, ?, ?)";
            ps = conn.prepareStatement(query);
            ps.setString(1, je.getUsername());
            ps.setString(2, je.getJournalEntry());
            ps.setString(3, je.getType());
            ps.setString(4, je.getDate());
            ps.executeUpdate();
        }catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }finally{
            try{
                conn.close();
            }catch(SQLException SQLex2){
                System.err.println(SQLex2);
            }
        }
    }

    //Method to retrieve all journal entries from a user by date (requires the username)
    public JournalEntry GetJournal(JournalEntry je){
        try{
            String query = "SELECT * FROM Journal WHERE username = ? AND Date = ?";
            ps = conn.prepareStatement(query);
            ps.setString(1, je.getUsername());
            ps.setString(2, je.getDate());
            rs = ps.executeQuery();
            if(rs.next()){
                return new JournalEntry(rs.getString("Username"), rs.getString("Entry"), rs.getString("Type"), rs.getString("Date"));                   
            }
            return null;
            
        }catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }finally{
            try{
                conn.close();
            }catch(SQLException SQLex2){
                System.err.println(SQLex2);
            }
        }
        return null;
    }

    // Method to modify a journal entry (requires the username and date to be the same as the original entry)
    public void EditEntry(JournalEntry je){
        try{
            String query = "UPDATE Journal SET Entry = ?, Type = ? WHERE username = ? AND Date = ?";
            ps = conn.prepareStatement(query);
            ps.setString(1, je.getJournalEntry());
            ps.setString(2, je.getType());
            ps.setString(3, je.getUsername());
            ps.setString(4, je.getDate());
            ps.executeUpdate();
        }catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }finally{
            try{
                conn.close();
            }catch(SQLException SQLex2){
                System.err.println(SQLex2);
            }
        }
    }

    // Method to delete a journal entry
    public void DeleteEntry(String username){
        try{
            String query = "DELETE FROM Journal WHERE username = ?";
            ps = conn.prepareStatement(query);
            ps.setString(1, username);
            ps.executeUpdate();
        }catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }finally{
            try{
                conn.close();
            }catch(SQLException SQLex2){
                System.err.println(SQLex2);
            }
        }
    }

}