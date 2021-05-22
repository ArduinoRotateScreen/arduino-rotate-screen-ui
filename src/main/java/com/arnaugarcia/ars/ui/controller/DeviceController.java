package com.arnaugarcia.ars.ui.controller;

import com.arnaugarcia.ars.service.service.DeviceService;
import com.arnaugarcia.ars.ui.component.Route;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
@FxmlView("/views/devices.fxml")
public class DeviceController extends Route implements Initializable {

    @FXML
    public VBox devicesContainer;
    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Init Home controller");
        deviceService.getDeviceList().forEach(device -> {
            devicesContainer.getChildren().add(new Label(device.getName()));
        });
    }
}
