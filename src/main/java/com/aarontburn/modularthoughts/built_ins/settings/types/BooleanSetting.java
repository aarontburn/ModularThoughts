package com.aarontburn.modularthoughts.built_ins.settings.types;

import com.aarontburn.modularthoughts.Logger;
import com.aarontburn.modularthoughts.handlers.StorageHandler;
import com.aarontburn.modularthoughts.module_builder.Module;
import com.aarontburn.modularthoughts.module_builder.settings.Setting;
import com.aarontburn.modularthoughts.built_ins.settings.ui_components.BooleanSettingBox;
import javafx.scene.Node;

public class BooleanSetting extends Setting<Boolean> {

    public BooleanSetting(final Module theParentModule,
                          final String theSettingName,
                          final Boolean theDefaultValue) {

        super(theParentModule, theSettingName, theDefaultValue);
    }

    public BooleanSetting(final Module theParentModule) {
        super(theParentModule);
    }


    @Override
    public BooleanSetting setValue(final Object newValue) {
        if (newValue instanceof Boolean) {
            return (BooleanSetting) super.setValue(newValue);
        }

        final String s = String.valueOf(newValue);
        if (s.equalsIgnoreCase("true")) {
            return (BooleanSetting) super.setValue(true);
        } else if (s.equalsIgnoreCase("false")) {
            return (BooleanSetting) super.setValue(false);
        }

        Logger.log("WARNING: Invalid boolean for " + getSettingName() + " found. Reverting to default...");
        return (BooleanSetting) super.setValue(getDefault());

    }

    @Override
    public Node getUIComponent() {
        return new BooleanSettingBox(this);
    }
}
