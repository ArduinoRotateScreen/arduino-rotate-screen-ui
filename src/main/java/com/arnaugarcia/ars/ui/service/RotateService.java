package com.arnaugarcia.ars.ui.service;

import com.arnaugarcia.ars.service.domain.Display;
import com.arnaugarcia.ars.service.service.dto.DeviceData;
import com.arnaugarcia.ars.ui.service.dto.DeviceThreshold;

public interface RotateService {

    Display getRotateDisplay();

    void setRotateDisplay(Display display);

    void setThreshold(DeviceThreshold threshold);

    void rotateIfNeeded(DeviceData deviceData);

}
