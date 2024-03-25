package com.thisastergroup.Model;

//User class to store user information
//This class is used to create a user object with the information of the user that will be handled in the DB
/* 
 * Needs to create a method to check the password requirements (regex)
 * Needs to create a method to check the email format (regex)
 * Needs to check the age format (int)
 * We will use Male, Female, Non binary or Other (idk if there are labels that aren't under that umbrella, perhaps genderfluid?) as gender values (Deployable list in the view)
 * I would change the constructor to fit the previous reqs as well so it also covers scenarios where the user is created the first time
*/

public class User{

    private String username;
    private String password;
    private String email;
    private String gender;
    private int age;
    private String country;

    /**
     * Constructor for User
     * 
     * Creates a User with the username, password, email, gender, age and country
     * @param username the username of the user. It checks availability in the db
     * @param password the password of the user. It goes through a regex to check the format
     * @param email the email of the user. It goes through a regex to check the format. And is also checked for availability in the db
     * @param gender we use Male, Female, Non binary or Other as values
     * @param age the age of the user as an int  
     * @param country the country of the user as a string
     * 
     */
    public User(String username, String password, String email, String gender, int age, String country){
        this.username = username;
        this.password = password;
        this.email = email;
        this.gender = gender;
        this.age = age;
        this.country = country;
    }

    /**
     * @return String return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return String return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return String return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return String return the gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * @return int return the age
     */
    public int getAge() {
        return age;
    }

    /**
     * @param age the age to set
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * @return String return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

}