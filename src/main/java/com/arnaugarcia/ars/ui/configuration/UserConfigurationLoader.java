package com.arnaugarcia.ars.ui.configuration;

import com.arnaugarcia.ars.service.domain.Device;
import com.arnaugarcia.ars.service.service.DeviceService;
import com.arnaugarcia.ars.ui.service.IOService;
import com.arnaugarcia.ars.ui.service.UserPreferenceService;
import com.arnaugarcia.ars.ui.service.dto.UserConfigurationDTO;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserConfigurationLoader {

    private final UserPreferenceService userPreferenceService;
    private final DeviceService deviceService;
    private final IOService ioService;

    public UserConfigurationLoader(UserPreferenceService userPreferenceService,
                                   DeviceService deviceService,
                                   IOService ioService) {
        this.userPreferenceService = userPreferenceService;
        this.deviceService = deviceService;
        this.ioService = ioService;
    }

    @EventListener
    public void onApplicationEvent() {
        this.userPreferenceService.findUserConfiguration()
                .flatMap(this::findDeviceByUserConfiguration)
                .ifPresent(ioService::startStream);
    }

    private Optional<Device> findDeviceByUserConfiguration(UserConfigurationDTO userConfigurationDTO) {
        return this.deviceService.findDeviceByPort(userConfigurationDTO.getDevicePort());
    }
}
