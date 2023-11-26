package com.aarontburn.modularthoughts.built_ins.settings.ui_components;

import com.aarontburn.modularthoughts.built_ins.settings.types.NumericSetting;
import com.aarontburn.modularthoughts.module_builder.settings.SettingBox;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

public class NumericSettingBox extends SettingBox<Number> {


    private TextField inputTextField;

    public NumericSettingBox(final NumericSetting theSetting) {
        super(theSetting);
    }

    @Override
    public Node createUsable() {
        // TODO: Restrict this to numbers

        inputTextField = new TextField();
        inputTextField.setText(String.valueOf(getSetting().getValue()));
        inputTextField.setAlignment(Pos.CENTER);
        inputTextField.setMaxHeight(Double.MAX_VALUE);
        inputTextField.setStyle(DEFAULT_NAME_STYLE);
        inputTextField.setPrefWidth(USABLE_WIDTH);

        inputTextField.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                getSetting().onValueChanged(Double.valueOf(inputTextField.getText()));
            }
        });

        inputTextField.focusedProperty().addListener((observableValue, b, isFocused) -> {
            if (!isFocused) {
                getSetting().onValueChanged(inputTextField.getText());
            }
        });
        return inputTextField;
    }

    @Override
    public void resetToDefault() {
        inputTextField.setText(String.valueOf(getSetting().getDefault()));
        getSetting().onValueChanged(String.valueOf(getSetting().getDefault()));
    }


}
