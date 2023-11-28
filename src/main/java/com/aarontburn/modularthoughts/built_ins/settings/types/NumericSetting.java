package com.aarontburn.modularthoughts.built_ins.settings.types;


import com.aarontburn.modularthoughts.Helper;
import com.aarontburn.modularthoughts.module_builder.Module;
import com.aarontburn.modularthoughts.module_builder.settings.Setting;
import com.aarontburn.modularthoughts.built_ins.settings.ui_components.NumericSettingBox;
import com.aarontburn.modularthoughts.module_builder.settings.SettingBox;

public class NumericSetting extends Setting<Number> {

    public NumericSetting(final Module theParentModule) {
        this(theParentModule, null, null);
    }

    public NumericSetting(final Module theParentModule,
                          final String theSettingName,
                          final Number theDefaultValue) {

        super(theParentModule, theSettingName, theDefaultValue);
    }


    @Override
    public Number validateInput(final Object theInput) {
        if (theInput instanceof Number) {
            return (Number) theInput;
        }
        try {
            return Helper.roundDouble(Double.parseDouble(String.valueOf(theInput)), 1);
        } catch (final NumberFormatException | ClassCastException ignored) {
        }

        return null;
    }

    @Override
    public SettingBox<Number> setUIComponent() {
        return new NumericSettingBox(this);
    }

}
