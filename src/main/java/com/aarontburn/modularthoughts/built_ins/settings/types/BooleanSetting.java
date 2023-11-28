package com.aarontburn.modularthoughts.built_ins.settings.types;

import com.aarontburn.modularthoughts.module_builder.Module;
import com.aarontburn.modularthoughts.module_builder.settings.Setting;
import com.aarontburn.modularthoughts.built_ins.settings.ui_components.BooleanSettingBox;
import com.aarontburn.modularthoughts.module_builder.settings.SettingBox;

public class BooleanSetting extends Setting<Boolean> {


    public BooleanSetting(final Module theParentModule) {
        this(theParentModule, null, null);
    }

    public BooleanSetting(final Module theParentModule,
                          final String theSettingName,
                          final Boolean theDefaultValue) {

        super(theParentModule, theSettingName, theDefaultValue);
    }

    @Override
    public Boolean validateInput(final Object theInput) {
        if (theInput instanceof Boolean) {
            return (Boolean) theInput;
        }
        final String s = String.valueOf(theInput);
        if (s.equalsIgnoreCase("true")) {
            return true;
        } else if (s.equalsIgnoreCase("false")) {
            return false;
        }
        return null;
    }


    @Override
    public SettingBox<Boolean> setUIComponent() {
        return new BooleanSettingBox(this);
    }
}
