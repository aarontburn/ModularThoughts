package com.aarontburn.modularthoughts.built_ins.modules.home_module;

import com.aarontburn.modularthoughts.Logger;
import com.aarontburn.modularthoughts.module_builder.ModuleGUI;
import com.aarontburn.modularthoughts.module_builder.ModuleSettings;
import com.aarontburn.modularthoughts.module_builder.change_reporter.ModuleEvent;
import com.aarontburn.modularthoughts.module_builder.settings.Setting;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Label;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HomeGUI extends ModuleGUI {

    private static final String HOME_FXML_PATH = "fxml/home-view.fxml";

    private Label dateLabel, abbreviatedDateLabel;
    private Label standardTimeLabel, militaryTimeLabel;

    private final Map<String, Node> boundNodesByID = new HashMap<>();

    public HomeGUI() {
        super(HOME_FXML_PATH);
    }

    @Override
    public void initialize() {
        dateLabel = (Label) lookup("HMdateLabel");
        boundNodesByID.put("HMdateLabel", dateLabel);

        abbreviatedDateLabel = (Label) lookup("HMabbreviatedDateLabel");
        boundNodesByID.put("HMabbreviatedDateLabel", abbreviatedDateLabel);

        standardTimeLabel = (Label) lookup("HMstandardTimeLabel");
        boundNodesByID.put("HMstandardTimeLabel", standardTimeLabel);

        militaryTimeLabel = (Label) lookup("HMmilitaryTimeLabel");
        boundNodesByID.put("HMmilitaryTimeLabel", militaryTimeLabel);

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
            case APPLY_SETTINGS -> {
                final ModuleSettings settings = getModule().getSettings();
                for (final Setting<?> setting : settings.getSettingsList()) {
                    if (setting.getBoundNodeID() == null) {
                        continue;
                    }
                    final Node node = boundNodesByID.get(setting.getBoundNodeID());
                    if (node == null) {
                        Logger.log("WARNING: couldn't find node with ID: " + setting.getBoundNodeID());
                        continue;
                    }
                    node.setStyle("-fx-font-size: " + setting.getValue());
                }


            }
        }

    }
}
