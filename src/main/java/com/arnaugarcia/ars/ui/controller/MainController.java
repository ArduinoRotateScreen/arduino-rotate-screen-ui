package com.arnaugarcia.ars.ui.controller;

import com.arnaugarcia.ars.ui.component.ActiveRoute;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
@FxmlView("/views/main.fxml")
public class MainController implements Initializable, ApplicationListener<ActiveRoute> {

    @FXML
    public ScrollPane content;
    private final FxWeaver fxWeaver;

    @Autowired
    public MainController(FxWeaver fxWeaver) {
        this.fxWeaver = fxWeaver;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        VBox pane = fxWeaver.loadView(HomeController.class);
        content.setContent(pane);
    }

    @Override
    public void onApplicationEvent(ActiveRoute activeRoute) {
        VBox pane = fxWeaver.loadView(activeRoute.getRoute());
        content.setContent(pane);
    }
}
