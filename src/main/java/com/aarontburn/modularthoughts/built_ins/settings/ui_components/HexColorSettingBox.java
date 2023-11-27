package com.aarontburn.modularthoughts.built_ins.settings.ui_components;

import com.aarontburn.modularthoughts.built_ins.settings.types.HexColorSetting;
import com.aarontburn.modularthoughts.built_ins.settings.types.StringSetting;
import com.aarontburn.modularthoughts.module_builder.settings.Setting;
import com.aarontburn.modularthoughts.module_builder.settings.SettingBox;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class HexColorSettingBox extends SettingBox<String> {

    protected static final String DEFAULT_COLOR_CSS =
            "-fx-border-radius: 5; -fx-background-color: accent-color; -fx-border-color: white;";

    protected static final int VERTICAL_SPACING = 8;

    public HexColorSettingBox(final HexColorSetting theSetting) {
        super(theSetting);
    }

    private TextField inputTextField;

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

    @Override
    protected Node createUsable() {
        inputTextField = new TextField();
        inputTextField.setText(getSetting().getValue().toString());
        inputTextField.setAlignment(Pos.CENTER_LEFT);
        inputTextField.setMaxHeight(Double.MAX_VALUE);
        inputTextField.setStyle(DEFAULT_NAME_STYLE);
        inputTextField.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                finishedEditing();
            }
        });
        inputTextField.focusedProperty().addListener((observableValue, b, isFocused) -> {
            if (!isFocused) {
                finishedEditing();
            }
        });

        return inputTextField;
    }

    protected boolean isInputFormValid(final String input) {
        if (getSetting().getInputValidator() != null) {
            return getSetting().getInputValidator().test(input);
        }

        return !input.isEmpty();
    }

    private void finishedEditing() {
        if (!isInputFormValid(inputTextField.getText())) {
            undo();
            return;
        }
        getSetting().onValueChanged(inputTextField.getText());
    }

    @Override
    public void resetToDefault() {
        inputTextField.setText(String.valueOf(getSetting().getDefault()));
        getSetting().onValueChanged(inputTextField.getText());
    }

    @Override
    public void undo() {
        inputTextField.setText(String.valueOf(getSetting().getValue()));
        getSetting().onValueChanged(inputTextField.getText());
    }

    @Override
    protected Object getRawValue() {
        return inputTextField.getText();
    }


}
