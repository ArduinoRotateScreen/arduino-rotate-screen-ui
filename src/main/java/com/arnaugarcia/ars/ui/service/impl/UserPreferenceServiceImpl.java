package com.arnaugarcia.ars.ui.service.impl;

import com.arnaugarcia.ars.ui.service.UserPreferenceService;
import com.arnaugarcia.ars.ui.service.dto.UserConfigurationDTO;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Optional;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import static java.util.Optional.ofNullable;
import static java.util.prefs.Preferences.userRoot;
import static org.apache.logging.log4j.util.Strings.isEmpty;

@Service
public class UserPreferenceServiceImpl implements UserPreferenceService {

    private final String USER_PREFERENCES_NODE = "ARS";
    private final String DEVICE_PORT_KEY = "PORT";
    private final String DISPLAY_KEY = "DISPLAY";

    private final Preferences preferences = userRoot().node(USER_PREFERENCES_NODE);
    private UserConfigurationDTO userConfiguration = new UserConfigurationDTO();

    @PostConstruct
    private void loadUserPreferences() {
        try {
            if (preferences.nodeExists(USER_PREFERENCES_NODE)) {
                setUserConfiguration(buildUserPreferences(preferences));
            }
        } catch (BackingStoreException e) {
            e.printStackTrace();
        }
    }

    private UserConfigurationDTO buildUserPreferences(Preferences userPreferences) {
        return UserConfigurationDTO.builder()
                .devicePort(userPreferences.get(DEVICE_PORT_KEY, null))
                .displayId(userPreferences.get(DISPLAY_KEY, null))
                .build();
    }

    private void storeUserConfiguration(UserConfigurationDTO userConfigurationDTO) {
        if (!isEmpty(userConfigurationDTO.getDevicePort())) {
            preferences.put(DEVICE_PORT_KEY, userConfigurationDTO.getDevicePort());
        }
        if (!isEmpty(userConfigurationDTO.getDisplayId())) {
            preferences.put(DISPLAY_KEY, userConfigurationDTO.getDisplayId());
        }
        setUserConfiguration(userConfigurationDTO);
    }

    @Override
    public Optional<String> findDisplay() {
        return ofNullable(userConfiguration.getDisplayId());
    }

    @Override
    public Optional<String> findPort() {
        return ofNullable(userConfiguration.getDevicePort());
    }

    @Override
    public void updateSelectedDisplay(String displayID) {
        userConfiguration.setDisplayId(displayID);
        storeUserConfiguration(userConfiguration);
    }

    @Override
    public void updatePort(String port) {
        userConfiguration.setDevicePort(port);
        storeUserConfiguration(userConfiguration);
    }

    private void setUserConfiguration(UserConfigurationDTO userConfiguration) {
        this.userConfiguration = userConfiguration;
    }

}
