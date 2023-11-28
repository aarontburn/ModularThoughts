package com.aarontburn.modularthoughts.module_builder.settings;

import com.aarontburn.modularthoughts.handlers.GUIHandler;
import com.aarontburn.modularthoughts.handlers.StorageHandler;
import com.aarontburn.modularthoughts.module_builder.Module;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

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
     * Boolean representing if this setting has all required fields initialized,
     * which are {@link Setting#settingName} and {@link Setting#defaultValue}.
     */
    private boolean ready;

    /**
     * The input validator for this setting.
     */
    private InputValidator<T> inputValidator;

    /**
     * The corresponding GUI component for this setting.
     */
    private final SettingBox<T> settingBox;


    protected Setting(@Nonnull final Module theParentModule,
                      @Nullable final String theSettingName,
                      @Nullable final T theDefaultValue) {

        this.parentModule = theParentModule;
        this.settingName = theSettingName;
        this.currentValue = theDefaultValue;
        this.settingBox = setUIComponent();
        ready = theSettingName != null && theDefaultValue != null;

    }

    protected Setting(@Nonnull final Module theParentModule) {
        this(theParentModule, null, null);
    }

    /**
     * Sets the name of this setting. This is a required field.
     *
     * @param theName The name of the setting.
     * @throws UnsupportedOperationException if the name of the setting is already set.
     * @return This setting.
     */
    public Setting<T> setName(@Nonnull final String theName) {
        if (settingName != null) {
            throw new UnsupportedOperationException("Cannot reassign setting name for " + settingName);
        }
        this.settingName = theName;
        ready = defaultValue != null;
        return this;
    }

    /**
     * Sets the default value of this setting. This is a required field.
     *
     * @param theDefaultValue The default value of the setting.
     * @throws UnsupportedOperationException if the default value of the setting is already set.
     * @return This setting.
     */
    public Setting<T> setDefault(@Nonnull final T theDefaultValue) {
        if (defaultValue != null) {
            throw new UnsupportedOperationException("Cannot reassign default value for " + settingName);
        }
        defaultValue = theDefaultValue;
        currentValue = theDefaultValue;
        ready = settingName != null;
        return this;
    }

    /**
     * Sets the description of this setting. This is NOT a required field.
     *
     * @param theDescription The description of this setting.
     * @throws UnsupportedOperationException if the description of the setting is already set.
     * @return This setting.
     */
    public Setting<T> setDescription(@Nonnull final String theDescription) {
        if (settingDescription != null) {
            throw new IllegalArgumentException("Cannot reassign description for " + settingName);
        }
        this.settingDescription = theDescription;
        return this;
    }

    public Setting<T> setBoundNodeID(final String nodeID) {
        if (boundNodeID != null) {
            throw new IllegalArgumentException("Cannot reassign bound node ID for " + settingName);
        }
        this.boundNodeID = nodeID;
        return this;
    }

    public String getSettingName() {
        return this.settingName;
    }

    public String getDescription() {
        return this.settingDescription;
    }

    public String getBoundNodeID() {
        return this.boundNodeID;
    }

    public T getValue() {
        if (currentValue == null) {
            throw new RuntimeException("Retrieved value was null for " + settingName);
        }

        return currentValue;
    }

    public T getDefault() {
        return defaultValue;
    }

    public void setValue(final Object newValue) {
        if (!ready) {
            throw new IllegalStateException(
                    "Not all fields of setting '"
                            + settingName + "' have been set. Missing: "
                            + (settingName == null ? "NAME" : "")
                            + (defaultValue == null ? "DEFAULT" : ""));
        }

        if (newValue == null) {
            throw new IllegalArgumentException("Setting value cannot be null.");
        }

        final T value = parseInput(newValue);
        currentValue = value != null ? value : currentValue;

        if (GUIHandler.isGuiInitialized()) {
            settingBox.updateDisplayValue();
            parentModule.refreshSettings();
            StorageHandler.writeSettingsToModuleStorage(parentModule);
        }
    }

    public void resetToDefault() {
        setValue(defaultValue);
    }

    private T parseInput(final Object theInput) {
        if (inputValidator != null) {
            return inputValidator.parseInput(theInput);
        }
        return validateInput(theInput);
    }

    public abstract T validateInput(final Object theInput);

    public Setting<T> setValidator(final InputValidator<T> thePredicate) {
        this.inputValidator = thePredicate;
        return this;
    }

    protected abstract SettingBox<T> setUIComponent();

    public SettingBox<T> getUIComponent() {
        return this.settingBox;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        final Setting<T> setting = (Setting<T>) other;

        return Objects.equals(settingName, setting.settingName)
                && Objects.equals(settingDescription, setting.settingDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(settingName, settingDescription);
    }

    @Override
    public String toString() {
        return settingName + " : " + currentValue;
    }


    public interface InputValidator<T> {
        T parseInput(final Object theInput);
    }
}
