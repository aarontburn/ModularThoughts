package com.aarontburn.modularthoughts.built_ins.settings.ui_components;

import com.aarontburn.modularthoughts.built_ins.settings.types.BooleanSetting;
import com.aarontburn.modularthoughts.module_builder.settings.SettingBox;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class BooleanSettingBox extends SettingBox<Boolean> {

    private static final String SELECTED_STYLE = DEFAULT_NAME_STYLE;
    private static final String UNSELECTED_STYLE = DEFAULT_NAME_STYLE + " -fx-text-fill: gray;";

    private boolean selectedValue;

    private Label trueLabel;
    private Label falseLabel;

    public BooleanSettingBox(final BooleanSetting theSetting) {
        super(theSetting);
        setValue(theSetting.getValue());
    }


    @Override
    public Node createUsable() {
        final HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setPrefWidth(USABLE_WIDTH);

        trueLabel = new Label("On");
        trueLabel.setStyle(SELECTED_STYLE);
        trueLabel.setOnMouseClicked(e -> {
            setValue(true);
            valueChanged();
        });

        final Label separator = new Label("/");
        separator.setStyle(SELECTED_STYLE + "-fx-text-fill: accent-color");

        falseLabel = new Label("Off");
        falseLabel.setStyle(SELECTED_STYLE);
        falseLabel.setOnMouseClicked(e -> {
            setValue(false);
            valueChanged();
        });

        hBox.getChildren().addAll(trueLabel, separator, falseLabel);
        return hBox;
    }

    @Override
    public void resetToDefault() {
        setValue((boolean) getSetting().getDefault());
        valueChanged();
    }


    private void setValue(final boolean theBoolean) {
        selectedValue = theBoolean;
        if (theBoolean) { // ON was pressed
            trueLabel.setStyle(SELECTED_STYLE);
            falseLabel.setStyle(UNSELECTED_STYLE);
            return;
        }
        trueLabel.setStyle(UNSELECTED_STYLE);
        falseLabel.setStyle(SELECTED_STYLE);
    }

    private void valueChanged() {
        getSetting().onValueChanged(selectedValue);
    }
}
