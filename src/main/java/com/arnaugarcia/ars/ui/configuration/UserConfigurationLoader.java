package com.arnaugarcia.ars.ui.configuration;

import com.arnaugarcia.ars.ui.service.UserConfigurationService;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class UserConfigurationLoader {

    private final UserConfigurationService userConfigurationService;

    public UserConfigurationLoader(UserConfigurationService userConfigurationService) {
        this.userConfigurationService = userConfigurationService;
    }

    @EventListener
    public void onApplicationEvent(ApplicationStartedEvent event) {
        this.userConfigurationService.findUserConfiguration();
        // Find user configuration and start streaming data with IOService
    }
}
