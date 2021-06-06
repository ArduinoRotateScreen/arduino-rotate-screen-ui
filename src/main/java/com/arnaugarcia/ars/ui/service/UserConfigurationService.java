package com.arnaugarcia.ars.ui.service;

import com.arnaugarcia.ars.ui.service.dto.UserConfigurationDTO;

import java.util.Optional;

public interface UserConfigurationService {
    Optional<UserConfigurationDTO> findUserConfiguration();
    void storeUserConfiguration();
}
