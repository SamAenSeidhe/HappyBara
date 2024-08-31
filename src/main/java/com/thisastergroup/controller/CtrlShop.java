package com.thisastergroup.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class CtrlShop {
    @FXML
    private AnchorPane apIntroPromo;
    @FXML
    private Button btnIcon;
    @FXML
    private AnchorPane apBanner;

    public void initialize() {
        GridPane.setColumnSpan(apIntroPromo, 3);
        GridPane.setColumnSpan(apBanner, 2);

    }    
}
