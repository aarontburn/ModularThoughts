package com.aarontburn.modularthoughts.built_ins.settings.types;

import com.aarontburn.modularthoughts.built_ins.settings.ui_components.HexColorSettingBox;
import com.aarontburn.modularthoughts.module_builder.Module;
import com.aarontburn.modularthoughts.module_builder.settings.Setting;
import com.aarontburn.modularthoughts.module_builder.settings.SettingBox;

public class HexColorSetting extends Setting<String> {

    public HexColorSetting(final Module theParentModule) {
        this(theParentModule, null, null);
    }

    protected HexColorSetting(final Module theParentModule,
                              final String theSettingName,
                              final String theDefaultValue) {

        super(theParentModule, theSettingName, theDefaultValue);
    }

    @Override
    public String validateInput(final Object theInput) {
        final String s = (String) theInput;
        return s.matches("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$") ? s : null;
    }

    @Override
    public SettingBox<String> setUIComponent() {
        return new HexColorSettingBox(this);
    }
}
