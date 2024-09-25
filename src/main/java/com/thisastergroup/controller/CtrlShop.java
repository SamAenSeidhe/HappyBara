package com.thisastergroup.controller;

import com.thisastergroup.Model.SQLItemMethods;
import com.thisastergroup.Model.User;
import com.thisastergroup.Model.Item;
import com.thisastergroup.controller.CtrlLogin;
import com.thisastergroup.Model.SQLUserMethods;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import java.io.IOException;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.Label;

public class CtrlShop {
    @FXML
    private AnchorPane apIntroPromo;
    @FXML
    private Button btnIcon;
    @FXML
    private AnchorPane apBanner;
    @FXML
    private FlowPane fpShop;
    @FXML
    private Button prueba;
    @FXML
    private Label coins_label;

    @FXML
    private Button room;
    @FXML
    private ImageView image1;
    @FXML
    private ImageView image2;
    @FXML
    private ImageView image3;
    @FXML
    private ImageView image4;
    @FXML
    private ImageView image5;
    @FXML
    private ImageView image6;
    @FXML
    private ImageView image7;
    @FXML
    private ImageView image8;
    @FXML
    private ImageView imageicon;
    @FXML
    private AnchorPane anchorpane1;
    @FXML
    private AnchorPane anchorpane2;
    @FXML
    private AnchorPane anchorpane3;
    @FXML
    private AnchorPane anchorpane4;
    @FXML
    private AnchorPane anchorpane5;
    @FXML
    private AnchorPane anchorpane6;
    @FXML
    private AnchorPane anchorpane7;
    @FXML
    private AnchorPane anchorpane8;
    @FXML
    private Label labelitem1;
    @FXML
    private Label labelitem2;
    @FXML
    private Label labelitem3;
    @FXML
    private Label labelitem4;
    @FXML
    private Label labelitem5;
    @FXML
    private Label labelitem6;
    @FXML
    private Label labelitem7;
    @FXML  
    private Label labelitem8;
    @FXML
    private Label superlabel;


    private SQLItemMethods sql = new SQLItemMethods();
    private SQLUserMethods sqlUser = new SQLUserMethods();
    private ArrayList<Item> items;
    private ArrayList<Item> userItems = sql.getUserItems(CtrlLogin.getUser());
    private Image img_icon = new Image("file:src/main/resources/Icon.png");
    private Image img1 = new Image("file:src/main/resources/aviator-glasses1.png");
    private Image img2 = new Image("file:src/main/resources/christmas-lights1.png");
    private Image img3 = new Image("file:src/main/resources/hoodie1.png");
    private Image img4 = new Image("file:src/main/resources/scarf1.png");
    private Image img5 = new Image("file:src/main/resources/leather-jacket1.png");
    private Image img8 = new Image("file:src/main/resources/hijab1.png");
    private Image img6 = new Image("file:src/main/resources/sun-glasses1.png");
    private Image img7 = new Image("file:src/main/resources/santa-suit1.png");

    private User user = CtrlLogin.getUser();

    public void initialize() {
        //GridPane.setColumnSpan(apIntroPromo, 3);
        //GridPane.setColumnSpan(apBanner, 2);

        items = sql.getAllItems();
        //loadItems();
        imageicon.setImage(img_icon);
        image1.setImage(img1);
        image2.setImage(img2);
        image3.setImage(img3);
        image4.setImage(img4);
        image5.setImage(img5);
        image6.setImage(img6);
        image7.setImage(img7);
        image8.setImage(img8);
        coins_label.setText("$" + Integer.toString(user.getBalance()));
        ownedItems("aviator_glasses",image1,anchorpane1,labelitem1);
        ownedItems("christmas_lights",image2,anchorpane2,labelitem2);
        ownedItems("hoodie",image3,anchorpane3,labelitem3);
        ownedItems("scarf",image4,anchorpane4,labelitem4);
        ownedItems("leather_jacket",image5,anchorpane5,labelitem5);
        ownedItems("sunglasses",image6,anchorpane6,labelitem6);
        ownedItems("santa_suit",image7,anchorpane7,labelitem7);
        ownedItems("hijab",image8,anchorpane8,labelitem8);
    }

