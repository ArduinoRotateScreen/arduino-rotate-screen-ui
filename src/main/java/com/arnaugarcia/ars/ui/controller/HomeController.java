package com.arnaugarcia.ars.ui.controller;

import com.arnaugarcia.ars.components.display.DisplayComponent;
import com.arnaugarcia.ars.service.domain.Display;
import com.arnaugarcia.ars.service.service.DisplayService;
import com.arnaugarcia.ars.ui.component.Route;
import com.arnaugarcia.ars.ui.service.UserPreferenceService;
import com.arnaugarcia.ars.ui.service.dto.UserConfigurationDTO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static java.util.stream.Collectors.toList;

@Component
@FxmlView("/views/home.fxml")
public class HomeController extends Route implements Initializable {

    private final DisplayService displayService;
    private final UserPreferenceService userPreferenceService;

    @FXML
    public HBox displayContainer;

    public HomeController(DisplayService displayService, UserPreferenceService userPreferenceService) {
        this.displayService = displayService;
        this.userPreferenceService = userPreferenceService;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDisplays();
    }

    private void loadDisplays() {
        appendDisplays(findAndTransformDisplays());
    }

    private void appendDisplays(List<DisplayComponent> displays) {
        this.displayContainer.getChildren().clear();
        this.displayContainer.getChildren().addAll(displays);
    }

    private List<DisplayComponent> findAndTransformDisplays() {
        return displayService.findDisplays()
                .stream()
                .map(this::buildDisplayComponent)
                .collect(toList());
    }

    private DisplayComponent buildDisplayComponent(Display display) {
        return DisplayComponent.builder()
                .displayWidth(display.getWide() / 10)
                .displayHeight(display.getHeight() / 10)
                .mainDisplay(display.getMain())
                .selected(isSelectedDisplay(display))
                .onClickAction(mouseEvent -> onDisplayClick(display))
                .build();
    }

    private boolean isSelectedDisplay(Display display) {
        final Optional<String> persistedDisplay = userPreferenceService.findDisplay();
        return persistedDisplay.isPresent() && persistedDisplay.get().equals(display.getId().toString());
    }

    private void onDisplayClick(Display display) {
        userPreferenceService.updateSelectedDisplay(display.getId().toString());
        loadDisplays();
    }
}
