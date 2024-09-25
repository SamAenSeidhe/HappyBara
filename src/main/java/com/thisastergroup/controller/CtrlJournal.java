package com.thisastergroup.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import java.util.ArrayList;

import com.thisastergroup.Model.JournalEntry;
import com.thisastergroup.Model.SQLJournalMethods;
import com.thisastergroup.controller.CtrlLogin;
import com.thisastergroup.controller.CtrlRoom;

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
    @FXML
    private ImageView icoSendEntry;
    @FXML
    private ImageView icoDump;
    @FXML
    private Button toRoom;
    @FXML
    private Text txtDate;
    @FXML
    private DatePicker dpCalendar;
    @FXML
    private CheckBox cbDump;
    @FXML
    private ToggleButton tbtnDump;
    @FXML
    private ImageView Title;

    private GaussianBlur blur = new GaussianBlur();

    // Variables to store and handle the journal entries
    private JournalEntry jEntry;
    private String date;
    private String user;
    private String type;
    private String journalEntry;
    private ArrayList<JournalEntry> entries = new ArrayList<>();
    private ArrayList<Text> textEntries = new ArrayList<>();
    private String us_ctrljr = CtrlLogin.getUser().getUsername();

    private Image imgSend = new Image("file:src/main/resources/send.png");
    private Image imgWrite = new Image("file:src/main/resources/edit.png");
    private Image imgDump = new Image("file:src/main/resources/fire.png");
    private Image imgBackground = new Image("file:src/main/resources/Title.png");

    public void initialize() {
        // Sets the current date into the date picker
        dpCalendar.setValue(java.time.LocalDate.now());
        dpCalendar.setOnAction(e -> pickDate());
        dpCalendar.valueProperty().addListener((obs, oldVal, newVal) -> pickDate());

        // Gets the user logged in and the current date to filter the DB
        getEntriesFromDB(dpCalendar.getValue().toString());

        // Read the DB and create the journal entries elements in the scene
        loadEntries(entries);

        // Blurs or shows the dump entries in the journal
        blur.setRadius(33);
        tbtnDump.setOnAction(e -> {
            if (tbtnDump.isSelected()) {
                for (Text txt : textEntries) {
                    if (txt.getStyleClass().contains("txtEntry-dump")) {
                        txt.setEffect(null);
                    }
                }
            } else {
                for (Text txt : textEntries) {
                    if (txt.getStyleClass().contains("txtEntry-dump")) {
                        txt.setEffect(blur);
                    }
                }
            }
        });

        // Resize the scene based on the window size
        apHeader.prefWidthProperty().bind(scenePane.widthProperty());

        spEntries.setPrefWidth((Double) scenePane.widthProperty().doubleValue() - 20);
        wrapEntries();
        scenePane.widthProperty().addListener((obs, oldVal, newVal) -> {
            spEntries.setPrefWidth(newVal.doubleValue() - 20);
            wrapEntries();
        });
        scenePane.heightProperty().addListener(
                (obs, oldVal, newVal) -> spEntries.setPrefHeight(newVal.doubleValue() - apHeader.getHeight() - 20));

        txtDate.prefWidth(500);
        txtDate.minWidth(500);

        // Move the buttons to the end of the Journal
        moveButtons();

        // Icons for the buttons and other effects
        icoNewEntry.setImage(imgWrite);
        icoSendEntry.setImage(imgSend);
        icoDump.setImage(imgDump);
        Title.setImage(imgBackground);
    }

    /**
     * Function to load the journal entries from the JournalEntry object to the GUI
     * 
     * It loads the entries from the object created and it adds them to the scene
     * creating Text elements in the VBox assigned
     * 
     * @see com.thisastergroup.model.JournalEntry
     */
    public void loadEntries(ArrayList<JournalEntry> entries) {

        // Clear the VBox to avoid duplicates of the entries or entries from different
        // dates
        vbEntries.getChildren().clear();
        textEntries.clear();

        if (entries != null) {
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
                    txtEntry.setEffect(blur);
                }

            }

            // Add the journal entries to the scene
            for (Text txt : textEntries) {
                vbEntries.getChildren().add(txt);
                vbEntries.getChildren().remove(btnNewEntry);
                vbEntries.getChildren().add(btnNewEntry);
            }
        }
    }

    /*
     * Function to read the journal entries from the database
     * 
     * It reads the entries from the database filtered by the user name and the date
     * it was created.
     */
    public void getEntriesFromDB(String date) {
        System.out.println("Getting entries from DB");
        SQLJournalMethods jm_db = new SQLJournalMethods();
        // Date todayDate = new Date();
        // SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        // this.date = dateformat.format(todayDate);

        this.date = date;
        this.user = us_ctrljr;
        this.type = "Manual";
        this.journalEntry = "To be or not to be that is the question. Whether 'tis nobler in the mind to suffer the slings and arrows of outrageous fortune, or to take arms against a sea of troubles and by opposing end them.";
        jEntry = new JournalEntry(this.user, this.journalEntry, this.type, this.date);
        this.entries = jm_db.GetJournal(jEntry);
        txtDate.setText(this.date);
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
        cbDump.disableProperty().set(false);
        cbDump.opacityProperty().set(1);
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
        cbDump.disableProperty().set(true);
        cbDump.opacityProperty().set(0);
        if (txtFieldEntry.getText().isEmpty()) {
            System.out.println("Entry is empty");
            moveButtons();
            return;
        }

        if (cbDump.isSelected()) {
            jEntry = new JournalEntry(this.user, this.txtFieldEntry.getText(), "Dump", this.date);
        } else {
            jEntry = new JournalEntry(this.user, this.txtFieldEntry.getText(), "Manual", this.date);
        }
        SQLJournalMethods jm_db = new SQLJournalMethods();
        jm_db.JournalEntry(jEntry);

        // Sends the new entry to the DB and then adds it to the UI
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
        vbEntries.getChildren().remove(cbDump);
        vbEntries.getChildren().add(cbDump);

        btnNewEntry.disableProperty().set(false);
        btnNewEntry.opacityProperty().set(1);
    }

    /**
     * Function to update the date of the journal entries to the selected date
     * 
     * It updates the date of the journal entries to the selected date in the UI and
     * request the entries for it from the DB
     * 
     * @see getEntriesFromDB()
     */
    public void pickDate() {
        String stDate = dpCalendar.getValue().toString();
        txtDate.setText(stDate);

        getEntriesFromDB(stDate);
        loadEntries(entries);
        moveButtons();
    }

    public void toRoom(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        Scene scene;

        root = FXMLLoader.load(getClass().getResource("..//view//TheFinalRoom.fxml"));
        scene = new Scene(root, 640, 480);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

}