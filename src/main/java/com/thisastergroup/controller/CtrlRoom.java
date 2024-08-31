package com.thisastergroup.controller;

import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.fxml.FXML;

public class CtrlRoom {
    @FXML
    private ImageView imgIndicator;
    @FXML
    private Button btnHygiene;
    @FXML
    private Button btnSleep;
    @FXML
    private Button btnFood;
    @FXML
    private ToggleButton tbJournal;
    @FXML
    private ToggleButton tbDump;
    @FXML
    private HBox hbIndicators;
    @FXML
    private AnchorPane apButtons;
    @FXML
    private ImageView imgChiguiro;

    // ImageView imageView = new
    // ImageView(getClass().getResource("../../../alert.png").toExternalForm());

    private Image chiguiFrame = new Image("file:src/main/resources/Idle.gif");

    private float indicator;
    private float hygiene;
    private float sleep;
    private float food;

    public void initialize() {

        /**
         * The following function sets an specific animation for the pet depending on
         * the indicators
         * 
         */
        imgChiguiro.setImage(chiguiFrame);
        // imageView.setFitHeight(50);
        // imageView.setFitWidth(50);
        // imgIndicator.setGraphic(imageView);

        hbIndicators.hoverProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                ShowStatus();

                imgIndicator.setOpacity(0);
                btnFood.setDisable(false);
                btnFood.setOpacity(1);
                btnSleep.setDisable(false);
                btnSleep.setOpacity(1);
                btnHygiene.setDisable(false);
                btnHygiene.setOpacity(1);

            } else {
                imgIndicator.setOpacity(1);
                btnFood.setDisable(true);
                btnFood.setOpacity(0);
                btnSleep.setDisable(true);
                btnSleep.setOpacity(0);
                btnHygiene.setDisable(true);
                btnHygiene.setOpacity(0);
            }
        });
    }

    /**
     * Calculates the average of the three indicators (Sleep, Food, Hygiene)
     * 
     * Takes values based on the information provided and time since last update
     * on each indicator to calculate the average value percentage of them and
     * /provide a visual feedback to the user
     * 
     */
    public void ShowStatus() {

        refreshIndicator();
        System.out.println("Indicator: " + indicator + " Hygiene: " + hygiene + " Sleep: " + sleep + " Food: " + food);

    }

    /**
     * Shows the journal tab and enables the dump toggler
     * 
     * When the journal tab is selected, the dump toggler is enabled and
     * the scene for the journal entries is shown to the user from the data
     * stored in the database related to the specific user
     * 
     */
    public void OpenJournal() {
        if (tbJournal.isSelected()) {
            tbDump.setDisable(false);
            tbDump.setOpacity(1);
            System.out.println("Journal is selected");
        } else {
            tbDump.setDisable(true);
            tbDump.setOpacity(0);

        }
    }

    /**
     * Toggles the dump lines within the journal entries
     * 
     * Shows or hides the dump lines within the journal entries based on the
     * user selection of the dump toggler and the type of entry of the journal
     * 
     */
    public void ToggleDump() {
        if (tbDump.isSelected()) {
            System.out.println("Dump is selected");
        } else {
            System.out.println("Dump is not selected");
        }
    }

    protected void refreshIndicator() {
        indicator = (hygiene + sleep + food) / 3;
    }

}