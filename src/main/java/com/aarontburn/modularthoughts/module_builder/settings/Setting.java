package com.aarontburn.modularthoughts.module_builder.settings;

import com.aarontburn.modularthoughts.Logger;
import com.aarontburn.modularthoughts.handlers.StorageHandler;
import com.aarontburn.modularthoughts.module_builder.Module;
import javafx.scene.Node;

import java.util.Objects;

public abstract class Setting<T> {

    private final Module parentModule;

    private String settingName;

    private String settingDescription = "";

    private String boundNodeID;
    private T currentValue;
    private T defaultValue;


    protected Setting(final Module theParentModule, final String theSettingName, final T theDefaultValue) {
        this.parentModule = theParentModule;
        this.settingName = theSettingName;
        this.currentValue = theDefaultValue;
    }

    protected Setting(final Module theParentModule) {
        this.parentModule = theParentModule;
    }


    public String getSettingName() {
        return this.settingName;
    }

    public String getDescription() {
        return this.settingDescription;
    }

    public T getValue() {
        if (currentValue == null) {
            throw new RuntimeException("Retrieved value was null.");
        }

        return currentValue;
    }

    public String getBoundNodeID() {
        return this.boundNodeID;
    }


    public Setting<T> setName(final String theName) {
        this.settingName = theName;
        return this;
    }

    public Setting<T> setDescription(final String theDescription) {
        this.settingDescription = theDescription;
        return this;
    }

    public Setting<T> setValue(final Object newValue) {
        if (newValue == null) {
            throw new IllegalArgumentException("Setting value cannot be null.");
        }
        currentValue = (T) newValue;
        return this;
    }

    public Setting<T> setDefault(final T theDefaultValue) {
        if (theDefaultValue == null) {
            throw new IllegalArgumentException("Setting value cannot be null.");
        }
        defaultValue = theDefaultValue;
        currentValue = theDefaultValue;
        return this;
    }

    public Setting<T> setBoundNodeID(final String nodeID) {
        this.boundNodeID = nodeID;
        return this;
    }

    public void onValueChanged(final Object theNewValue) {
        setValue(theNewValue);
        parentModule.refreshSettings();
        StorageHandler.writeSettingsToModuleStorage(parentModule);
    }

    public T getDefault() {
        return defaultValue;
    }

    protected Module getParentModule() {
        return parentModule;
    }

    public abstract Node getUIComponent();

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
