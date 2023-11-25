package com.aarontburn.modularthoughts.module.settings;


public class NumericSetting extends Setting<Number> {

    private static final Number DEFAULT_VALUE = 0;

    public NumericSetting(final String theSettingName) {
        super(theSettingName);

        setValue(DEFAULT_VALUE);
    }


}
