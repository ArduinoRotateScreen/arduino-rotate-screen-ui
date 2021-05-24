package com.arnaugarcia.ars.ui.controller;

import com.arnaugarcia.ars.service.domain.Device;
import com.arnaugarcia.ars.service.service.DeviceService;
import com.arnaugarcia.ars.service.service.dto.DeviceDataListener;
import com.arnaugarcia.ars.ui.component.Route;
import com.arnaugarcia.ars.ui.service.RotateService;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
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
    public Label statusLabel;

    @FXML
    public CheckBox showDeviceLogsCheckBox;

    @FXML
    public TextArea logArea;

    @FXML
    private ChoiceBox<Device> deviceSelector;

    private Device currentDevice;

    private final RotateService rotateService;

    public DeviceController(DeviceService deviceService, RotateService rotateService) {
        this.deviceService = deviceService;
        this.rotateService = rotateService;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDevicesInSelector();
        deviceSelector.getSelectionModel()
                .selectedItemProperty()
                .addListener(onItemSelected());
    }

    private ChangeListener<Device> onItemSelected() {
        return (observableValue, device, t1) -> {
            final Device selectedDevice = observableValue.getValue();
            if (this.currentDevice == selectedDevice) {
                return;
            }
            this.currentDevice = selectedDevice;
            attachListener(this.currentDevice);
        };
    }

    private void attachListener(Device device)  {
        deviceService.attachListener(device, (DeviceDataListener) deviceData -> {
            appendStringInLog(deviceData.toString());
            this.rotateService.rotateIfNeeded(deviceData);
        });
    }

    @FXML
    private void clearAndToggleLogArea() {
        this.logArea.clear();
        this.logArea.setDisable(!this.logArea.isDisabled());
    }

    private void appendStringInLog(String data) {
        if (this.showDeviceLogsCheckBox.isSelected()) {
            this.logArea.appendText(data);
        }
    }

    private void loadDevicesInSelector() {
        final List<Device> deviceList = this.deviceService.getDeviceList();
        deviceSelector.setItems(observableArrayList(deviceList));
    }
}
