package com.arnaugarcia.ars.service.component;

import javafx.collections.ObservableMap;
import javafx.scene.layout.Pane;

public class Menu extends Pane {

    private ObservableMap<String, String> routes;

    public Menu() {
        System.out.println(routes);
    }

    public ObservableMap<String, String> getRoutes() {
        return routes;
    }
}
