package com.thisastergroup.Controller;

import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.fxml.FXML;

import java.io.IOException;

import com.thisastergroup.Model.User;

public class ctrlLogin {
    @FXML

    /*
     * Handles the login button event
     * It tries to create an User object with the information used in the UI. If it fails it should show an error message.
     * If the User object is created it checks if the user exists in the DB.
     * Then it checks if the password is correct.
     * Lastly it logs in the user and shows the main window.
     * 
     */
    public void Login(ActionEvent event){
       
    }

    /*
     * Handles the sign up hyperlink event
     * It shows the sign up window
     * @see ctrlSignUp
     * @throws IOException
     */
    public void toSignUp(ActionEvent event) throws IOException{
        Stage stage;
        Parent root;
        Scene scene;

        root = FXMLLoader.load(getClass().getResource("..//View//SignUp.fxml"));
        scene = new Scene(root, 640, 480);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();     
    }
    
}