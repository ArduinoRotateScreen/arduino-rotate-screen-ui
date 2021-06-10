package com.arnaugarcia.ars.ui.service;

import com.arnaugarcia.ars.service.domain.Device;

public interface IOService {

    /**
     * Starts steaming and emits events to application context. If the selected device has the same port it won't do nothing
     * @param device the device to stream data of
     */
    void streamDataOf(Device device);

    boolean isStreaming();

    void stopStream();

}
