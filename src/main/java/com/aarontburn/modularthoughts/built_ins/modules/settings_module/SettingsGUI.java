package com.aarontburn.modularthoughts.built_ins.modules.settings_module;

import com.aarontburn.modularthoughts.module_builder.ModuleGUI;
import com.aarontburn.modularthoughts.module_builder.change_reporter.ModuleEvent;
import com.aarontburn.modularthoughts.module_builder.settings.Setting;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.Map;

public class SettingsGUI extends ModuleGUI {

    private static final String FXML_PATH = "fxml/settings-view.fxml";


    private VBox settingsTabGroup;
    private VBox settingsList;
    private Label currentlySelectedTab;

    public SettingsGUI() {
        super(FXML_PATH);
    }

    @Override
    public void initialize() {
        settingsTabGroup = (VBox) lookup("SMsettingsTabGroup");
        settingsList = (VBox) lookup("SMsettingsList");

        settingsTabGroup.getChildren().clear();
        settingsList.getChildren().clear();

        final ScrollPane leftScrollPane = (ScrollPane) lookup("SMleftScrollPane");
        leftScrollPane.setCache(false);
        for (final Node n : leftScrollPane.getChildrenUnmodifiable()) {
            n.setCache(false);
        }

        final ScrollPane rightScrollPane = (ScrollPane) lookup("SMrightScrollPane");
        rightScrollPane.setCache(false);
        for (final Node n : rightScrollPane.getChildrenUnmodifiable()) {
            n.setCache(false);
        }

    }

    @Override
    public void eventFired(ModuleEvent event) {
        switch (SettingsModule.ChangeEvents.valueOf(event.getEventName())) {
            case POPULATE_SETTINGS_LIST -> {
                final Map<String, List<Setting<?>>> payload = (Map<String, List<Setting<?>>>) event.getData();

                for (final String settingGroupName : payload.keySet()) {
                    final HBox hBox = new HBox();
                    hBox.setAlignment(Pos.CENTER);

                    final Label label = new Label(settingGroupName);
                    label.setStyle("-fx-font-size: 28;");

                    hBox.setOnMouseClicked(e -> {
                        if (currentlySelectedTab != null) {
                            currentlySelectedTab.setStyle("-fx-font-size: 28;");
                        }

                        currentlySelectedTab = label;
                        currentlySelectedTab.setStyle("-fx-font-size: 28; -fx-text-fill: accent-color;");

                        settingsList.getChildren().clear();
                        for (final Setting<?> setting : payload.get(settingGroupName)) {
                            settingsList.getChildren().add(setting.getUIComponent().createUI());
                        }
                    });

                    hBox.getChildren().add(label);
                    settingsTabGroup.getChildren().add(hBox);


                }


            }
        }
    }
}
