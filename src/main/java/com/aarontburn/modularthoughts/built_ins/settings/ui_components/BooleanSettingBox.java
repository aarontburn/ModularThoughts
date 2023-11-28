package com.aarontburn.modularthoughts.built_ins.settings.ui_components;

import com.aarontburn.modularthoughts.module_builder.settings.Setting;
import com.aarontburn.modularthoughts.module_builder.settings.SettingBox;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class BooleanSettingBox extends SettingBox<Boolean> {

    private static final String SELECTED_STYLE = DEFAULT_NAME_STYLE;
    private static final String UNSELECTED_STYLE = DEFAULT_NAME_STYLE + " -fx-text-fill: gray;";
    private Label trueLabel;
    private Label falseLabel;

    public BooleanSettingBox(final Setting<Boolean> theSetting) {
        super(theSetting);
        updateSetting(theSetting.getValue());
    }

    @Override
    protected Node createLeft() {
        final HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setPrefWidth(LEFT_WIDTH);

        trueLabel = new Label("On");
        trueLabel.setStyle(SELECTED_STYLE);
        trueLabel.setOnMouseClicked(e -> updateSetting(true));

        final Label separator = new Label("/");
        separator.setStyle(SELECTED_STYLE + "-fx-text-fill: accent-color");

        falseLabel = new Label("Off");
        falseLabel.setStyle(SELECTED_STYLE);
        falseLabel.setOnMouseClicked(e -> updateSetting(false));

        hBox.getChildren().addAll(trueLabel, separator, falseLabel);
        return hBox;
    }

    @Override
    protected void updateDisplayValue() {
        final boolean bool = getSetting().getValue();
        trueLabel.setStyle(bool ? SELECTED_STYLE : UNSELECTED_STYLE);
        falseLabel.setStyle(bool ? UNSELECTED_STYLE : SELECTED_STYLE);
    }



}
