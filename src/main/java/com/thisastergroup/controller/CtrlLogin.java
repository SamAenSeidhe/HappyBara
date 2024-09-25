package com.thisastergroup.controller;

import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.TextAlignment;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.Node;
import javafx.fxml.FXML;

import java.io.IOException;

import org.mindrot.jbcrypt.BCrypt;

import com.thisastergroup.Model.SQLUserMethods;


/*
 * Implement the methods for the Login method, it needs to check if the email exists and if the password is correct
 */

import com.thisastergroup.Model.User;

public class CtrlLogin {
    @FXML
    private Label msgLogin;
    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private FlowPane fpLogin;

    private Label lblMessage = new Label("");
    private static User us;
    private SQLUserMethods db_methods = new SQLUserMethods();

    /**
     * Handles the login button event
     * First checks if the user email exists in the DB.
     * Then it checks if the password is correct.
     * Lastly it logs in the user and shows the main window.
     * 
     */
    public void Login(ActionEvent event) throws IOException {

        if (db_methods.userExists(txtEmail.getText())) {
            try {
                us = db_methods.getUser(txtEmail.getText(), txtPassword.getText());                
                createMsg("Welcome " + us.getUsername());
                toMainScreen(event);

            } catch (Exception e) {
                createMsg("Wrong password");
                System.out.println(e);
                txtPassword.clear();
            }

        } else {
            createMsg("User doesn't exist");
        }

    }

    /**
     * Handles the sign up hyperlink event
     * It shows the sign up window
     * 
     * @see CtrlSignUp
     * 
     * @throws IOException
     */
    public void toSignUp(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        Scene scene;

        root = FXMLLoader.load(getClass().getResource("..//view//SignUp.fxml"));
        scene = new Scene(root, 640, 480);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void toMainScreen(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        Scene scene;

        root = FXMLLoader.load(getClass().getResource("..//view//TheFinalRoom.fxml"));
        scene = new Scene(root, 640, 480);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    
    /**
     * Creates a message in the UI
     * 
     * It creates a label with the message provided and adds it to the end of the
     * FlowPane
     * 
     * Use it for error messages or success messages
     * 
     * @param msg
     * 
     */
    private void createMsg(String msg) {
        fpLogin.getChildren().remove(lblMessage);
        lblMessage.setText(msg);
        lblMessage.prefHeight(50);
        lblMessage.prefWidth(250);
        lblMessage.setStyle("-fx-text-fill: #212140; -fx-font-family: 'Bookman Old Style';");
        lblMessage.setTextAlignment(TextAlignment.CENTER);

        fpLogin.getChildren().add(lblMessage);
    }

    public static User getUser() {
        return us;
    }

    public static void setUser(User user) {
        us = user;
    }
    

}