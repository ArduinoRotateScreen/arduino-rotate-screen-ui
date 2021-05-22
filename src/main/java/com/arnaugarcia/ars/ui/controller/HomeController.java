package com.arnaugarcia.ars.ui.controller;

import com.arnaugarcia.ars.ui.component.Route;
import javafx.fxml.Initializable;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@FxmlView("/views/home.fxml")
public class HomeController extends Route implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Hello from HomeController");
    }
}
