package com.arnaugarcia.ars.ui;

import com.arnaugarcia.ars.ui.ArduinoRotateScreenUI.StageReadyEvent;
import javafx.stage.Stage;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class StageInitializer implements ApplicationListener<StageReadyEvent> {

    @Override
    public void onApplicationEvent(StageReadyEvent stageReadyEvent) {
        final Stage stage = stageReadyEvent.getStage();
    }
}
