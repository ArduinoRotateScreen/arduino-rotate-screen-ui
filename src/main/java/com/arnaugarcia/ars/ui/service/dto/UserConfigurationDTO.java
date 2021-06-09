package com.arnaugarcia.ars.ui.service.dto;


import lombok.Builder;
import lombok.Getter;

@Getter
public class UserConfigurationDTO {
    private final String devicePort;

    @Builder
    public UserConfigurationDTO(String devicePort) {
        if (devicePort == null) {
            throw new IllegalArgumentException();
        }
        this.devicePort = devicePort;
    }
}
