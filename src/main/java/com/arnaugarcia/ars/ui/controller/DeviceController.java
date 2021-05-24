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
import javafx.scene.control.ChoiceBox;
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
            this.currentDevice = observableValue.getValue();
            connect(this.currentDevice);
        };
    }

    private void connect(Device device)  {
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
            }
        });
    }

    private void appendStringInLog(String data) {

    }

    private void loadDevicesInSelector() {
        final List<Device> deviceList = this.deviceService.getDeviceList();
        deviceSelector.setItems(observableArrayList(deviceList));
    }
}
