package com.aarontburn.modularthoughts.built_ins.settings.ui_components;

import com.aarontburn.modularthoughts.Helper;
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
    protected Node createLeft() {
        inputTextField = new TextField();
        inputTextField.setText(String.valueOf(getSetting().getValue()));
        inputTextField.setAlignment(Pos.CENTER);
        inputTextField.setMaxHeight(Double.MAX_VALUE);
        inputTextField.setStyle(DEFAULT_NAME_STYLE);
        inputTextField.setPrefWidth(LEFT_WIDTH);

        inputTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d*)?")) {
                inputTextField.setText(oldValue);
            }
        });

        inputTextField.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                getSetting().setValue(inputTextField.getText());
                super.requestFocus();
            }
        });

        inputTextField.focusedProperty().addListener((observableValue, b, isFocused) -> {
            if (!isFocused) {
                getSetting().setValue(inputTextField.getText());
            }
        });
        return inputTextField;
    }

    @Override
    protected void updateDisplayValue() {
        inputTextField.setText(String.valueOf(getSetting().getValue().doubleValue()));
    }

}
