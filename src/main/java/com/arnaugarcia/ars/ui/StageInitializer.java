package com.arnaugarcia.ars.ui;

import com.arnaugarcia.ars.ui.ArduinoRotateScreenUI.StageReadyEvent;
import com.arnaugarcia.ars.ui.configuration.BeanBuilderFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Field;

@Component
public class StageInitializer implements ApplicationListener<StageReadyEvent> {

    @Value("classpath:/views/main.fxml")
    private Resource mainViewResource;

    @Value("classpath:/assets/css/main.css")
    private Resource mainCSSResource;

    private final BeanBuilderFactory beanBuilderFactory;

    public StageInitializer(BeanBuilderFactory beanBuilderFactory) {
        this.beanBuilderFactory = beanBuilderFactory;
    }

    @Override
    public void onApplicationEvent(StageReadyEvent stageReadyEvent) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setBuilderFactory(beanBuilderFactory);
            Parent parent = loader.load(mainViewResource.getURL());
            final Stage stage = stageReadyEvent.getStage();
            final Scene scene = new Scene(parent, 800, 600);
            scene.getStylesheets().add(mainCSSResource.getURL().toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
