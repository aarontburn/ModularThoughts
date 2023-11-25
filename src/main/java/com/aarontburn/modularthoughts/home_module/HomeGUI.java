package com.aarontburn.modularthoughts.home_module;

import com.aarontburn.modularthoughts.module.Module;
import com.aarontburn.modularthoughts.module.ModuleGUI;
import com.aarontburn.modularthoughts.module.change_reporter.ModuleEvent;
import javafx.application.Platform;
import javafx.scene.control.Label;


public class HomeGUI extends ModuleGUI {

    private static final String HOME_FXML_PATH = "fxml/home-view.fxml";

    private Label dateLabel, abbreviatedDateLabel;
    private Label standardTimeLabel, militaryTimeLabel;

    public HomeGUI() {
        super(HOME_FXML_PATH);
    }



    @Override
    public void initialize() {
        dateLabel = (Label) lookup("HMdateLabel");
        abbreviatedDateLabel = (Label) lookup("HMabbreviatedDateLabel");
        standardTimeLabel = (Label) lookup("HMstandardTimeLabel");
        militaryTimeLabel = (Label) lookup("HMmilitaryTimeLabel");
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void eventFired(final ModuleEvent event) {
        switch (HomeModule.ChangeEvents.valueOf(event.getEventName())) {
            case DATE_CHANGED -> Platform.runLater(() -> {
                final String[] payload = (String[]) event.getData();
                dateLabel.setText(payload[0]);
                abbreviatedDateLabel.setText(payload[1]);
            });
            case TIME_CHANGED -> Platform.runLater(() -> {
                final String[] payload = (String[]) event.getData();
                standardTimeLabel.setText(payload[0]);
                militaryTimeLabel.setText(payload[1]);
            });
        }

    }
}
