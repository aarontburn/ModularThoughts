package com.aarontburn.modularthoughts.module.settings;

public abstract class Setting<T> {

    private final String settingName;

    private String settingDescription = "";

    private T value;


    protected Setting(final String theSettingName) {
        this.settingName = theSettingName;
    }

    public String getSettingName() {
        return this.settingName;
    }

    public String getDescription() {
        return this.settingDescription;
    }

    public void setDescription(final String theDescription) {
        this.settingDescription = theDescription;
    }

    public Class<?> getSettingType() {
        return value.getClass();
    }

    public T getValue() {
        if (value == null) {
            throw new RuntimeException("Retrieved value was null.");
        }

        return value;
    }

    public void setValue(T newValue) {
        if (newValue == null) {
            throw new IllegalArgumentException("Setting value cannot be null.");
        }

        value = newValue;
    }


}
