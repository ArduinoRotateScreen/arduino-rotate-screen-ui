package com.arnaugarcia.ars.ui.service;

import com.arnaugarcia.ars.ui.service.dto.UserConfigurationDTO;

import java.util.Optional;

public interface UserPreferenceService {

    Optional<UserConfigurationDTO> findUserConfiguration();

    void storeUserConfiguration(UserConfigurationDTO userConfigurationDTO);
}
