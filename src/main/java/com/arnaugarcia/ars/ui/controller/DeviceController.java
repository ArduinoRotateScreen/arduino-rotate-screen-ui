package com.arnaugarcia.ars.ui.controller;

import com.arnaugarcia.ars.service.service.DeviceService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class DeviceController implements Initializable {

    @FXML
    public VBox devicesContainer;
    private DeviceService deviceService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Init Home controller");
        deviceService.getDeviceList();
    }
}
