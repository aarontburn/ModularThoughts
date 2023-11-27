package com.aarontburn.modularthoughts.built_ins.settings.types;

import com.aarontburn.modularthoughts.built_ins.settings.ui_components.HexColorSettingBox;
import com.aarontburn.modularthoughts.module_builder.Module;
import com.aarontburn.modularthoughts.module_builder.settings.Setting;
import com.aarontburn.modularthoughts.module_builder.settings.SettingBox;

public class HexColorSetting extends Setting<String> {

    public HexColorSetting(final Module theParentModule) {
        this(theParentModule, null, null);
        setValidator(o -> String.valueOf(o).matches("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$"));
    }

    protected HexColorSetting(final Module theParentModule,
                              final String theSettingName,
                              final String theDefaultValue) {

        super(theParentModule, theSettingName, theDefaultValue);
    }

    @Override
    public SettingBox<String> getUIComponent() {
        return new HexColorSettingBox(this);
    }
}
