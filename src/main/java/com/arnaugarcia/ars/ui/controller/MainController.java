package com.arnaugarcia.ars.ui.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static javafx.fxml.FXMLLoader.load;

@Controller
public class MainController implements Initializable {

    @Value("classpath:/views/home.fxml")
    private Resource homeViewResource;

    @FXML
    public ScrollPane content;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            VBox pane = load(homeViewResource.getURL());
            content.setContent(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
