package com.aarontburn.modularthoughts.built_ins.settings.types;

import com.aarontburn.modularthoughts.Logger;
import com.aarontburn.modularthoughts.handlers.StorageHandler;
import com.aarontburn.modularthoughts.module_builder.Module;
import com.aarontburn.modularthoughts.module_builder.settings.Setting;
import com.aarontburn.modularthoughts.built_ins.settings.ui_components.StringSettingBox;
import javafx.scene.Node;

public class StringSetting extends Setting<String> {

    public StringSetting(final Module theParentModule, final String theSettingName, final String theDefaultValue) {
        super(theParentModule, theSettingName, theDefaultValue);
    }

    public StringSetting(final Module theParentModule) {
        super(theParentModule);
    }

    @Override
    public StringSetting setValue(final Object newValue) {
        final StringSetting s = (StringSetting) super.setValue(String.valueOf(newValue));
        if (!(newValue instanceof String)) {
            Logger.log("WARNING: Needed to cast setting for " + getSettingName() + " to a String.");
        }
        return s;
    }

    @Override
    public Node getUIComponent() {
        return new StringSettingBox(this);
    }
}
