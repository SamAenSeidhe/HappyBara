package com.thisastergroup.controller;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.io.IOException;
import java.lang.StackWalker.StackFrame;
import java.time.Duration;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;

import com.thisastergroup.Model.Interaction;
import com.thisastergroup.Model.JournalEntry;
import com.thisastergroup.Model.Item;
import com.thisastergroup.Model.SQLUserMethods;
import com.thisastergroup.Model.SQLJournalMethods;
import com.thisastergroup.Model.SQLItemMethods;
import com.thisastergroup.Model.User;
import com.thisastergroup.Model.XMLHandler;
import com.thisastergroup.controller.CtrlLogin;

public class CtrlRoom {
    @FXML
    private Button imgIndicator;
    @FXML
    private Button btnHygiene;
    @FXML
    private Button btnSleep;
    @FXML
    private Button btnFood;
    @FXML
    private Button Right_Arrow;
    @FXML
    private Button Left_Arrow;
    @FXML
    private ToggleButton btnToShop;
    @FXML
    private ToggleButton tbJournal;
    @FXML
    private HBox JournalSec;
    @FXML
    private HBox CenterSec;
    @FXML
    private HBox CareSec;
    @FXML
    private HBox ShopButtons;
    @FXML
    private HBox NoItemsMsg;
    @FXML
    private AnchorPane apButtons;
    @FXML
    private VBox Structure;
    @FXML
    private VBox ShopSec;
    @FXML
    private ImageView imgChiguiro;
    @FXML
    private StackPane stack;
    @FXML
    private ImageView currentItem;
    @FXML
    private StackPane mainStackPane;

    // ImageView imageView = new
    // ImageView(getClass().getResource("../../../alert.png").toExternalForm());

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
    private int item;
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    private SQLUserMethods sql = new SQLUserMethods();
    private SQLItemMethods sql_items = new SQLItemMethods();

    private Image chiguiFrame = new Image("file:src/main/resources/Idle.gif");

