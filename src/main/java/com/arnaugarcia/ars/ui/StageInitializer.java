package com.arnaugarcia.ars.ui;

import com.arnaugarcia.ars.ui.ArduinoRotateScreenUI.StageReadyEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class StageInitializer implements ApplicationListener<StageReadyEvent> {

    @Value("classpath:/views/main.fxml")
    private Resource mainViewResource;

    @Value("classpath:/assets/css/main.css")
    private Resource mainCSSResource;

    private final ApplicationContext applicationContext;

    public StageInitializer(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(StageReadyEvent stageReadyEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(mainViewResource.getURL());
            loader.setControllerFactory(applicationContext::getBean);
            Parent parent = loader.load();
            final Stage stage = stageReadyEvent.getStage();
            final Scene scene = new Scene(parent, 800, 600);
            scene.getStylesheets().add(mainCSSResource.getURL().toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
