package com.arnaugarcia.ars.ui.service;

import com.arnaugarcia.ars.ui.service.dto.UserConfigurationDTO;

import java.util.Optional;

public interface UserPreferenceService {

    Optional<String> findDisplay();

    Optional<String> findPort();

    void updateSelectedDisplay(String displayID);

    void updatePort(String port);
}
