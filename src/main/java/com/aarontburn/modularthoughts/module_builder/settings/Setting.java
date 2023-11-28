package com.aarontburn.modularthoughts.module_builder.settings;

import com.aarontburn.modularthoughts.handlers.GUIHandler;
import com.aarontburn.modularthoughts.handlers.StorageHandler;
import com.aarontburn.modularthoughts.module_builder.Module;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;


/**
 * An abstract Setting, which can be inherited to represent a multitude of settings.
 * <p>
 * The built-in settings are:
 * <ul>
 *     <li>
 *         {@link com.aarontburn.modularthoughts.built_ins.settings.types.BooleanSetting}
 * <p>
 *         Setting to represent binary state, such as true or false.
 *     </li>
 *
 *     <li>
 *         {@link com.aarontburn.modularthoughts.built_ins.settings.types.NumericSetting}
 * <p>
 *          Setting to represent numeric data, such as 10.0.
 *     </li>
 *
 *     <li>
 *         {@link com.aarontburn.modularthoughts.built_ins.settings.types.StringSetting}
 * <p>
 *          Setting to represent String data, such as a URL.
 *     </li>
 *
 *     <li>
 *         {@link com.aarontburn.modularthoughts.built_ins.settings.types.HexColorSetting}
 * <p>
 *          Setting to represent hex color codes, such as #FFFFFF.
 *     </li>
 * </ul>
 *
 * @param <T> The type of this setting.
 * @author Aaron Burnham
 */
public abstract class Setting<T> {

    /**
     * The module that this setting belongs to.
     */
    private final Module parentModule;

    /**
     * The name of the setting.
     */
    private String settingName;

    /**
     * The description of the setting.
     */
    private String settingDescription;

    /**
     * The ID of a node that is directly correlated with the
     * change of this setting.
     */
    private String boundNodeID;

    /**
     * The current value of this setting.
     */
    private T currentValue;

    /**
     * The default value of this setting.
     */
    private T defaultValue;

    /**
     * The input validator for this setting.
     */
    private InputValidator<T> inputValidator;

    /**
     * The corresponding GUI component for this setting.
     */
    private final SettingBox<T> settingBox;


    /**
     * Creates a new Setting with a specified parent module, setting name, and default value.
     * <p>
     * While the parent module CANNOT be null, the setting name and default value can. This is under
     * the assumption that they will be set later, as they are required fields.
     *
     * @param theParentModule The module that this setting belongs to.
     * @param theSettingName The name of the setting.
     * @param theDefaultValue The default value of the setting.
     */
    protected Setting(@Nonnull final Module theParentModule,
                      @Nullable final String theSettingName,
                      @Nullable final T theDefaultValue) {

        this.parentModule = theParentModule;

        if (theSettingName != null) {
            setName(theSettingName);
        }

        if (theDefaultValue != null) {
            setDefault(theDefaultValue);
        }
        this.settingBox = setUIComponent();

    }

    /**
     * Creates a new setting with the module that this setting belongs to.
     * <p>
     * Use the following methods to set the state of the setting:
     * <ul>
     *     <li>{@link #setName(String)} Sets the name of the setting (REQUIRED).</li>
     *     <li>{@link #setDefault(Object)} Sets the default value of the setting (REQUIRED).</li>
     *     <li>{@link #setDescription(String)} Sets the description of the setting.</li>
     *     <li>{@link #setBoundNodeId(String)} Sets bound node ID.</li>
     * </ul>
     *
     * @param theParentModule The module that this setting belongs to.
     */
    protected Setting(@Nonnull final Module theParentModule) {
        this(theParentModule, null, null);
    }

    /**
     * Sets the name of this setting. This is a required field.
     *
     * @param theName The name of the setting.
     * @return This setting.
     * @throws UnsupportedOperationException if the name of the setting is already set.
     */
    public @Nonnull Setting<T> setName(@Nonnull final String theName) {
        if (this.settingName != null) {
            throw new UnsupportedOperationException("Cannot reassign setting name for " + this.settingName);
        }
        this.settingName = theName;
        return this;
    }

    /**
     * Sets the default value of this setting. This is a required field.
     *
     * @param theDefaultValue The default value of the setting.
     * @return This setting.
     * @throws UnsupportedOperationException if the default value of the setting is already set.
     */
    public @Nonnull Setting<T> setDefault(@Nonnull final T theDefaultValue) {
        if (this.defaultValue != null) {
            throw new UnsupportedOperationException("Cannot reassign default value for " + this.settingName);
        }
        this.defaultValue = theDefaultValue;
        this.currentValue = theDefaultValue;
        return this;
    }

    /**
     * Sets the description of this setting. This is NOT a required field.
     *
     * @param theDescription The description of this setting.
     * @return This setting.
     * @throws UnsupportedOperationException if the description of the setting is already set.
     */
    public @Nonnull Setting<T> setDescription(@Nonnull final String theDescription) {
        if (this.settingDescription != null) {
            throw new UnsupportedOperationException("Cannot reassign description for " + this.settingName);
        }
        this.settingDescription = theDescription;
        return this;
    }

    /**
     * Sets the bound node ID of this setting. This is NOT a required field.
     *
     * @param theNodeId The node ID that relates to this setting.
     * @return This setting.
     * @throws UnsupportedOperationException if the node ID of this setting is already set.
     */
    public @Nonnull Setting<T> setBoundNodeId(@Nonnull final String theNodeId) {
        if (this.boundNodeID != null) {
            throw new UnsupportedOperationException("Cannot reassign bound node ID for " + this.settingName);
        }
        this.boundNodeID = theNodeId;
        return this;
    }

