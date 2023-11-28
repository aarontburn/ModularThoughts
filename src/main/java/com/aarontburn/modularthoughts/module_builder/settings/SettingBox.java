package com.aarontburn.modularthoughts.module_builder.settings;

import com.aarontburn.modularthoughts.handlers.GUIHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public abstract class SettingBox<T> extends HBox {

    /**
     * The default spacing between all nodes inside this HBox.
     */
    protected static final double DEFAULT_SPACING = 24;

    /**
     * The default font size of the name of the setting.
     */
    protected static final int DEFAULT_NAME_FONT_SIZE = 24;

    /**
     * The default font size of the description of the setting.
     */
    protected static final int DEFAULT_DESC_FONT_SIZE = 16;

    /**
     * The default width of the left component.
     * Because the default spacing is 24,
     * the right component should start at 139 (115 + 24).
     */
    protected static final int LEFT_WIDTH = 115;

    /**
     * The default CSS formatting for the setting name.
     */
    protected static final String DEFAULT_NAME_STYLE = String.format("-fx-font-size: %s;", DEFAULT_NAME_FONT_SIZE);

    /**
     * The default CSS formatting for the description.
     */
    protected static final String DEFAULT_DESC_STYLE = String.format("-fx-font-size: %s;", DEFAULT_DESC_FONT_SIZE);

    /**
     * The corresponding setting to modify.
     */
    private final Setting<T> setting;
    private boolean isInitialized;

    public SettingBox(final Setting<T> theSetting) {
        super(DEFAULT_SPACING);
        this.setting = theSetting;
    }

    /**
     * Adds the left node and right node to the parent.
     * This is overridable to create a fully customized UI.
     */
    public Node createUI() {
        if (isInitialized) {
            return this;
        }
        isInitialized = true;
        this.getChildren().addAll(createLeft(), createRight());
        return this;
    }

    /**
     * Creates the left component. By default, this returns a spacer.
     * This is overridable to create a custom component on the left.
     *
     * @return The left Node.
     */
    protected Node createLeft() {
        final Pane spacer = new Pane();
        spacer.setPrefWidth(LEFT_WIDTH);
        return spacer;
    }

    /**
     * Creates the right component. By default, this returns a spacer.
     * This is overridable to create a custom component on the left.
     *
     * @return The right Node.
     */
    protected Node createRight() {
        final VBox labelBox = new VBox();
        labelBox.setAlignment(Pos.CENTER_LEFT);

        final HBox topBox = new HBox(DEFAULT_SPACING);
        labelBox.getChildren().add(topBox);

        final Label resetToDefaultLabel = new Label("↻");
        resetToDefaultLabel.setStyle(DEFAULT_NAME_STYLE);
        resetToDefaultLabel.setOnMouseClicked(e -> setting.resetToDefault());

        final Label nameLabel = new Label(setting.getSettingName());
        nameLabel.setStyle(DEFAULT_NAME_STYLE);

        topBox.getChildren().addAll(resetToDefaultLabel, nameLabel);

        if (setting.getDescription() != null) {
            final Label descriptionLabel = new Label(setting.getDescription());
            descriptionLabel.setStyle(DEFAULT_DESC_STYLE);
            labelBox.getChildren().add(descriptionLabel);
        }
        return labelBox;
    }

    /**
     * @return The corresponding setting.
     */
    protected Setting<T> getSetting() {
        return this.setting;
    }

    public void updateSetting(final T theValue) {
        setting.setValue(theValue);
    }

    protected abstract void updateDisplayValue();

    public void undo() {
        updateSetting(setting.getValue());
    }


}
