package com.arnaugarcia.ars.ui.service.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class UserConfigurationDTO {
    private final String devicePort;
    private final String selectedDisplay;

    @Builder
    public UserConfigurationDTO(@NonNull String devicePort, @NonNull String selectedDisplay) {
        this.selectedDisplay = selectedDisplay;
        this.devicePort = devicePort;
    }
}
