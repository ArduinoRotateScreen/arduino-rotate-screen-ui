package com.arnaugarcia.ars.ui.controller;

import com.arnaugarcia.ars.service.service.DeviceService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
@Scope("prototype")
public class HomeController implements Initializable {

    @FXML
    public VBox devicesContainer;

    @Autowired
    private DeviceService deviceService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        deviceService.getDeviceList().forEach(device -> {
            devicesContainer.getChildren().add(new Label(device.getName()));
        });
        System.out.println("Hello from HomeController");
    }
}
