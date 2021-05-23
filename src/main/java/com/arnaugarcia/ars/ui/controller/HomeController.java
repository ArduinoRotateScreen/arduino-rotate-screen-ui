package com.arnaugarcia.ars.ui.controller;

import com.arnaugarcia.ars.components.display.DisplayComponent;
import com.arnaugarcia.ars.service.domain.Display;
import com.arnaugarcia.ars.service.service.DisplayService;
import com.arnaugarcia.ars.ui.component.Route;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static java.util.stream.Collectors.toList;

@Component
@FxmlView("/views/home.fxml")
public class HomeController extends Route implements Initializable {

    private final DisplayService displayService;

    @FXML
    public HBox displays;

    public HomeController(DisplayService displayService) {
        this.displayService = displayService;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appendDisplays();
    }

    private void appendDisplays() {
        final List<DisplayComponent> displays = displayService.findDisplays()
                .stream()
                .map(this::buildDisplayComponent)
                .collect(toList());
        this.displays.getChildren().addAll(displays);
    }

    private DisplayComponent buildDisplayComponent(Display display) {
        return DisplayComponent.builder()
                .displayWidth(display.getWide() / 10)
                .displayHeight(display.getHeight() / 10)
                .identifier("hey")
                .mainDisplay(display.getMain())
                .build();
    }
}
