package com.arnaugarcia.ars.service.controller;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class AboutController implements Initializable {

    private MainController mainController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void init(MainController mainController) {
        this.mainController = mainController;
    }
}
