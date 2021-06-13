package com.arnaugarcia.ars.ui.service.dto;


import lombok.Builder;
import lombok.Getter;

import static java.util.Objects.isNull;

@Getter
public class UserConfigurationDTO {
    private final String devicePort;
    private final String selectedDisplay;

    @Builder
    public UserConfigurationDTO(String devicePort, String selectedDisplay) {
        if (isNull(devicePort) || isNull(selectedDisplay)) {
            throw new IllegalArgumentException();
        }
        this.selectedDisplay = selectedDisplay;
        this.devicePort = devicePort;
    }
}
