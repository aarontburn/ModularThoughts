package com.aarontburn.modularthoughts.module;

import com.aarontburn.modularthoughts.GUIHandler;
import com.aarontburn.modularthoughts.Main;
import com.aarontburn.modularthoughts.module.change_reporter.ModuleListener;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

public abstract class ModuleGUI implements ModuleListener {

    private final String fxmlPath;

    public ModuleGUI(final String theFXMLPath) {
        this.fxmlPath = theFXMLPath;
    }



    public abstract void initialize();

}
