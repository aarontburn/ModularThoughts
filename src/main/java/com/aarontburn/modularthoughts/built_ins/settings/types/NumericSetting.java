package com.aarontburn.modularthoughts.built_ins.settings.types;


import com.aarontburn.modularthoughts.Logger;
import com.aarontburn.modularthoughts.handlers.StorageHandler;
import com.aarontburn.modularthoughts.module_builder.Module;
import com.aarontburn.modularthoughts.module_builder.settings.Setting;
import com.aarontburn.modularthoughts.built_ins.settings.ui_components.NumericSettingBox;
import javafx.scene.Node;

public class NumericSetting extends Setting<Number> {

    public NumericSetting(final Module theParentModule, final String theSettingName, final Number theDefaultValue) {
        super(theParentModule, theSettingName, theDefaultValue);
    }

    public NumericSetting(final Module theParentModule) {
        super(theParentModule);
    }

    @Override
    public NumericSetting setValue(final Object newValue) {
        if (newValue instanceof Double) {
            return (NumericSetting) super.setValue(newValue);
        } else if (newValue instanceof String) {
            try {
                return (NumericSetting) super.setValue(Double.parseDouble((String) newValue));
            } catch (final NumberFormatException | ClassCastException e ) {
                Logger.log("WARNING: Could not parse " + newValue + " to a number. Reverting to default...");
            }
        }
        // This part is only reached if it couldn't turn the value into a Number.
        return (NumericSetting) super.setValue(getDefault());

    }


    @Override
    public Node getUIComponent() {
        return new NumericSettingBox(this);
    }
}
