package com.arnaugarcia.ars.ui.service.impl;

import com.arnaugarcia.ars.service.domain.Display;
import com.arnaugarcia.ars.service.domain.DisplayRotation;
import com.arnaugarcia.ars.service.domain.Orientation;
import com.arnaugarcia.ars.service.service.DisplayService;
import com.arnaugarcia.ars.service.service.dto.DeviceData;
import com.arnaugarcia.ars.ui.hook.events.DeviceDataEvent;
import com.arnaugarcia.ars.ui.service.RotateService;
import com.arnaugarcia.ars.ui.service.UserPreferenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Optional;

import static com.arnaugarcia.ars.service.domain.DisplayRotation.ROTATE_0;
import static com.arnaugarcia.ars.service.domain.DisplayRotation.ROTATE_90;
import static java.util.Optional.empty;
import static java.util.Optional.of;

@Service
public class RotateServiceImpl implements RotateService, ApplicationListener<DeviceDataEvent> {

    private final Logger logger = LoggerFactory.getLogger(RotateService.class);

    private Optional<Display> selectedDisplay = empty();
    private final int threshold = 30; // Replace this with value
    private final UserPreferenceService userPreferenceService;
    private final DisplayService displayService;

    public RotateServiceImpl(UserPreferenceService userPreferenceService, DisplayService displayService) {
        this.userPreferenceService = userPreferenceService;
        this.displayService = displayService;
    }

    @PostConstruct
    private void findPersistedDisplay() {
        this.userPreferenceService.findDisplay()
                .flatMap(this.displayService::findById)
                .ifPresent(this::setSelectedDisplay);
    }

    @Override
    public void setSelectedDisplay(Display selectedDisplay) {
        this.selectedDisplay = of(selectedDisplay);
    }

    @Override
    public void rotateIfNeeded(DeviceData deviceData) {
        deviceData.getOrientation(threshold).ifPresent(deviceOrientation -> {
            if (this.selectedDisplay.isPresent()) {
                final Display selectedDisplay = this.selectedDisplay.get();
                if (selectedDisplay.getOrientation() != deviceOrientation) {
                    final DisplayRotation rotation = buildRotation(deviceOrientation);
                    logger.info("Rotating screen {} to {}", selectedDisplay.getId(), rotation.getValue());
                    displayService.rotateDisplay(selectedDisplay, rotation);
                    reloadSelectedDisplayById(selectedDisplay.getId().toString());
                }
            }
        });
    }

    private void reloadSelectedDisplayById(String displayId) {
        this.displayService.findById(displayId)
                .ifPresent(this::setSelectedDisplay);
    }

    public DisplayRotation buildRotation(Orientation orientation) {
        return switch (orientation) {
            case VERTICAL, INVERTED_VERTICAL -> ROTATE_90;
            case HORIZONTAL, INVERTED_HORIZONTAL -> ROTATE_0;
        };
    }

    @Override
    public void onApplicationEvent(DeviceDataEvent deviceDataEvent) {
        rotateIfNeeded(deviceDataEvent.getData());
    }
}
