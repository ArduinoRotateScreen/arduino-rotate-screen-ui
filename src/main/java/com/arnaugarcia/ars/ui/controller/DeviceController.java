package com.arnaugarcia.ars.ui.controller;

import com.arnaugarcia.ars.service.domain.Device;
import com.arnaugarcia.ars.service.service.DeviceService;
import com.arnaugarcia.ars.ui.component.Route;
import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
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

import static com.fazecast.jSerialComm.SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
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

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
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
            deviceService.removeListener(device);
            this.currentDevice = selectedDevice;
            attachListener(this.currentDevice);
        };
    }

    private void attachListener(Device device)  {
        deviceService.attachListener(device, new SerialPortDataListener() {
            @Override
            public int getListeningEvents() {
                return LISTENING_EVENT_DATA_AVAILABLE;
            }

            @Override
            public void serialEvent(SerialPortEvent event) {
                if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE)
                    return;
                final SerialPort serialPort = event.getSerialPort();
                byte[] newData = new byte[serialPort.bytesAvailable()];
                serialPort.readBytes(newData, newData.length);
                appendStringInLog(new String(newData));
            }
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
