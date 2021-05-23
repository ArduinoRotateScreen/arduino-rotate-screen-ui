package com.arnaugarcia.ars.ui.controller;

import com.arnaugarcia.ars.service.domain.Device;
import com.arnaugarcia.ars.service.service.DeviceService;
import com.arnaugarcia.ars.ui.component.Route;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Controller
@FxmlView("/views/devices.fxml")
public class DeviceController extends Route implements Initializable {

    private final DeviceService deviceService;

    @FXML
    private ChoiceBox deviceSelector;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        final List<String> deviceList = this.deviceService.getDeviceList().
                stream()
                .map(Device::getPort)
                .collect(Collectors.toList());
        final ObservableList<String> devices = FXCollections.observableArrayList(deviceList);
        deviceSelector.setItems(devices);
    }
}
