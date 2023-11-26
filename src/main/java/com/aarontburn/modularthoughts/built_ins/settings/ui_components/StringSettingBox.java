package com.aarontburn.modularthoughts.built_ins.settings.ui_components;

import com.aarontburn.modularthoughts.module_builder.settings.Setting;
import com.aarontburn.modularthoughts.built_ins.settings.types.StringSetting;
import com.aarontburn.modularthoughts.module_builder.settings.SettingBox;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class StringSettingBox extends SettingBox<String> {

    private static final int VERTICAL_SPACING = 8;

    private TextField inputTextField;


    public StringSettingBox(final StringSetting theSetting) {
        super(theSetting);
    }

    @Override
    protected void createUI() {
        final Pane spacer = new Pane();
        spacer.setPrefWidth(USABLE_WIDTH);
        this.getChildren().add(spacer);

        final Setting<?> setting = getSetting();
        final VBox vBox = new VBox(VERTICAL_SPACING);
        this.getChildren().add(vBox);

        final HBox labelBox = new HBox(DEFAULT_SPACING);
        labelBox.setAlignment(Pos.BOTTOM_LEFT);

        final Label resetToDefaultLabel = new Label("↻");
        resetToDefaultLabel.setStyle(DEFAULT_NAME_STYLE);
        resetToDefaultLabel.setOnMouseClicked(e -> resetToDefault());

        final Label displayLabel = new Label(setting.getSettingName());
        labelBox.setStyle(DEFAULT_NAME_STYLE);
        labelBox.getChildren().addAll(resetToDefaultLabel, displayLabel);

        if (!setting.getDescription().isEmpty()) {
            final Label descriptionLabel = new Label(setting.getDescription());
            descriptionLabel.setStyle(DEFAULT_DESC_STYLE);
            labelBox.getChildren().add(descriptionLabel);
        }
        vBox.getChildren().addAll(labelBox, createUsable());
    }

    @Override
    public Node createUsable() {
        inputTextField = new TextField();
        inputTextField.setText(getSetting().getValue().toString());
        inputTextField.setAlignment(Pos.CENTER_LEFT);
        inputTextField.setMaxHeight(Double.MAX_VALUE);
        inputTextField.setStyle(DEFAULT_NAME_STYLE);
        inputTextField.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                getSetting().onValueChanged(inputTextField.getText());
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
