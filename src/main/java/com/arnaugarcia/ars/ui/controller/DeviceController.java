package com.arnaugarcia.ars.ui.controller;

import com.arnaugarcia.ars.service.domain.Device;
import com.arnaugarcia.ars.service.service.DeviceService;
import com.arnaugarcia.ars.service.service.dto.DeviceDataListener;
import com.arnaugarcia.ars.service.service.exception.DeviceNotFound;
import com.arnaugarcia.ars.ui.component.Route;
import com.arnaugarcia.ars.ui.service.RotateService;
import com.arnaugarcia.ars.ui.service.UserPreferenceService;
import com.arnaugarcia.ars.ui.service.dto.UserConfigurationDTO;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static java.util.stream.Collectors.toList;
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
    private ChoiceBox<String> deviceSelector;

    private Device currentDevice;

    private final RotateService rotateService;

    private final UserPreferenceService userPreferenceService;

    public DeviceController(DeviceService deviceService,
                            RotateService rotateService,
                            UserPreferenceService userPreferenceService) {
        this.deviceService = deviceService;
        this.rotateService = rotateService;
        this.userPreferenceService = userPreferenceService;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDevicesInSelector();
        userPreferenceService.findUserConfiguration()
                .ifPresent(userConfigurationDTO -> setSelectedPort(userConfigurationDTO.getDevicePort()));
        deviceSelector.getSelectionModel()
                .selectedItemProperty()
                .addListener(onItemSelected());
    }

    private void setSelectedPort(String devicePort) {
        deviceSelector.setValue(devicePort);
    }

    private ChangeListener<String> onItemSelected() {
        return (observableValue, devicePort, t1) -> {
            final Device selectedDevice = deviceService.findDeviceByPort(devicePort)
                    .orElseThrow(() -> new DeviceNotFound(devicePort));
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
        final List<String> deviceList = this.deviceService.getDeviceList().stream()
                .map(Device::getPort)
                .collect(toList());
        deviceSelector.setItems(observableArrayList(deviceList));
    }

    public void saveDefaultDevice(MouseEvent mouseEvent) {
        final UserConfigurationDTO userConfiguration = UserConfigurationDTO.builder()
                .devicePort(this.currentDevice.getPort())
                .build();
        this.userPreferenceService.storeUserConfiguration(userConfiguration);
        // TODO: Alert system (success saved preferences)
    }
}
