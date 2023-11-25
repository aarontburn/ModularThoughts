package com.aarontburn.modularthoughts.home_module;

import com.aarontburn.modularthoughts.GUIHandler;
import com.aarontburn.modularthoughts.module.ModuleGUI;
import com.aarontburn.modularthoughts.module.change_reporter.ModuleEvent;
import javafx.application.Application;


public class HomeGUI extends ModuleGUI {


    private static final String HOME_FXML_PATH = "fxml/home-view.fxml";

    public HomeGUI() {
        super(HOME_FXML_PATH);





    }

    @Override
    public void initialize() {
        Application.launch(GUIHandler.class);
    }

    @Override
    public void eventFired(final ModuleEvent event) {

    }
}
