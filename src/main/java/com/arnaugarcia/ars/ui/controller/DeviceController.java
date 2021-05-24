package com.arnaugarcia.ars.ui.controller;

import com.arnaugarcia.ars.service.domain.Device;
import com.arnaugarcia.ars.service.service.DeviceService;
import com.arnaugarcia.ars.ui.component.Route;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static javafx.collections.FXCollections.observableArrayList;

@Controller
@FxmlView("/views/devices.fxml")
public class DeviceController extends Route implements Initializable {

    private final DeviceService deviceService;

    @FXML
    private ChoiceBox<Device> deviceSelector;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        final List<Device> deviceList = this.deviceService.getDeviceList();
        deviceSelector.setItems(observableArrayList(deviceList));
        deviceSelector.getSelectionModel().selectFirst();
    }
}
