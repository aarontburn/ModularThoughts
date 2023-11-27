package com.aarontburn.modularthoughts.built_ins.settings.ui_components;

import com.aarontburn.modularthoughts.built_ins.settings.types.HexColorSetting;
import com.aarontburn.modularthoughts.module_builder.settings.Setting;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class HexColorSettingBox extends StringSettingBox {

    protected static final String DEFAULT_COLOR_CSS =
            "-fx-border-radius: 5; -fx-background-radius: 5; " +
                    "-fx-background-color: accent-color; -fx-border-color: white;";

    public HexColorSettingBox(final HexColorSetting theSetting) {
        super(theSetting);
    }

    @Override
    protected void createUI() {
        final VBox colorDisplay = new VBox();
        colorDisplay.setAlignment(Pos.CENTER_LEFT);
        colorDisplay.setPrefWidth(USABLE_WIDTH);

        final Label colorLabel = new Label();
        colorLabel.setPrefHeight(48);
        colorLabel.setPrefWidth(USABLE_WIDTH);
        colorLabel.setStyle(DEFAULT_COLOR_CSS);
        colorDisplay.getChildren().add(colorLabel);

        final Setting<?> setting = getSetting();
        final VBox vBox = new VBox(VERTICAL_SPACING);
        this.getChildren().addAll(colorDisplay, vBox);

        final HBox labelBox = new HBox(DEFAULT_SPACING);
        labelBox.setAlignment(Pos.BOTTOM_LEFT);

        final Label resetToDefaultLabel = new Label("↻");
        resetToDefaultLabel.setStyle(DEFAULT_NAME_STYLE);
        resetToDefaultLabel.setOnMouseClicked(e -> resetToDefault());

        final Label displayLabel = new Label(setting.getSettingName());
        displayLabel.setAlignment(Pos.BOTTOM_LEFT);
        labelBox.setStyle(DEFAULT_NAME_STYLE);
        labelBox.getChildren().addAll(resetToDefaultLabel, displayLabel);

        if (setting.getDescription() != null) {
            final Label descriptionLabel = new Label(setting.getDescription());
            descriptionLabel.setStyle(DEFAULT_DESC_STYLE);
            descriptionLabel.setMaxHeight(Double.MAX_VALUE);
            descriptionLabel.setAlignment(Pos.BOTTOM_LEFT);
            labelBox.getChildren().add(descriptionLabel);
        }
        vBox.getChildren().addAll(labelBox, createUsable());

    }


}
