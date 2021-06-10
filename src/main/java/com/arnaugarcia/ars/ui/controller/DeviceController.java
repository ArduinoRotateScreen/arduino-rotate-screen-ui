package com.arnaugarcia.ars.ui.controller;

import com.arnaugarcia.ars.service.domain.Device;
import com.arnaugarcia.ars.service.service.DeviceService;
import com.arnaugarcia.ars.service.service.exception.DeviceNotFound;
import com.arnaugarcia.ars.ui.component.Route;
import com.arnaugarcia.ars.ui.hook.events.DeviceDataEvent;
import com.arnaugarcia.ars.ui.service.IOService;
import com.arnaugarcia.ars.ui.service.UserPreferenceService;
import com.arnaugarcia.ars.ui.service.dto.UserConfigurationDTO;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static java.util.stream.Collectors.toList;
import static javafx.collections.FXCollections.observableArrayList;

@Controller
@FxmlView("/views/devices.fxml")
public class DeviceController extends Route implements Initializable, ApplicationListener<DeviceDataEvent> {

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

    private final IOService ioService;

    private final UserPreferenceService userPreferenceService;

    public DeviceController(DeviceService deviceService,
                            IOService ioService,
                            UserPreferenceService userPreferenceService) {
        this.deviceService = deviceService;
        this.ioService = ioService;
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
        return (observableValue, devicePort, selectedValue) -> {
            final Device selectedDevice = deviceService.findDeviceByPort(selectedValue)
                    .orElseThrow(() -> new DeviceNotFound(devicePort));
            if (this.currentDevice == selectedDevice) {
                return;
            }
            this.currentDevice = selectedDevice;
            ioService.streamDataOf(this.currentDevice);
        };
    }

    @FXML
    private void clearAndToggleLogArea() {
        this.logArea.clear();
        this.logArea.setDisable(!this.logArea.isDisabled());
    }

    private void loadDevicesInSelector() {
        final List<String> deviceList = this.deviceService.getDeviceList().stream()
                .map(Device::getPort)
                .collect(toList());
        deviceSelector.setItems(observableArrayList(deviceList));
    }

    @FXML
    public void saveDefaultDevice() {
        final UserConfigurationDTO userConfiguration = UserConfigurationDTO.builder()
                .devicePort(this.currentDevice.getPort())
                .build();
        this.userPreferenceService.storeUserConfiguration(userConfiguration);
        // TODO: Alert system (success saved preferences)
    }

    @Override
    public void onApplicationEvent(DeviceDataEvent deviceDataEvent) {
        System.out.println("Data received " + deviceDataEvent.getData().toString());
        if (this.showDeviceLogsCheckBox.isSelected()) {
            this.logArea.appendText(deviceDataEvent.toString());
        }
    }
}
