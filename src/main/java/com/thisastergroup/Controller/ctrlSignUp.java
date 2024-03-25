package com.thisastergroup.Controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class ctrlSignUp{
   
    @SuppressWarnings("rawtypes")
    @FXML private ComboBox SUGender;

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
     */
    public void SignUp(ActionEvent event){
        System.out.println("SignUp");
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
