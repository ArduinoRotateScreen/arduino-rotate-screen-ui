package com.arnaugarcia.ars.ui.hook.events;

import com.arnaugarcia.ars.service.service.dto.DeviceData;
import org.springframework.context.ApplicationEvent;

public final class DeviceDataEvent extends ApplicationEvent {
    public DeviceDataEvent(DeviceData deviceData) {
        super(deviceData);
    }

    public DeviceData getData() {
        return (DeviceData) getSource();
    }
}
