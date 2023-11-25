package com.aarontburn.modularthoughts.settings_module;

import com.aarontburn.modularthoughts.module.ModuleGUI;
import com.aarontburn.modularthoughts.module.change_reporter.ModuleEvent;
import javafx.scene.layout.VBox;

public class SettingsGUI extends ModuleGUI {

    private static final String FXML_PATH = "fxml/settings-view.fxml";

    private VBox settingsTabGroup;
    public SettingsGUI() {
        super(FXML_PATH);
    }

    @Override
    public void initialize() {
        settingsTabGroup = (VBox) lookup("SMsettingsTabGroup");
    }

    @Override
    public void eventFired(ModuleEvent event) {

    }
}
