package com.thisastergroup.model;

// JournalEntry class to store journal entries
// This class is used to create a journal entry which consist of 4 data fields: username, journalEntry, type, and date (format YYYY-MM-DD HH:MI:SS)
/*
 * Same as user it needs some methods to verify the lenght of the journal entry (max 16 MB 16,777,215 characters)
 * Another method for checking the date format (YYYY-MM-DD HH:MI:SS)
 * If we plan on adding images or pictures to decorate the entry we need to add a method to check 
 * the size of the image (max  MB) and include the data type both here and in the DB
 * The Username String in the constructor should be sent only as an attribute 
 * from the User class (Username.getusername()) to avoid errors in the DB
 * The type attribute should be a dropdown list in the view with the following options: 
 * "Manual", "Sleep", "Food", "Hygene", "Mood/Mental", "Dump". Those can be changed later 
 */

public class JournalEntry {
    private String username;
    private String journalEntry;
    private String type;
    private String date;    
    
    /**
     * Constructor for JournalEntry
     * 
     * Creates a Journal entry with the Username as the identifier, the type of entry,
     * the date with the DATEIME SQL type format and the entry itself
     *  
     * @param username the username of the user (Username.getusername())
     * @param journalEntry the journal entry 
     * @param type the type of entry (Manual, Sleep, Food, Hygene, Mood/Mental, Dump)
     * @param date the date of the entry format (YYYY-MM-DD HH:MI:SS)
     *   
     */
    public JournalEntry(String username, String journalEntry, String type, String date){
        this.username = username;
        this.journalEntry = journalEntry;

        this.type = type;
        this.date = date;
    }

    /**
     * @return String return the username in the entry
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set (Username.getusername())
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return String return the journalEntry in the entry
     */
    public String getJournalEntry() {
        return journalEntry;
    }

    /**
     * @param journalEntry the journalEntry to set
     */
    public void setJournalEntry(String journalEntry) {
        this.journalEntry = journalEntry;
    }

    /**
     * @return String return the type of the entry
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set (Manual, Sleep, Food, Hygene, Mood/Mental, Dump)
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return String return the date of the entry
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date the date to set (YYYY-MM-DD HH:MI:SS)
     */
    public void setDate(String date) {
        this.date = date;
    }

}