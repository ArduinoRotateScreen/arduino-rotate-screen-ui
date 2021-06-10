package com.arnaugarcia.ars.ui.hook;

import com.arnaugarcia.ars.service.domain.Device;
import com.arnaugarcia.ars.service.service.DeviceService;
import com.arnaugarcia.ars.ui.service.IOService;
import com.arnaugarcia.ars.ui.service.UserPreferenceService;
import com.arnaugarcia.ars.ui.service.dto.UserConfigurationDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ApplicationInitHook {

    private final Logger logger = LoggerFactory.getLogger(ApplicationInitHook.class);

    private final UserPreferenceService userPreferenceService;
    private final DeviceService deviceService;
    private final IOService ioService;

    public ApplicationInitHook(UserPreferenceService userPreferenceService,
                               DeviceService deviceService,
                               IOService ioService) {
        this.userPreferenceService = userPreferenceService;
        this.deviceService = deviceService;
        this.ioService = ioService;
    }

    @EventListener
    public void onApplicationEvent(ApplicationStartedEvent applicationStartedEvent) {
        logger.info("Trying to stream data if device is found");
        this.userPreferenceService.findUserConfiguration()
                .flatMap(this::findDeviceByUserConfiguration)
                .ifPresent(ioService::streamDataOf);
    }

    private Optional<Device> findDeviceByUserConfiguration(UserConfigurationDTO userConfigurationDTO) {
        return this.deviceService.findDeviceByPort(userConfigurationDTO.getDevicePort());
    }
}
