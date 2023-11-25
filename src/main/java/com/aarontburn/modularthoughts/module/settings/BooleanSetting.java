package com.aarontburn.modularthoughts.module.settings;

public class BooleanSetting extends Setting<Boolean> {

    private static final Boolean DEFAULT_VALUE = false;

    public BooleanSetting(final String theSettingName) {
        super(theSettingName);

        setValue(DEFAULT_VALUE);

    }

}
