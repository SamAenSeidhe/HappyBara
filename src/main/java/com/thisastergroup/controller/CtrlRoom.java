package com.thisastergroup.controller;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.io.IOException;
import java.time.Duration;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import com.thisastergroup.Model.Interaction;
import com.thisastergroup.Model.SQLUserMethods;
import com.thisastergroup.Model.User;
import com.thisastergroup.Model.XMLHandler;
import com.thisastergroup.controller.CtrlLogin;

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
    private HBox hbIndicators;
    @FXML
    private AnchorPane apButtons;
    @FXML
    private ImageView imgChiguiro;

    // ImageView imageView = new
    // ImageView(getClass().getResource("../../../alert.png").toExternalForm());

    private Image chiguiFrame = new Image("file:src/main/resources/Idle.gif");

    private User user = CtrlLogin.getUser();
    private Interaction interaction;

    private XMLHandler xmlHandler = new XMLHandler();

    private float indicator;
    private float hygiene;
    private float sleep;
    private float food;
    private LocalDateTime lasTimefeed;
    private LocalDateTime lasTimesleep;
    private LocalDateTime lasTimeclean;
    private long secondsFeed;
    private long secondsSleep;
    private long secondsClean;
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    private SQLUserMethods sql = new SQLUserMethods();

    public void initialize() {

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            // code to run when the application is closed

            user.setLastTimes(Stringfechas());
            sql.updateLastTimes(user);

        }));
        String timecitos = sql.getlastTimes(user);
        System.out.println(timecitos);
        if (timecitos != null) {
            String[] fechas = timecitos.split("&");
            lasTimeclean = String_To_DateTime(fechas[0]);
            lasTimefeed = String_To_DateTime(fechas[1]);
            lasTimesleep = String_To_DateTime(fechas[2]);
        }

        threadclean();
        threadfeed();
        threadsleep();

        /**
         * The following function sets an specific animation for the pet depending on
         * the indicators
         * 
         */
        imgChiguiro.setImage(chiguiFrame);
        // imageView.setFitHeight(50);
        // imageView.setFitWidth(50);
        // imgIndicator.setGraphic(imageView);

        apButtons.hoverProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                ShowStatus();
                // hbIndicators.setOpacity(0);
                imgIndicator.setOpacity(1);
                btnFood.setDisable(true);
                btnFood.setOpacity(0);
                btnSleep.setDisable(true);
                btnSleep.setOpacity(0);
                btnHygiene.setDisable(true);
                btnHygiene.setOpacity(0);

            } else {
                // hbIndicators.setOpacity(0);
                imgIndicator.setOpacity(0);
                btnFood.setDisable(false);
                btnFood.setOpacity(1);
                btnSleep.setDisable(false);
                btnSleep.setOpacity(1);
                btnHygiene.setDisable(false);
                btnHygiene.setOpacity(1);
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
    public void OpenJournal(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        Scene scene;
        if (tbJournal.isSelected()) {

            System.out.println("Journal is selected");
            root = FXMLLoader.load(getClass().getResource("..//view//Journal.fxml"));
            scene = new Scene(root, 500, 480);
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
            user.setLastTimes(Stringfechas());
            sql.updateLastTimes(user);

        }
    }

    protected void refreshIndicator() {
        indicator = (hygiene + sleep + food) / 3;
    }

    // Methods to create the thread for the buttons to calculate time since last
    // execution
    public void threadfeed() {

        Thread clockThread1;
        clockThread1 = new Thread(new Runnable() {
            public void run() {

                while (true) {
                    LocalDateTime now = LocalDateTime.now();
                    if (lasTimefeed != null) {
                        Duration duration = Duration.between(lasTimefeed, now);
                        secondsFeed = duration.toSeconds();

                        if (secondsFeed <= 20)
                            btnFood.setStyle("-fx-background-color: #5E9D46;");
                        else if (secondsFeed <= 30)
                            btnFood.setStyle("-fx-background-color: #D9C830;");
                        else
                            btnFood.setStyle("-fx-background-color: #C94242;");
                        System.out.println("Seconds feed: " + secondsFeed);

                    } else {
                        lasTimefeed = now;
                    }

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        clockThread1.setDaemon(true);
        clockThread1.start(); // Iniciar el hilo
    }

    public void threadclean() {

        Thread clockThread2;
        clockThread2 = new Thread(new Runnable() {
            public void run() {

                while (true) {
                    LocalDateTime now = LocalDateTime.now();
                    if (lasTimeclean != null) {
                        Duration duration = Duration.between(lasTimeclean, now);
                        secondsClean = duration.toSeconds();

                        if (secondsClean <= 60)
                            btnHygiene.setStyle("-fx-background-color: #5E9D46;");
                        else if (secondsClean <= 70)
                            btnHygiene.setStyle("-fx-background-color: #D9C830;");
                        else
                            btnHygiene.setStyle("-fx-background-color: #C94242;");
                        System.out.println("Seconds clean: " + secondsClean);

                    } else {
                        lasTimeclean = now;
                    }

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        clockThread2.setDaemon(true);
        clockThread2.start(); // Iniciar el hilo
    }

    public void threadsleep() {

        Thread clockThread3;
        clockThread3 = new Thread(new Runnable() {
            public void run() {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                while (true) {
                    LocalDateTime now = LocalDateTime.now();
                    if (lasTimesleep != null) {
                        Duration duration = Duration.between(lasTimesleep, now);
                        secondsSleep = duration.toSeconds();

                        if (secondsSleep <= 20)
                            btnSleep.setStyle("-fx-background-color: #5E9D46;");
                        else if (secondsSleep <= 30)
                            btnSleep.setStyle("-fx-background-color: #D9C830;");
                        else
                            btnSleep.setStyle("-fx-background-color: #C94242;");
                        System.out.println("Seconds sleep:  " + secondsSleep);
                        System.out.println('\n');
                    } else {
                        lasTimesleep = now;
                    }

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        clockThread3.setDaemon(true);
        clockThread3.start(); // Iniciar el hilo
    }

    // Methods to interact with the pet
    public void feed() {
        sleep = 100;

        LocalDateTime now = LocalDateTime.now();
        lasTimefeed = now;
        System.out.println("Feeding");
        interaction = xmlHandler.getRandomEat();
    }

    public void clean() {

        LocalDateTime now = LocalDateTime.now();
        lasTimeclean = now;

        System.out.println("Cleaning");
        interaction = xmlHandler.getRandomHygene();
    }

    public void sleep() {
        food = 100;
        LocalDateTime now = LocalDateTime.now();

        lasTimesleep = now;

        System.out.println("Sleeping");
        interaction = xmlHandler.getRandomHygene();
        showActivity();
    }

    public void showActivity() {
        // VBox vbActivity = new VBox();
        System.out.println(interaction.getQuestion());
        System.out.println(interaction.getTip());

    }

    public void colorChange(long seconds, Button button) {
        if (seconds <= 20)
            button.setStyle("-fx-background-color: #5E9D46;");
        else if (seconds <= 30)
            button.setStyle("-fx-background-color: #D9C830;");
        else
            button.setStyle("-fx-background-color: #C94242;");
    }

    public LocalDateTime String_To_DateTime(String string) {
        return LocalDateTime.parse(string, dtf);
    }

    public String Stringfechas() {
        return "" + dtf.format(lasTimeclean) + "&" + dtf.format(lasTimefeed) + "&" + dtf.format(lasTimesleep);
    }

}