package com.arnaugarcia.ars.ui.service.impl;

import com.arnaugarcia.ars.service.domain.Device;
import com.arnaugarcia.ars.service.service.DeviceService;
import com.arnaugarcia.ars.service.service.dto.DeviceDataListener;
import com.arnaugarcia.ars.ui.hook.events.DeviceDataEvent;
import com.arnaugarcia.ars.ui.service.IOService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;

@Service
public class IOServiceImpl implements IOService {

    private final Logger logger = LoggerFactory.getLogger(IOService.class);
    private Optional<Device> currentDevice = empty();
    private final ApplicationContext applicationContext;
    private final DeviceService deviceService;

    public IOServiceImpl(ApplicationContext applicationContext, DeviceService deviceService) {
        this.applicationContext = applicationContext;
        this.deviceService = deviceService;
    }

    @Override
    public void streamDataOf(Device device) {
        if (isCurrentStreamingDevice(device)) {
            return;
        }
        setCurrentDevice(of(device));
        this.deviceService.attachListener(device, streamDataToContext());
    }

    private boolean isCurrentStreamingDevice(Device device) {
        return currentDevice.isPresent() && device.getPort().equals(currentDevice.get().getPort());
    }

    @Override
    public boolean isStreaming() {
        return this.currentDevice.isPresent();
    }

    @Override
    public void stopStream() {
        this.currentDevice.ifPresent(deviceService::removeListener);
        setCurrentDevice(empty());
    }

    private DeviceDataListener streamDataToContext() {
        return deviceData -> {
            logger.debug("Found device data {}", deviceData.toString());
            applicationContext.publishEvent(new DeviceDataEvent(deviceData));
        };
    }

    private void setCurrentDevice(Optional<Device> device) {
        this.currentDevice = device;
    }
}
