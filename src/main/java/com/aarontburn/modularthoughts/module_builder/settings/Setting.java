package com.aarontburn.modularthoughts.module_builder.settings;

import com.aarontburn.modularthoughts.handlers.StorageHandler;
import com.aarontburn.modularthoughts.module_builder.Module;

import java.util.Objects;
import java.util.function.Predicate;

public abstract class Setting<T> {

    private final Module parentModule;
    private String settingName;
    private String settingDescription;
    private String boundNodeID;
    private T currentValue;
    private T defaultValue;
    private boolean ready;
    private Predicate<Object> inputValidator;


    protected Setting(final Module theParentModule,
                      final String theSettingName,
                      final T theDefaultValue) {

        this.parentModule = theParentModule;
        this.settingName = theSettingName;
        this.currentValue = theDefaultValue;
        ready = theSettingName != null && theDefaultValue != null;

    }

    protected Setting(final Module theParentModule) {
        this(theParentModule, null, null);
    }

    public Setting<T> setName(final String theName) {
        if (settingName != null) {
            throw new IllegalArgumentException("Cannot reassign setting name for " + settingName);
        }
        this.settingName = theName;
        ready = settingName != null && defaultValue != null;
        return this;
    }

    public Setting<T> setDescription(final String theDescription) {
        if (settingDescription != null) {
            throw new IllegalArgumentException("Cannot reassign description for " + settingName);
        }
        this.settingDescription = theDescription;
        return this;
    }

    public Setting<T> setDefault(final T theDefaultValue) {
        if (defaultValue != null) {
            throw new IllegalArgumentException("Cannot reassign default value for " + settingName);
        }
        if (theDefaultValue == null) {
            throw new IllegalArgumentException("Setting value cannot be null.");
        }
        defaultValue = theDefaultValue;
        currentValue = theDefaultValue;
        ready = settingName != null;
        return this;
    }

    public Setting<T> setBoundNodeID(final String nodeID) {
        if (boundNodeID != null) {
            throw new IllegalArgumentException("Cannot reassign bound node ID for " + settingName);
        }
        this.boundNodeID = nodeID;
        return this;
    }

    public Setting<T> setValidator(final Predicate<Object> thePredicate) {
        this.inputValidator = thePredicate;
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

    public Predicate<Object> getInputValidator() {
        return this.inputValidator;
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

        currentValue = validateInput(newValue);
    }


    public void onValueChanged(final Object theNewValue) {
        setValue(theNewValue);
        parentModule.refreshSettings();
        StorageHandler.writeSettingsToModuleStorage(parentModule);
    }


    public T validateInput(final Object newValue) {
        return inputValidator.test(newValue) ? (T) newValue : currentValue;
    }


    public abstract SettingBox<T> getUIComponent();


    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        final Setting<?> setting = (Setting<?>) other;

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
}
