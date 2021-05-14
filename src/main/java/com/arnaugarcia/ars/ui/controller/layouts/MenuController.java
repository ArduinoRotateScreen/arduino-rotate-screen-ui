package com.arnaugarcia.ars.ui.controller.layouts;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class MenuController implements Initializable {

    @FXML
    public VBox sidebarMenu;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sidebarMenu.getChildren().forEach(System.out::println);
    }

    public void exit(MouseEvent mouseEvent) {
        System.exit(0);
    }

    public void goAbout(MouseEvent mouseEvent) {
        // navigate("ABOUT");
    }

    public void goDevices(MouseEvent mouseEvent) {
        //navigate("DEVICES");
    }

    public void goHome(MouseEvent mouseEvent) {
        // navigate("HOME");
    }
}
