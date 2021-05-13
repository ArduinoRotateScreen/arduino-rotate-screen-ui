package com.arnaugarcia.ars.service.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static javafx.fxml.FXMLLoader.load;

public class MainController implements Initializable {

    @FXML
    public ScrollPane content;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            VBox pane = load(getClass().getResource("/javafx/views/home.fxml"));
            content.setContent(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
