package com.arnaugarcia.ars.ui.controller.layouts;

import com.arnaugarcia.ars.ui.PrimaryStageInitializer;
import com.arnaugarcia.ars.ui.component.ActiveRoute;
import com.arnaugarcia.ars.ui.controller.AboutController;
import com.arnaugarcia.ars.ui.controller.DeviceController;
import com.arnaugarcia.ars.ui.controller.HomeController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class MenuController {

    @FXML
    public VBox sidebarMenu;
    private ApplicationContext applicationContext;

    @Autowired
    public MenuController(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void exit() {
        System.exit(0);
    }

    public void goAbout(MouseEvent mouseEvent) {
        applicationContext.publishEvent(new ActiveRoute(AboutController.class));
    }

    public void goDevices(MouseEvent mouseEvent) {
        applicationContext.publishEvent(new ActiveRoute(DeviceController.class));
    }

    public void goHome(MouseEvent mouseEvent) {
        applicationContext.publishEvent(new ActiveRoute(HomeController.class));
    }
}
