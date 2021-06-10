package com.arnaugarcia.ars.ui.service.impl;

import com.arnaugarcia.ars.service.domain.Display;
import com.arnaugarcia.ars.service.service.DisplayService;
import com.arnaugarcia.ars.service.service.dto.DeviceData;
import com.arnaugarcia.ars.ui.hook.events.DeviceDataEvent;
import com.arnaugarcia.ars.ui.service.RotateService;
import com.arnaugarcia.ars.ui.service.dto.DeviceThreshold;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import static com.arnaugarcia.ars.ui.service.dto.DeviceThreshold.Between.between;

@Service
public class RotateServiceImpl implements RotateService, ApplicationListener<DeviceDataEvent> {

    private Display currentDisplay;
    private DeviceThreshold threshold;
    private final DisplayService displayService;

    public RotateServiceImpl(DisplayService displayService) {
        this.currentDisplay = findPersistedDisplay();
        this.threshold = findPersistedThreshold();
        this.displayService = displayService;
    }

    private DeviceThreshold findPersistedThreshold() { // TODO: Get persisted
        return DeviceThreshold.builder()
                .verticalThreshold(between(70, 100))
                .horizontalThreshold(between(-10, 10))
                .build();
    }

    private Display findPersistedDisplay() {
        return new Display(188940595, null, null, 90, null, null);
    }

    @Override
    public void rotateIfNeeded(DeviceData deviceData) {
        System.out.println("Is rotate needed for " + deviceData.toString());
        this.currentDisplay.getOrientation();
    }

    @Override
    public Display getRotateDisplay() {
        return this.currentDisplay;
    }

    @Override
    public void setRotateDisplay(Display display) {
        this.currentDisplay = display;
    }

    @Override
    public void setThreshold(DeviceThreshold threshold) {
        this.threshold = threshold;
    }

    @Override
    public void onApplicationEvent(DeviceDataEvent deviceDataEvent) {
        rotateIfNeeded(deviceDataEvent.getData());
    }
}
