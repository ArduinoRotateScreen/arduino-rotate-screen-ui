package com.arnaugarcia.ars.ui.service.impl;

import com.arnaugarcia.ars.service.domain.Device;
import com.arnaugarcia.ars.service.service.DeviceService;
import com.arnaugarcia.ars.service.service.dto.DeviceDataListener;
import com.arnaugarcia.ars.ui.hook.events.DeviceDataEvent;
import com.arnaugarcia.ars.ui.service.IOService;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;

@Service
public class IOServiceImpl implements IOService {

    private Optional<Device> currentDevice = empty();
    private final ApplicationContext applicationContext;
    private final DeviceService deviceService;

    public IOServiceImpl(ApplicationContext applicationContext, DeviceService deviceService) {
        this.applicationContext = applicationContext;
        this.deviceService = deviceService;
    }

    @Override
    public void startStream(Device device) {
        this.deviceService.attachListener(device, streamEventsToContext());
        setCurrentDevice(of(device));
    }

    @Override
    public void stopStream() {
        this.currentDevice.ifPresent(deviceService::removeListener);
        setCurrentDevice(empty());
    }

    private void setCurrentDevice(Optional<Device> device) {
        this.currentDevice = device;
    }

    private DeviceDataListener streamEventsToContext() {
        return deviceData -> applicationContext.publishEvent(new DeviceDataEvent(deviceData));
    }
}
