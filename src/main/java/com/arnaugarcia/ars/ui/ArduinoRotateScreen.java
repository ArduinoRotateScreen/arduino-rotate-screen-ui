package com.arnaugarcia.ars.ui;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ArduinoRotateScreen {

    public static void main(String[] args) {
        Application.launch(ArduinoRotateScreenUI.class, args);
    }
}