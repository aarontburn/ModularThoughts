package com.aarontburn.modularthoughts.built_ins.settings.types;


import com.aarontburn.modularthoughts.Logger;
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

        setValidator(o -> {
            if (o instanceof Number) {
                return true;
            } else {
                try {
                    Double.parseDouble((String.valueOf(o)));
                    return true;
                } catch (final NumberFormatException | ClassCastException e) {
                    Logger.log("WARNING: Could not parse " + o + " to a number. Reverting to default...");
                }
            }
            return false;
        });
    }


    @Override
    public Number validateInput(final Object newValue) {
        if (newValue instanceof Number) {
            return (Number) newValue;
        }

        try {
            return (Double.parseDouble(String.valueOf(newValue)));
        } catch (final NumberFormatException | ClassCastException e) {
            Logger.log("WARNING: Could not parse " + newValue + " to a number. Reverting to last valid...");
        }

        return getValue();
    }

    @Override
    public SettingBox<Number> getUIComponent() {
        return new NumericSettingBox(this);
    }

}