    public void initialize() {

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            // code to run when the application is closed
            sql.updateOutfit(user);
            user.setLastTimes(Stringfechas());
            sql.updateLastTimes(user);
        }));

        String timecitos = sql.getlastTimes(user);
        System.out.println("Last Times: " + timecitos);
        if (timecitos != null) {
            String[] fechas = timecitos.split("&");
            lasTimeclean = String_To_DateTime(fechas[0]);
            lasTimefeed = String_To_DateTime(fechas[1]);
            lasTimesleep = String_To_DateTime(fechas[2]);
        }
        stack.getChildren().add(new Label(""));
        if (user.getOutfit() != null) {
            Item outfit = sql_items.getItem(user.getOutfit());
            System.out.println(outfit);
            System.out.println(outfit.getImage_path());
            Image currentOutfit = new Image(outfit.getImage_path());
            currentItem.setImage(currentOutfit);
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
        /*
         * ShopButtons.setDisable(true);
         * ShopButtons.setOpacity(0);
         * NoItemsMsg.setDisable(true);
         * NoItemsMsg.setOpacity(0);
         */
        ShopSec.setDisable(true);
        ShopSec.setOpacity(0);
        Right_Arrow.setDisable(true);
        Right_Arrow.setOpacity(0);
        Left_Arrow.setDisable(true);
        Left_Arrow.setOpacity(0);
        imgIndicator.setOpacity(1);
        btnFood.setDisable(true);
        btnFood.setOpacity(0);
        btnSleep.setDisable(true);
        btnSleep.setOpacity(0);
        btnHygiene.setDisable(true);
        btnHygiene.setOpacity(0);
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
        imgIndicator.setDisable(true);
        imgIndicator.setOpacity(0);
        btnFood.setDisable(false);
        btnFood.setOpacity(1);
        btnSleep.setDisable(false);
        btnSleep.setOpacity(1);
        btnHygiene.setDisable(false);
        btnHygiene.setOpacity(1);
        refreshIndicator();
        System.out.println("Indicator: " + indicator + " Hygiene: " + hygiene + " Sleep: " + sleep + " Food: " + food);
        apButtons.hoverProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                btnFood.setDisable(true);
                btnFood.setOpacity(0);
                btnSleep.setDisable(true);
                btnSleep.setOpacity(0);
                btnHygiene.setDisable(true);
                btnHygiene.setOpacity(0);
            } else {
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

    public void Customization() throws IOException {
        item = -1;
        System.out.println("Customization Clicked!");
        imgIndicator.setDisable(!apButtons.isDisabled());
        apButtons.setDisable(!apButtons.isDisabled());
        ShopSec.setDisable(!ShopSec.isDisabled());
        Right_Arrow.setDisable(!Right_Arrow.isDisabled());
        Left_Arrow.setDisable(!Left_Arrow.isDisabled());
        if (Right_Arrow.isDisabled() && Left_Arrow.isDisabled()) {
            imgIndicator.setOpacity(0);
            Right_Arrow.setOpacity(0);
            Left_Arrow.setOpacity(0);
            ShopButtons.setOpacity(0);
            NoItemsMsg.setOpacity(0);
        } else {
            if (currentItem != null) {
                stack.getChildren().remove(currentItem);
            }
            imgIndicator.setOpacity(0);
            Right_Arrow.setOpacity(1);
            Left_Arrow.setOpacity(1);
            ShopSec.setOpacity(1);
            ShopButtons.setOpacity(1);
            NoItemsMsg.setOpacity(0);
        }
    }

    public void ChangeClothingForwards() throws IOException {
        ArrayList<Item> clothingOptions = sql_items.getUserItems(user);
        Image viewCurrentItem;
        if (!clothingOptions.isEmpty()) {
            if (currentItem.getImage() != null) {
                currentItem.setImage(null);
            }
            item++;
            try {
                viewCurrentItem = new Image(clothingOptions.get(item).getImage_path());
            } catch (Exception e) {
                item = 0;
                viewCurrentItem = new Image(clothingOptions.get(item).getImage_path());
            }
            currentItem = new ImageView(viewCurrentItem);
            stack.getChildren().add(currentItem);
            System.out.println(clothingOptions.get(item));
            user.setOutfit(clothingOptions.get(item).getName());
        } else {
            NoItemsMsg.setDisable(false);
            NoItemsMsg.setOpacity(1);
        }
    }

    public void ChangeClothingBackwards() throws IOException {
        ArrayList<Item> clothingOptions = sql_items.getUserItems(user);
        Image viewCurrentItem;
        if (!clothingOptions.isEmpty()) {
            if (currentItem.getImage() != null) {
                currentItem.setImage(null);
            }
            item--;
            try {
                viewCurrentItem = new Image(clothingOptions.get(item).getImage_path());
            } catch (Exception e) {
                item = clothingOptions.size() - 1;
                viewCurrentItem = new Image(clothingOptions.get(item).getImage_path());
            }
            currentItem = new ImageView(viewCurrentItem);
            stack.getChildren().add(currentItem);
            System.out.println(clothingOptions.get(item));
            user.setOutfit(clothingOptions.get(item).getName());
        } else {
            NoItemsMsg.setDisable(false);
            NoItemsMsg.setOpacity(1);
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
                    imgIndicator.setStyle("-fx-background-color: "
                            + obtenerColorHexadecimal((secondsFeed + secondsClean + secondsSleep) / 3, 10.0, 30.0)
                            + ";");
                    if (lasTimefeed != null) {
                        Duration duration = Duration.between(lasTimefeed, now);
                        secondsFeed = duration.toSeconds();

                        btnFood.setStyle(
                                "-fx-background-color: " + obtenerColorHexadecimal(secondsFeed, 20.0, 35.0) + ";");
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

                        btnHygiene.setStyle(
                                "-fx-background-color: " + obtenerColorHexadecimal(secondsClean, 10.0, 30.0) + ";");
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

                        btnSleep.setStyle(
                                "-fx-background-color: " + obtenerColorHexadecimal(secondsSleep, 10.0, 30.0) + ";");
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

    // Method to calculate the color of the button based on the time since last
    public static String obtenerColorHexadecimal(long seconds, double seconds_yellow, double seconds_red) {
        // Definir los colores verde, amarillo y rojo en RGB
        int[] verde = { 0x5E, 0x9D, 0x46 }; // #5E9D46
        int[] amarillo = { 0xD9, 0xC8, 0x30 }; // #D9C830
        int[] rojo = { 0xC9, 0x42, 0x42 }; // #C94242

        int[] colorResultante = new int[3];
        double porcentaje;

        // Si los segundos son mayores o iguales a seconds_red, quedarse en rojo
        if (seconds >= seconds_red) {
            return "#C94242"; // Color rojo en formato hexadecimal
        }

        // Calcular el porcentaje basado en los segundos
        if (seconds <= seconds_yellow) {
            // Transición de verde a amarillo
            porcentaje = (double) seconds / seconds_yellow;
            for (int i = 0; i < 3; i++) {
                colorResultante[i] = (int) (verde[i] + (amarillo[i] - verde[i]) * porcentaje);
            }
        } else {
            // Transición de amarillo a rojo
            porcentaje = (double) (seconds - seconds_yellow) / (seconds_red - seconds_yellow);
            for (int i = 0; i < 3; i++) {
                colorResultante[i] = (int) (amarillo[i] + (rojo[i] - amarillo[i]) * porcentaje);
            }
        }

        // Asegurarse de que los valores de RGB estén dentro del rango 0-255
        for (int i = 0; i < 3; i++) {
            colorResultante[i] = Math.min(255, Math.max(0, colorResultante[i]));
        }

        // Convertir el color a formato hexadecimal asegurando que siempre tenga 2
        // dígitos por componente
        return String.format("#%02x%02x%02x", colorResultante[0], colorResultante[1], colorResultante[2]);
    }

    // Methods to interact with the pet
    public void feed() {
        sleep = 100;

        LocalDateTime now = LocalDateTime.now();
        lasTimefeed = now;
        System.out.println("Feeding");
        interaction = xmlHandler.getRandomEat();
        showActivity();
    }

    public void clean() {

        LocalDateTime now = LocalDateTime.now();
        lasTimeclean = now;

        System.out.println("Cleaning");
        interaction = xmlHandler.getRandomHygene();
        showActivity();
    }

    public void sleep() {
        food = 100;
        LocalDateTime now = LocalDateTime.now();

        lasTimesleep = now;

        System.out.println("Sleeping");
        interaction = xmlHandler.getRandomSleep();
        showActivity();
    }

    public void toShop(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        Scene scene;

        root = FXMLLoader.load(getClass().getResource("..//view//TheFinalShop.fxml"));
        scene = new Scene(root, 500, 480);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        user.setLastTimes(Stringfechas());
        sql.updateLastTimes(user);
    }

    public void showActivity() {
        // VBox vbActivity = new VBox();
        System.out.println(interaction.getQuestion());
        System.out.println(interaction.getTip());

        VBox vbActivity = new VBox();
        vbActivity.setMinSize(200, 100);
        vbActivity.setMaxSize(200, 300);
        vbActivity.setAlignment(Pos.CENTER);
        vbActivity.getStyleClass().add("activity-box");

        Label lblQuestion = new Label(interaction.getQuestion());
        lblQuestion.getStyleClass().add("activity-label");
        lblQuestion.setWrapText(true);
        lblQuestion.setPrefWidth(480);
        vbActivity.getChildren().add(lblQuestion);

        Label lblTip = new Label(interaction.getTip());
        lblTip.getStyleClass().add("activity-label");
        lblTip.setWrapText(true);
        lblTip.setPrefWidth(200);

        TextField txtAnswer = new TextField();
        vbActivity.getChildren().add(txtAnswer);

        Button closeActivity = new Button("X");
        closeActivity.getStyleClass().add("activity-close-button");
        closeActivity.setOnAction(e -> {
            vbActivity.getChildren().clear();
            mainStackPane.getChildren().remove(vbActivity);
            txtAnswer.clear();
        });

        Button sendAnswer = new Button("Send");
        sendAnswer.getStyleClass().add("activity-send-button");
        sendAnswer.setOnAction(e -> {
            vbActivity.getChildren().clear();
            vbActivity.getChildren().add(closeActivity);
            vbActivity.getChildren().add(lblTip);

            SQLJournalMethods sqlJournal = new SQLJournalMethods();
            sqlJournal
                    .JournalEntry(new JournalEntry(user.getUsername(),
                            interaction.getQuestion() + ": " + txtAnswer.getText(), "Interaction",
                            dtf.format(LocalDateTime.now())));
            user.setBalance(user.getBalance() + 5);
            System.out.println("Journal Entry added: " + txtAnswer.getText());

            SQLUserMethods sqlUser = new SQLUserMethods();
            sqlUser.updateUser(user);

        });
        vbActivity.getChildren().add(sendAnswer);

        mainStackPane.getChildren().add(vbActivity);
    }

    public LocalDateTime String_To_DateTime(String string) {
        return LocalDateTime.parse(string, dtf);
    }

    public String Stringfechas() {
        return "" + dtf.format(lasTimeclean) + "&" + dtf.format(lasTimefeed) + "&" + dtf.format(lasTimesleep);
    }

}