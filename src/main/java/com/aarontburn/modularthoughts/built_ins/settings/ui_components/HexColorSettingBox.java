package com.aarontburn.modularthoughts.built_ins.settings.ui_components;

import com.aarontburn.modularthoughts.built_ins.settings.types.HexColorSetting;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class HexColorSettingBox extends StringSettingBox {

    protected static final String DEFAULT_COLOR_CSS =
            "-fx-border-radius: 5; -fx-background-radius: 5; " +
                    "-fx-background-color: accent-color; -fx-border-color: white;";

    public HexColorSettingBox(final HexColorSetting theSetting) {
        super(theSetting);
    }

    @Override
    protected Node createLeft() {
        final VBox colorDisplay = new VBox();
        colorDisplay.setAlignment(Pos.CENTER_LEFT);
        colorDisplay.setPrefWidth(LEFT_WIDTH);

        final Label colorLabel = new Label();
        colorLabel.setPrefHeight(48);
        colorLabel.setPrefWidth(LEFT_WIDTH);
        colorLabel.setStyle(DEFAULT_COLOR_CSS);
        colorDisplay.getChildren().add(colorLabel);

        return colorDisplay;
    }


}
