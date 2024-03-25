package com.thisastergroup.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;

import com.thisastergroup.Model.User;
import com.thisastergroup.Model.SQLUserMethods;

public class ctrlSignUp implements Initializable{
   
    
    @FXML
    private TextField SUUsername;
    @FXML 
    private PasswordField SUPassword;
    @FXML
    private TextField SUEmail;
    @FXML
    private TextField SUAge;
    @FXML
    private ComboBox<String> SUGender;
    @FXML
    private TextField SUCountry;
    @FXML
    private Label msgSignUp;

    private String[] genderOptions = {"Male", "Female", "Enby", "Other"};


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {        
        SUGender.getItems().addAll(genderOptions);
    }
    
    /*
     * Creates a new user in the db
     * 
     * It tries to create an User object with the information used in the UI. If it fails it should show an error message.
     * If the User object is created it checks if the username and email already exists in the DB. And also shows a message.
     * Then it creates the user in the DB with the information provided. 
     * Lastly it shows the main window or the login window.
     * 
     * @see User
     * @see ctrlLogin
     * @see SQLUserMethods
     */
    public void SignUp(ActionEvent event){
        try{
            User us = new User(SUUsername.getText(), SUPassword.getText(), SUEmail.getText(), SUGender.getValue().toString(), Integer.parseInt(SUAge.getText()), SUCountry.getText());
            try{
                SQLUserMethods usSQL = new SQLUserMethods();
                usSQL.signUp(us);
            } catch (Exception e){
                msgSignUp.setText("Error creating user in the DB: ");
                System.out.println(e.getMessage());
            }
            
            msgSignUp.setText("User created");
        } catch (Exception e){
            msgSignUp.setText("Error creating user");
            System.out.println(e.getMessage());
        }
    }

    /*
     * Handles the go back to login hyperlink event
     * It shows the login window
     * 
     * @see ctrlLogin
     * @throws IOException
     */
    public void toLogin(ActionEvent event) throws IOException{
        Stage stage;
        Parent root;
        Scene scene;

        root = FXMLLoader.load(getClass().getResource("..//View//Login.fxml"));
        scene = new Scene(root, 500, 480);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();     
    }

    
    
}
