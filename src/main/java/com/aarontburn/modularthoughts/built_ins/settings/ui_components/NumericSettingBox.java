package com.aarontburn.modularthoughts.built_ins.settings.ui_components;

import com.aarontburn.modularthoughts.Helper;
import com.aarontburn.modularthoughts.built_ins.settings.types.NumericSetting;
import com.aarontburn.modularthoughts.module_builder.settings.Setting;
import com.aarontburn.modularthoughts.module_builder.settings.SettingBox;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

public class NumericSettingBox extends SettingBox<Number> {


    private TextField inputTextField;

    public NumericSettingBox(final Setting<Number> theSetting) {
        super(theSetting);
    }

    @Override
    protected Node createUsable() {
        inputTextField = new TextField();
        inputTextField.setText(String.valueOf(getSetting().getValue()));
        inputTextField.setAlignment(Pos.CENTER);
        inputTextField.setMaxHeight(Double.MAX_VALUE);
        inputTextField.setStyle(DEFAULT_NAME_STYLE);
        inputTextField.setPrefWidth(USABLE_WIDTH);

        inputTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d*)?")) {
                inputTextField.setText(oldValue);
            }
        });

        inputTextField.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                finishedEditing();
                super.requestFocus();
            }
        });

        inputTextField.focusedProperty().addListener((observableValue, b, isFocused) -> {
            if (!isFocused) {
                finishedEditing();
            }
        });
        return inputTextField;
    }


    private void finishedEditing() {

        if (!getSetting().getInputValidator().test(inputTextField.getText())) {
            undo();
            return;
        }

        final double roundedDouble = Helper.roundDouble(Double.parseDouble(inputTextField.getText()), 1);

        inputTextField.setText(String.valueOf(roundedDouble));
        getSetting().onValueChanged(roundedDouble);
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
