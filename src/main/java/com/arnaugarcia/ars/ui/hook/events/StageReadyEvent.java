package com.arnaugarcia.ars.ui.hook.events;

import javafx.stage.Stage;
import org.springframework.context.ApplicationEvent;

public final class StageReadyEvent extends ApplicationEvent {
    public StageReadyEvent(Stage stage) {
        super(stage);
    }

    public Stage getStage() {
        return (Stage) getSource();
    }
}
