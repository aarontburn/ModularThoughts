package com.aarontburn.modularthoughts.built_ins.settings.ui_components;

import com.aarontburn.modularthoughts.module_builder.settings.Setting;
import com.aarontburn.modularthoughts.module_builder.settings.SettingBox;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class StringSettingBox extends SettingBox<String> {

    protected static final int VERTICAL_SPACING = 8;
    private TextField inputTextField;

    public StringSettingBox(final Setting<String> theSetting) {
        super(theSetting);
    }

    @Override
    protected Node createRight() {
        final Setting<?> setting = getSetting();
        final VBox vBox = new VBox(VERTICAL_SPACING);

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

        inputTextField = new TextField();
        inputTextField.setText(getSetting().getValue());
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

        vBox.getChildren().addAll(labelBox, inputTextField);
        return vBox;
    }

    @Override
    protected Node createLeft() {
        final Pane spacer = new Pane();
        spacer.setPrefWidth(USABLE_WIDTH);
        return spacer;
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
