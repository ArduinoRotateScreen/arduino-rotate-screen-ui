package com.arnaugarcia.ars.ui.controller;

import com.arnaugarcia.ars.service.service.DisplayService;
import com.arnaugarcia.ars.ui.component.Route;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@FxmlView("/views/home.fxml")
public class HomeController extends Route implements Initializable {

    private final DisplayService displayService;

    @FXML
    public HBox displays;

    public HomeController(DisplayService displayService) {
        this.displayService = displayService;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appendDisplays();
    }

    private void appendDisplays() {
        displayService.findDisplays().forEach(display -> {
            displays.getChildren().add(new Rectangle());
        });
    }
}