    public void loadItems() {
        // Button creation for each item
        for (int i = 0; i < items.size(); i++) {
            AnchorPane apNewItem = new AnchorPane();
            ImageView ivItem = new ImageView();
            Label price = new Label();
            Label discount = new Label();

            // ivItem.setImage(new Image("file:src/main/resources/" + items.get(i).getName()
            // + ".png"));
            ivItem.fitHeightProperty().add(70);
            ivItem.fitWidthProperty().add(70);

            AnchorPane.setLeftAnchor(ivItem, 5.0);
            AnchorPane.setTopAnchor(ivItem, 5.0);
            AnchorPane.setRightAnchor(ivItem, 5.0);
            AnchorPane.setBottomAnchor(ivItem, 5.0);

            price.setText(items.get(i).getPrice() + "m");
            AnchorPane.setRightAnchor(price, 5.0);
            AnchorPane.setBottomAnchor(price, 5.0);

            apNewItem.getStyleClass().add("item-card");
            apNewItem.setPrefSize(80, 80);
            apNewItem.getChildren().add(ivItem);
            apNewItem.getChildren().add(price);
            if (items.get(i).getDiscount() > 0) {
                discount.setText(items.get(i).getDiscount() + "% off!");
                apNewItem.getChildren().add(discount);
                AnchorPane.setTopAnchor(discount, 5.0);
                AnchorPane.setLeftAnchor(discount, 5.0);
                apNewItem.getStyleClass().add("promo-card");
            }

            apNewItem.setId(items.get(i).getName());
            apNewItem.setOnMouseClicked(e -> 
                System.out.println("Item:" + apNewItem.getId() + " clicked")
            );
            fpShop.getChildren().add(apNewItem);
            //Label lbl = new Label("Hello");
            //fpShop.getChildren().add(lbl);
            //fpShop.getChildren().remove(lbl);
            
        }
    }
    
    public void toRoom (ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        Scene scene;    
        System.out.println("To Room");
        root = FXMLLoader.load(getClass().getResource("..//view//TheFinalRoom.fxml"));
        scene = new Scene(root, 640, 480);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        sqlUser.updateBalance(user);
    }
    
    public void ownedItems(String nameItem , ImageView imga , AnchorPane anchor , Label labelitemx) {
        for (Item useritem : userItems) {
            if (useritem.getName().equals(nameItem)) {
                imga.setDisable(true);
                labelitemx.setText("SOLD");
                anchor.setStyle("-fx-background-color: grey");
                anchor.setDisable(true);
            }
        }
    }
    
    public void buyItem(Item item, User user) {
        user.addItem(item.getName());
        user.setBalance(user.getBalance()-item.getPrice());
        sqlUser.updateitems(user);
        sqlUser.updateBalance(user);
    }

    public void buyItem1(MouseEvent event) throws IOException {
        String Item1 = "aviator_glasses";
        if (user.getBalance() > sql.getItem(Item1).getPrice()) {
            buyItem(sql.getItem(Item1), user);

        image1.setDisable(true);
        labelitem1.setText("SOLD");
        anchorpane1.setStyle("-fx-background-color: grey");
        anchorpane1.setDisable(true);
        coins_label.setText("$" + Integer.toString(user.getBalance()));
        // change background color to gray
        // disable buy button (though there isn't any button???)
        // change label to "sold"
        } else {
        superlabel.setText("Not Enough Coins");
        superlabel.setStyle("-fx-font-size: 20;");
        }
    }

    public void buyItem2(MouseEvent event) throws IOException {
        String Item1 = "christmas_lights";
        if (user.getBalance() > sql.getItem(Item1).getPrice()) {
            buyItem(sql.getItem(Item1), user);
        image2.setDisable(true);
        labelitem2.setText("SOLD");
        anchorpane2.setStyle("-fx-background-color: grey"); 
        anchorpane2.setDisable(true);
        coins_label.setText("$" + Integer.toString(user.getBalance()));

        // change background color to gray
        // disable buy button (though there isn't any button???)
        // change label to "sold"
        } else {
        superlabel.setText("Not Enough Coins");
        superlabel.setStyle("-fx-font-size: 20;");
        }
    }

