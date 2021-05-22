package com.arnaugarcia.ars.ui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class SpringbootJavaFxApplication extends Application {

    private ConfigurableApplicationContext context;

    @Override
    public void init() {
        this.context = new SpringApplicationBuilder() //(1)
                .sources(ArduinoRotateScreen.class)
                .run(getParameters().getRaw().toArray(new String[0]));
    }

    @Override
    public void start(Stage primaryStage) {
        context.publishEvent(new PrimaryStageInitializer.StageReadyEvent(primaryStage)); //(2)
    }

    @Override
    public void stop() throws Exception { //(3)
        this.context.close();
        Platform.exit();
    }
}
