package com.arnaugarcia.ars.ui.service;

import com.arnaugarcia.ars.service.domain.Device;

public interface IOService {

    void startStream(Device device);

    void stopStream();

}