    public void buyItem3(MouseEvent event) throws IOException {
        String Item1 = "hoodie";
        if (user.getBalance() > sql.getItem(Item1).getPrice()) {
            buyItem(sql.getItem(Item1), user);
        image3.setDisable(true);
        labelitem3.setText("SOLD");
        anchorpane3.setStyle("-fx-background-color: grey");
        anchorpane3.setDisable(true);
        coins_label.setText("$" + Integer.toString(user.getBalance()));

        // change background color to gray
        // disable buy button (though there isn't any button???)
        // change label to "sold"
        } else {
        superlabel.setText("Not Enough Coins");
        superlabel.setStyle("-fx-font-size: 20;");
        }
    }

    public void buyItem4(MouseEvent event) throws IOException {
        String Item1 = "scarf";
        if (user.getBalance() > sql.getItem(Item1).getPrice()) {
            buyItem(sql.getItem(Item1), user);
        image4.setDisable(true);
        labelitem4.setText("SOLD");
        anchorpane4.setStyle("-fx-background-color: grey");
        anchorpane4.setDisable(true);
        coins_label.setText("$" + Integer.toString(user.getBalance()));

        // change background color to gray
        // disable buy button (though there isn't any button???)
        // change label to "sold"
        } else {
        superlabel.setText("Not Enough Coins");
        superlabel.setStyle("-fx-font-size: 20;");
        }
    }

    public void buyItem5(MouseEvent event) throws IOException {
        String Item1 = "leather_jacket";
        if (user.getBalance() > sql.getItem(Item1).getPrice()) {
            buyItem(sql.getItem(Item1), user);
        image5.setDisable(true);
        labelitem5.setText("SOLD");
        anchorpane5.setStyle("-fx-background-color: grey");
        anchorpane5.setDisable(true);
        coins_label.setText("$" + Integer.toString(user.getBalance()));
        // change background color to gray
        // disable buy button (though there isn't any button???)
        // change label to "sold"
        } else {
        superlabel.setText("Not Enough Coins");
        superlabel.setStyle("-fx-font-size: 20;");
        }
    }

    public void buyItem6(MouseEvent event) throws IOException {
        String Item1 = "sunglasses";
        if (user.getBalance() > sql.getItem(Item1).getPrice()) {
            buyItem(sql.getItem(Item1), user);
        image6.setDisable(true);
        labelitem6.setText("SOLD");
        anchorpane6.setStyle("-fx-background-color: grey");
        anchorpane6.setDisable(true);
        coins_label.setText("$" + Integer.toString(user.getBalance()));

        // change background color to gray
        // disable buy button (though there isn't any button???)
        // change label to "sold"
        } else {
            superlabel.setText("Not Enough Coins");
            superlabel.setStyle("-fx-font-size: 20;");
        }
    }

    public void buyItem7(MouseEvent event) throws IOException {
        String Item1 = "santa_suit";
        if (user.getBalance() > sql.getItem(Item1).getPrice()) {
            buyItem(sql.getItem(Item1), user);
        image7.setDisable(true);
        labelitem7.setText("SOLD");
        anchorpane7.setStyle("-fx-background-color: grey");
        anchorpane7.setDisable(true);
        coins_label.setText("$" + Integer.toString(user.getBalance()));

        // change background color to gray
        // disable buy button (though there isn't any button???)
        // change label to "sold"
        } else {
            superlabel.setText("Not Enough Coins");
            superlabel.setStyle("-fx-font-size: 20;");
        }
    }

    public void buyItem8(MouseEvent event) throws IOException {
        String Item1 = "hijab";
        if (user.getBalance() > sql.getItem(Item1).getPrice()) {
            buyItem(sql.getItem(Item1), user);
        image8.setDisable(true);
        labelitem8.setText("SOLD");
        anchorpane8.setStyle("-fx-background-color: grey");
        anchorpane8.setDisable(true);
        coins_label.setText("$" + Integer.toString(user.getBalance()));

        // change background color to gray
        // disable buy button (though there isn't any button???)
        // change label to "sold"
        } else {
            superlabel.setText("Not Enough Coins");
            superlabel.setStyle("-fx-font-size: 20;");
        }
    }

}



