package com.aarontburn.modularthoughts.module.settings;

public class StringSetting extends Setting<String> {

    private static final String DEFAULT_VALUE = "";

    public StringSetting(final String theSettingName) {
        super(theSettingName);

        setValue(DEFAULT_VALUE);

    }

}
