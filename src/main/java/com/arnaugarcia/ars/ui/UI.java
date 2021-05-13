package com.arnaugarcia.ars.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class UI extends Application {

    private Stage window;

    public static void main(String[] args) {
       launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/javafx/views/main.fxml"));
        Scene scene = new Scene(root, 800, 500);
        scene.getStylesheets().add(getClass().getResource("/javafx/assets/css/main.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
        this.window = primaryStage;
    }
}
