package com.arnaugarcia.ars.ui.service.impl;

import com.arnaugarcia.ars.ui.service.UserConfigurationService;
import com.arnaugarcia.ars.ui.service.dto.UserConfigurationDTO;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Optional;
import java.util.prefs.Preferences;

import static java.util.prefs.Preferences.userRoot;

@Service
public class UserConfigurationServiceImpl implements UserConfigurationService {

    private final Preferences preferences = userRoot();
    private UserConfigurationDTO userConfiguration;

    @PostConstruct
    private void initUserData() {
        System.out.println("Init user data");
    }

    @Override
    public Optional<UserConfigurationDTO> findUserConfiguration() {
        return Optional.empty();
    }

    @Override
    public void storeUserConfiguration() {

    }
}
