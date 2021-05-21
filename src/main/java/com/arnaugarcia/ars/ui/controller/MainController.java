package com.arnaugarcia.ars.ui.controller;

import com.arnaugarcia.ars.service.service.DeviceService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Controller
@Scope("prototype")
public class MainController implements Initializable {

    @Value("classpath:/views/home.fxml")
    private Resource homeViewResource;

    @Autowired
    private DeviceService deviceService;

    @FXML
    public ScrollPane content;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            VBox pane = FXMLLoader.load(homeViewResource.getURL());
            content.setContent(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
