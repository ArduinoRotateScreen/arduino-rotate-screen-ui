package com.arnaugarcia.ars.ui.hook;

import com.arnaugarcia.ars.ui.controller.MainController;
import com.arnaugarcia.ars.ui.hook.events.StageReadyEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class PrimaryStageInitializer implements ApplicationListener<StageReadyEvent> {

    @Value("classpath:/assets/css/main.css")
    private Resource mainCSSResource;

    private final FxWeaver fxWeaver;

    @Autowired
    public PrimaryStageInitializer(FxWeaver fxWeaver) { //(1)
        this.fxWeaver = fxWeaver;
    }

    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        try {
            Stage stage = event.getStage();
            Scene scene = new Scene(fxWeaver.loadView(MainController.class), 900, 600);
            scene.getStylesheets().add(mainCSSResource.getURL().toExternalForm());
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
