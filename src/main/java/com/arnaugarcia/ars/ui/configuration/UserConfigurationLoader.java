package com.arnaugarcia.ars.ui.configuration;

import com.arnaugarcia.ars.ui.service.UserPreferenceService;
import com.arnaugarcia.ars.ui.service.dto.UserConfigurationDTO;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserConfigurationLoader {

    private final UserPreferenceService userPreferenceService;

    public UserConfigurationLoader(UserPreferenceService userPreferenceService) {
        this.userPreferenceService = userPreferenceService;
    }

    @EventListener
    public void onApplicationEvent(ApplicationStartedEvent event) {
        final Optional<UserConfigurationDTO> userConfiguration = this.userPreferenceService.findUserConfiguration();
        //userConfiguration.ifPresent(streamData);
        // Find user configuration and start streaming data with IOService
    }
}
