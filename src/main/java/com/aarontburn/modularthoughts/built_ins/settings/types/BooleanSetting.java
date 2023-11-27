package com.aarontburn.modularthoughts.built_ins.settings.types;

import com.aarontburn.modularthoughts.module_builder.Module;
import com.aarontburn.modularthoughts.module_builder.settings.Setting;
import com.aarontburn.modularthoughts.built_ins.settings.ui_components.BooleanSettingBox;
import com.aarontburn.modularthoughts.module_builder.settings.SettingBox;

public class BooleanSetting extends Setting<Boolean> {


    public BooleanSetting(final Module theParentModule) {
        this(theParentModule, null, null);

        setValidator(o -> {
            if (o instanceof Boolean) {
                return (Boolean) o;
            }
            final String s = String.valueOf(o);

            return s.equalsIgnoreCase("true") || s.equalsIgnoreCase("false");
        });
    }

    public BooleanSetting(final Module theParentModule,
                          final String theSettingName,
                          final Boolean theDefaultValue) {

        super(theParentModule, theSettingName, theDefaultValue);
    }


    @Override
    public SettingBox<Boolean> getUIComponent() {
        return new BooleanSettingBox(this);
    }
}
