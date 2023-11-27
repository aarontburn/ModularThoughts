package com.aarontburn.modularthoughts.built_ins.settings.types;

import com.aarontburn.modularthoughts.built_ins.settings.ui_components.HexColorSettingBox;
import com.aarontburn.modularthoughts.module_builder.Module;
import com.aarontburn.modularthoughts.module_builder.settings.Setting;
import com.aarontburn.modularthoughts.built_ins.settings.ui_components.StringSettingBox;
import com.aarontburn.modularthoughts.module_builder.settings.SettingBox;

public class StringSetting extends Setting<String> {

    public StringSetting(final Module theParentModule) {
        this(theParentModule, null, null);
    }



    public StringSetting(final Module theParentModule,
                         final String theSettingName,
                         final String theDefaultValue) {

        super(theParentModule, theSettingName, theDefaultValue);

    }

    @Override
    public SettingBox<String> getUIComponent() {
        return new StringSettingBox(this);
    }


}
