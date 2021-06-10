package com.arnaugarcia.ars.ui.service.impl;

import com.arnaugarcia.ars.service.domain.Device;
import com.arnaugarcia.ars.service.service.DeviceService;
import com.arnaugarcia.ars.service.service.dto.DeviceDataListener;
import com.arnaugarcia.ars.ui.service.IOService;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class IOServiceImpl implements IOService {

    private final ApplicationContext applicationContext;
    private final DeviceService deviceService;

    public IOServiceImpl(ApplicationContext applicationContext, DeviceService deviceService) {
        this.applicationContext = applicationContext;
        this.deviceService = deviceService;
    }

    @Override
    public void startStream(Device device) {
        this.deviceService.attachListener(device, (DeviceDataListener) deviceData -> {
            //applicationContext.publishEvent();
        });
    }

    @Override
    public void stopStream() {

    }
}
