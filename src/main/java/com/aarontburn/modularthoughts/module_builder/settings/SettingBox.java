package com.aarontburn.modularthoughts.module_builder.settings;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public abstract class SettingBox<T> extends HBox {

    protected static final double DEFAULT_SPACING = 24;

    protected static final int DEFAULT_NAME_FONT_SIZE = 24;
    protected static final int DEFAULT_DESC_FONT_SIZE = 16;

    protected static final String DEFAULT_NAME_STYLE = String.format("-fx-font-size: %s;", DEFAULT_NAME_FONT_SIZE);
    protected static final String DEFAULT_DESC_STYLE = String.format("-fx-font-size: %s;", DEFAULT_DESC_FONT_SIZE);

    
    protected static final int USABLE_WIDTH = 115;

    private final Setting<?> setting;

    public SettingBox(final Setting<?> theSetting) {
        super(DEFAULT_SPACING);
        this.setting = theSetting;
        createUI();
    }

    protected void createUI() {
        this.getChildren().add(createUsable());

        final VBox labelBox = new VBox();
        labelBox.setAlignment(Pos.CENTER_LEFT);

        final HBox topBox = new HBox(DEFAULT_SPACING);
        labelBox.getChildren().add(topBox);

        final Label resetToDefaultLabel = new Label("↻");
        resetToDefaultLabel.setStyle(DEFAULT_NAME_STYLE);
        resetToDefaultLabel.setOnMouseClicked(e -> resetToDefault());

        final Label nameLabel = new Label(setting.getSettingName());
        nameLabel.setStyle(DEFAULT_NAME_STYLE);

        topBox.getChildren().addAll(resetToDefaultLabel, nameLabel);

        if (!setting.getDescription().isEmpty()) {
            final Label descriptionLabel = new Label(setting.getDescription());
            descriptionLabel.setStyle(DEFAULT_DESC_STYLE);
            labelBox.getChildren().add(descriptionLabel);
        }
        this.getChildren().add(labelBox);
    }

    public abstract Node createUsable();

    public abstract void resetToDefault();

    protected Setting<?> getSetting() {
        return this.setting;
    }




}
