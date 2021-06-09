package com.arnaugarcia.ars.ui.service.impl;

import com.arnaugarcia.ars.ui.service.UserPreferenceService;
import com.arnaugarcia.ars.ui.service.dto.UserConfigurationDTO;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Optional;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.prefs.Preferences.userRoot;

@Service
public class UserPreferenceServiceImpl implements UserPreferenceService {

    private final String USER_PREFERENCES_NODE = "ARS";
    private final Preferences preferences = userRoot().node(USER_PREFERENCES_NODE);
    private Optional<UserConfigurationDTO> userConfiguration = empty();
    private final String DEVICE_PORT_KEY = "PORT";

    @PostConstruct
    private void loadUserPreferences() {
        try {
            if (preferences.nodeExists(USER_PREFERENCES_NODE)) {
                setUserConfiguration(buildUserPreferences(preferences));
            } else {
                preferences.node(USER_PREFERENCES_NODE);
            }
        } catch (BackingStoreException e) {
            e.printStackTrace();
        }
    }

    private UserConfigurationDTO buildUserPreferences(Preferences userPreferences) {
        return UserConfigurationDTO.builder()
                .devicePort(userPreferences.get(DEVICE_PORT_KEY, null))
                .build();
    }

    @Override
    public Optional<UserConfigurationDTO> findUserConfiguration() {
        return userConfiguration;
    }

    @Override
    public void storeUserConfiguration(UserConfigurationDTO userConfigurationDTO) {
        preferences.put(DEVICE_PORT_KEY, userConfigurationDTO.getDevicePort());
        setUserConfiguration(userConfigurationDTO);
    }

    private void setUserConfiguration(UserConfigurationDTO userConfiguration) {
        this.userConfiguration = of(userConfiguration);
    }
}
