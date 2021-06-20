package com.arnaugarcia.ars.ui.service;

import com.arnaugarcia.ars.service.domain.Display;
import com.arnaugarcia.ars.service.service.dto.DeviceData;

public interface RotateService {

    void setSelectedDisplay(Display selectedDisplay);

    void rotateIfNeeded(DeviceData deviceData);

}