    /**
     * Returns the name of this setting.
     *
     * @return The name of this setting.
     */
    public @Nonnull String getSettingName() {
        return this.settingName;
    }

    /**
     * Returns the description of this setting.
     *
     * @return The description of this setting, or null if it hasn't been set.
     */
    public @Nullable String getDescription() {
        return this.settingDescription;
    }

    /**
     * Returns the node ID of the bound node.
     *
     * @return The node ID, or null if it hasn't been set.
     */
    public @Nullable String getBoundNodeID() {
        return this.boundNodeID;
    }

    /**
     * Returns the value of this setting.
     *
     * @return The value of this setting, or null if
     * @throws IllegalStateException if an attempt was made to access the value of this setting before all
     *                               appropriate fields were set.
     */
    public @Nonnull T getValue() {
        checkRequiredFields();
        return this.currentValue;
    }

    /**
     * Changes the value of this setting.
     * <p>
     * It passes the value into {@link #parseInput(Object)}, which returns either
     * a value of type that matches this settings type, or null indicating that it could
     * not properly parse the input.
     * <p>
     * If the input is null, the current value will remain the same. Otherwise, it will update
     * its value to the new one.
     * <p>
     * Once the GUI is initialized (via a call to {@link GUIHandler#isGuiInitialized()}), it will:
     * <ul>
     *     <li>Refresh the GUI counterpart with the updated value</li>
     *     <li>Call for a refresh in the parent module</li>
     *     <li>Re-write the settings for this module</li>
     * </ul>
     *
     * @param theValue The new value, not null.
     * @throws IllegalStateException if an attempt was made to set the value before all
     *                               appropriate fields were set.
     */
    public void setValue(@Nonnull final Object theValue) {
        checkRequiredFields();

        final T value = parseInput(theValue);
        this.currentValue = value != null ? value : this.currentValue;

        if (GUIHandler.isGuiInitialized()) {
            this.settingBox.updateDisplayValue();
            this.parentModule.refreshSettings();
            StorageHandler.writeSettingsToModuleStorage(this.parentModule);
        }
    }

    /**
     * Checks if the required fields are set before data can be accessed or set.
     * <p>
     * The required fields are {@link #settingName} and {@link #defaultValue}.
     *
     * @throws IllegalStateException if the required fields were NOT set.
     */
    private void checkRequiredFields() {
        if (this.settingName == null || this.defaultValue == null) {
            throw new IllegalStateException(
                    "Attempted to access '" + this.settingName + "' before all values were set. Missing:"
                            + (this.settingName == null ? "NAME" : "")
                            + (this.defaultValue == null ? "DEFAULT" : ""));
        }
    }

    /**
     * Resets the setting to default.
     */
    public void resetToDefault() {
        setValue(this.defaultValue);
    }

    /**
     * Converts an Object input into a {@link T} type input.
     * <p>
     * If an {@link #inputValidator} is specified, it will use it to parse the input.
     * <p>
     * Otherwise, it will use {@link #validateInput(Object)} to parse the input.
     *
     * @param theInput The input to parse.
     * @return A {@link T} type valid input, or null if the input couldn't be parsed.
     */
    private @Nullable T parseInput(@Nonnull final Object theInput) {
        if (this.inputValidator != null) {
            return this.inputValidator.parseInput(theInput);
        }
        return validateInput(theInput);
    }

    /**
     * Child-overridden method to parse inputs IF a {@link #inputValidator} is
     * not specified.
     * <p>
     * If the input is valid, it should return a {@link T} as the input.
     * <p>
     * Otherwise, it should send null. If null is not sent, it will attempt to assign potentially
     * invalid inputs to this setting.
     *
     * @param theInput The input to parse.
     * @return A {@link T} valid input, or null if the input could not be parsed.
     */
    public abstract @Nullable T validateInput(final Object theInput);

    /**
     * Sets the input validator for this setting.
     * <p>
     * {@link #parseInput(Object)} will use the specified input validator instead of
     * the {@link #validateInput(Object)} to parse input.
     *
     * @param theInputValidator The input validator to use over {@link #parseInput(Object)}.
     * @return This setting.
     */
    public @Nonnull Setting<T> setValidator(@Nonnull final InputValidator<T> theInputValidator) {
        this.inputValidator = theInputValidator;
        return this;
    }

    /**
     * Child-overridden method to set the corresponding UI component for this setting.
     *
     * @return The UI component for this setting.
     */
    protected abstract SettingBox<T> setUIComponent();

    /**
     * Returns the UI component for this setting.
     *
     * @return The UI component.
     */
    public @Nonnull SettingBox<T> getUIComponent() {
        return this.settingBox;
    }

    @Override
    public boolean equals(@Nullable final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        final Setting<T> setting = (Setting<T>) other;

        return Objects.equals(this.settingName, setting.settingName)
                && Objects.equals(this.settingDescription, setting.settingDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.settingName, this.settingDescription);
    }

    @Override
    public String toString() {
        return this.settingName + " : " + this.currentValue;
    }


    /**
     * A functional interface to, given an Object input, parse and return the input
     * as type {@link T}, or null if it couldn't be properly parsed.
     *
     * @param <T> The type of the valid input.
     */
    public interface InputValidator<T> {

        /**
         * Overridden method that should parse an input.
         *
         * @param theInput The object to parse.
         * @return A valid input as type {@link T}, or null if the input couldn't be parsed.
         */
        T parseInput(final Object theInput);
    }
}
