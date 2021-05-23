package com.arnaugarcia.ars.ui.controller.layouts;

import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
@FxmlView("/layout/footer.fxml")
public class FooterController {
    @FXML
    public Hyperlink website;

    public void openWebsite() {
        try {
            Runtime rt = Runtime.getRuntime();
            String url = "https://arnaugarcia.com";
            rt.exec("open " + url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
