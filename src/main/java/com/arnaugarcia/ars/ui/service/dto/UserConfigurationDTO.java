package com.arnaugarcia.ars.ui.service.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import static org.springframework.util.StringUtils.hasText;

@Getter
@Setter
@Builder
public class UserConfigurationDTO {
    private String devicePort;
    private String displayId;

    public UserConfigurationDTO() {
    }

    public UserConfigurationDTO(String devicePort, String displayId) {
        this.displayId = displayId;
        this.devicePort = devicePort;
    }

    public boolean existsPort() {
        return hasText(this.devicePort);
    }

    public boolean existsDisplay() {
        return hasText(this.displayId);
    }
}
