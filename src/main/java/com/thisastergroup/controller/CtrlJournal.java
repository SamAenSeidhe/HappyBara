package com.thisastergroup.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;

import com.thisastergroup.model.JournalEntry;

public class CtrlJournal {
    // Nodes and elements from the Journal.fxml
    @FXML
    private AnchorPane apHeader;
    @FXML
    private FlowPane scenePane;
    @FXML
    private ScrollPane spEntries;
    @FXML
    private VBox vbEntries;
    @FXML
    private Button btnNewEntry;
    @FXML
    private TextField txtFieldEntry;
    @FXML
    private Button btnSend;
    @FXML
    private ImageView icoNewEntry;

    // Variables to store and handle the journal entries
    private JournalEntry jEntry;
    private String date;
    private String user;
    private String type;
    private String journalEntry;
    private ArrayList<JournalEntry> entries = new ArrayList<>();
    private ArrayList<Text> textEntries = new ArrayList<>();

    private Image img = new Image("file:src/main/resources/Icon.png");

    public void initialize() {
        this.date = "today";

        // Gets the user logged in and the current date to filter the DB
        getEntriesFromDB(this.date);

        // Read the DB and create the journal entries elements in the scene
        loadEntries();

        // Resize the scene based on the window size
        apHeader.prefWidthProperty().bind(scenePane.widthProperty());
        scenePane.widthProperty().addListener((obs, oldVal, newVal) -> {
            spEntries.setPrefWidth(newVal.doubleValue() - 20);
            wrapEntries();
        });
        scenePane.heightProperty().addListener(
                (obs, oldVal, newVal) -> spEntries.setPrefHeight(newVal.doubleValue() - apHeader.getHeight() - 20));

        // Move the buttons to the end of the Journal
        moveButtons();

        icoNewEntry.setImage(img);
    }

    /**
     * Function to load the journal entries from the JournalEntry object to the GUI
     * 
     * It loads the entries from the object created and it adds them to the scene
     * creating Text elements in the VBox assigned
     * 
     * @see com.thisastergroup.model.JournalEntry
     */
    public void loadEntries() {

        // TODO Create the journal entries elements in the scene
        for (int i = 0; i < 6; i++) {
            jEntry = new JournalEntry(this.user, this.journalEntry, this.type, this.date);
            entries.add(jEntry);
        }

        for (JournalEntry entry : entries) {

            Text txtEntry = new Text();
            txtEntry.setLineSpacing(5);
            // txtEntry.setStyle("-fx-font-size: 20px; -fx-fill: #000000;");
            txtEntry.setText(entry.getJournalEntry());
            txtEntry.setWrappingWidth(spEntries.getPrefWidth() - 20);

            txtEntry.getStyleClass().add("txtEntry");
            textEntries.add(txtEntry);

            if (entry.getType().equals("Dump")) {
                // TODO hide the dump lines
                txtEntry.getStyleClass().add("txtEntry-dump");
            }

        }

        // Add the journal entries to the scene
        for (Text txt : textEntries) {
            vbEntries.getChildren().add(txt);
            vbEntries.getChildren().remove(btnNewEntry);
            vbEntries.getChildren().add(btnNewEntry);
        }
    }

    /*
     * Function to read the journal entries from the database
     * 
     * It reads the entries from the database filtered by the user name and the date
     * it was created.
     */
    public void getEntriesFromDB(String date) {
        // TODO read the DB, in the meantime this is the mockup

        this.date = "2024-06-01";
        this.user = "User";
        this.type = "Manual";
        this.journalEntry = "To be or not to be that is the question. Whether 'tis nobler in the mind to suffer the slings and arrows of outrageous fortune, or to take arms against a sea of troubles and by opposing end them.";
    }

    /**
     * Function to wrap the journal entries
     * 
     * It wraps the journal entries to the width of the scene to avoid overflow
     * based on the listener of the scroll pane
     */
    public void wrapEntries() {
        for (Text txt : textEntries) {
            txt.setWrappingWidth(spEntries.getPrefWidth() - 20);
        }
    }

    /**
     * Function to enable the write entry
     * 
     * It creates a text field to write a new journal entry
     * 
     * @see sendEntry()
     */
    public void enableWriteEntry() {
        System.out.println("Writing entry: ");
        moveButtons();
        txtFieldEntry.disableProperty().set(false);
        txtFieldEntry.opacityProperty().set(1);
        btnSend.disableProperty().set(false);
        btnSend.opacityProperty().set(1);
        btnNewEntry.disableProperty().set(true);
        btnNewEntry.opacityProperty().set(0);
        vbEntries.getChildren().remove(btnNewEntry);
        vbEntries.getChildren().add(btnNewEntry);
    }

    /**
     * Function to write a new journal entry
     * 
     * It creates a new journal entry in the database and adds it to the scene as a
     * Text element
     */
    public void sendEntry() {

        System.out.println("Sending entry: " + txtFieldEntry.getText());
        txtFieldEntry.disableProperty().set(true);
        txtFieldEntry.opacityProperty().set(0);
        btnSend.disableProperty().set(true);
        btnSend.opacityProperty().set(0);
        if (txtFieldEntry.getText().isEmpty()) {
            System.out.println("Entry is empty");
            moveButtons();
            return;
        }

        jEntry = new JournalEntry(this.user, this.txtFieldEntry.getText(), "Manual", this.date);
        // TODO Send new entry to the DB (jEntrty) also an if statement in case the
        // entry is empty

        Text txtEntry = new Text();
        txtEntry.setLineSpacing(5);
        // txtEntry.setStyle("-fx-font-size: 20px; -fx-fill: #000000;");
        txtEntry.setText(jEntry.getJournalEntry());
        txtEntry.setWrappingWidth(spEntries.getPrefWidth() - 20);
        txtEntry.getStyleClass().add("txtEntry-dump");
        textEntries.add(txtEntry);
        vbEntries.getChildren().add(txtEntry);
        moveButtons();
    }

    /**
     * Function to move all buttons to the end of the VBox
     * 
     * @see Journal.fxml
     */
    public void moveButtons() {
        vbEntries.getChildren().remove(btnNewEntry);
        vbEntries.getChildren().add(btnNewEntry);
        vbEntries.getChildren().remove(txtFieldEntry);
        txtFieldEntry.clear();
        vbEntries.getChildren().add(txtFieldEntry);
        vbEntries.getChildren().remove(btnSend);
        vbEntries.getChildren().add(btnSend);

        btnNewEntry.disableProperty().set(false);
        btnNewEntry.opacityProperty().set(1);
    }

}