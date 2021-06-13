package com.arnaugarcia.ars.ui.service.impl;

import com.arnaugarcia.ars.service.domain.Display;
import com.arnaugarcia.ars.service.domain.Orientation;
import com.arnaugarcia.ars.service.domain.DisplayRotation;
import com.arnaugarcia.ars.service.service.DisplayService;
import com.arnaugarcia.ars.service.service.dto.DeviceData;
import com.arnaugarcia.ars.ui.hook.events.DeviceDataEvent;
import com.arnaugarcia.ars.ui.service.RotateService;
import com.arnaugarcia.ars.ui.service.dto.DeviceThreshold;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import static com.arnaugarcia.ars.service.domain.DisplayRotation.ROTATE_0;
import static com.arnaugarcia.ars.service.domain.DisplayRotation.ROTATE_90;
import static com.arnaugarcia.ars.ui.service.dto.DeviceThreshold.Between.between;

@Service
public class RotateServiceImpl implements RotateService, ApplicationListener<DeviceDataEvent> {

    private final Logger logger = LoggerFactory.getLogger(RotateService.class);

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
        deviceData.getOrientation(30).ifPresent(deviceOrientation -> {
            if (this.currentDisplay.getOrientation() != deviceOrientation) {
                final DisplayRotation rotation = buildRotation(deviceOrientation);
                logger.info("Rotating screen {} to {}", currentDisplay.getId(), rotation.getValue());
                displayService.rotateDisplay(currentDisplay, rotation);
            }
        });
    }

    public DisplayRotation buildRotation(Orientation orientation) {
        return switch (orientation) {
            case VERTICAL, INVERTED_VERTICAL -> ROTATE_0;
            case HORIZONTAL, INVERTED_HORIZONTAL -> ROTATE_90;
        };
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
