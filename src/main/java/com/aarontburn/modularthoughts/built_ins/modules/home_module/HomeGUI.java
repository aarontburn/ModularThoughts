package com.aarontburn.modularthoughts.built_ins.modules.home_module;

import com.aarontburn.modularthoughts.Logger;
import com.aarontburn.modularthoughts.Main;
import com.aarontburn.modularthoughts.module_builder.Module;
import com.aarontburn.modularthoughts.module_builder.ModuleGUI;
import com.aarontburn.modularthoughts.module_builder.ModuleSettings;
import com.aarontburn.modularthoughts.module_builder.change_reporter.ModuleEvent;
import com.aarontburn.modularthoughts.module_builder.settings.Setting;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import javax.annotation.Nonnull;
import java.util.*;


public class HomeGUI extends ModuleGUI {

    private static final String HOME_FXML_PATH = "fxml/home-view.fxml";
    private final Map<String, Node> boundNodesByID = new HashMap<>();
    private Label fullDateLabel, abbreviatedDateLabel;
    private Label standardTimeLabel, militaryTimeLabel;
    private VBox labelContainer;

    public HomeGUI(@Nonnull final Module theModule) {
        super(theModule, HOME_FXML_PATH);
    }

    @Override
    public void initialize() {
        fullDateLabel = (Label) lookup("HMdateLabel");
        boundNodesByID.put("HMdateLabel", fullDateLabel);

        abbreviatedDateLabel = (Label) lookup("HMabbreviatedDateLabel");
        boundNodesByID.put("HMabbreviatedDateLabel", abbreviatedDateLabel);

        standardTimeLabel = (Label) lookup("HMstandardTimeLabel");
        boundNodesByID.put("HMstandardTimeLabel", standardTimeLabel);

        militaryTimeLabel = (Label) lookup("HMmilitaryTimeLabel");
        boundNodesByID.put("HMmilitaryTimeLabel", militaryTimeLabel);

        labelContainer = (VBox) lookup("HMlabelContainer");
    }

    @Override
    public void eventFired(final ModuleEvent event) {
        switch (HomeModule.ChangeEvents.valueOf(event.getEventName())) {
            case DATE_CHANGED -> Platform.runLater(() -> {
                final String[] payload = (String[]) event.getData();
                fullDateLabel.setText(payload[0]);
                abbreviatedDateLabel.setText(payload[1]);
            });
            case TIME_CHANGED -> Platform.runLater(() -> {
                final String[] payload = (String[]) event.getData();
                standardTimeLabel.setText(payload[0]);
                militaryTimeLabel.setText(payload[1]);
            });
            case APPLY_SETTINGS -> {
                final ModuleSettings settings = getModule().getSettings();

                final String[] displayOrder
                        = ((String) settings.getSettingByName("Display Order").getValue())
                        .split("");

                final List<Node> newOrder = new ArrayList<>();
                for (final String s : displayOrder) {
                    switch (s) {
                        case "1" -> newOrder.add(fullDateLabel);
                        case "2" -> newOrder.add(abbreviatedDateLabel);
                        case "3" -> newOrder.add(standardTimeLabel);
                        case "4" -> newOrder.add(militaryTimeLabel);
                        case " " -> newOrder.add(null);
                        default ->
                            System.out.println("Invalid display order passed: " + s);
                    }
                }

                labelContainer.getChildren().clear();
                for (final Node node : newOrder) {
                    if (node == null) {
                        final Label spacer = new Label();
                        spacer.setStyle("-fx-font-size: 30;");
                        labelContainer.getChildren().add(spacer);
                        continue;
                    }
                    labelContainer.getChildren().add(node);
                }


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
