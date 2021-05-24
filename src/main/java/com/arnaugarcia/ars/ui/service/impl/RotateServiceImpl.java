package com.arnaugarcia.ars.ui.service.impl;

import com.arnaugarcia.ars.service.domain.Display;
import com.arnaugarcia.ars.service.service.DisplayService;
import com.arnaugarcia.ars.service.service.dto.DeviceData;
import com.arnaugarcia.ars.ui.service.RotateService;
import com.arnaugarcia.ars.ui.service.dto.DeviceThreshold;
import org.springframework.stereotype.Service;

@Service
public class RotateServiceImpl implements RotateService {

    private Display currentDisplay;
    private DeviceThreshold threshold;
    private final DisplayService displayService;

    public RotateServiceImpl(DisplayService displayService) {
        this.currentDisplay = findPersistedDisplay();
        this.displayService = displayService;
    }

    private Display findPersistedDisplay() {
        return null;
    }

    @Override
    public void rotateIfNeeded(DeviceData deviceData) {

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
}
