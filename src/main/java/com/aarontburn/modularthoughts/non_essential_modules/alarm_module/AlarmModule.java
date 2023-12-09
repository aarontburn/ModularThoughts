package com.aarontburn.modularthoughts.non_essential_modules.alarm_module;

import com.aarontburn.modularthoughts.built_ins.settings.types.BooleanSetting;
import com.aarontburn.modularthoughts.module_builder.Module;
import com.aarontburn.modularthoughts.module_builder.ModuleGUI;
import com.aarontburn.modularthoughts.module_builder.settings.Setting;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class AlarmModule extends Module {

    private static final String MODULE_NAME = "Alarm";

    private final List<Alarm> alarmList = new ArrayList<>();

    public AlarmModule() {
        super(MODULE_NAME);
    }

    @Nonnull
    @Override
    protected ModuleGUI setModuleGui() {
        return new AlarmGUI(this);
    }

    @Nullable
    @Override
    protected Setting<?>[] registerSettings() {
        return new Setting[]{
                new BooleanSetting(this)
                        .setName("Display Military Time")
                        .setDefault(true)
        };
    }

    @Override
    public void initialize() {
        super.initialize();
    }

    @Override
    public void refreshSettings() {

    }

    void createNewAlarm(final Alarm theNewAlarm) {
        alarmList.add(theNewAlarm);

        notifyListeners(ChangeEvents.UPDATE_ALARMS.name(), theNewAlarm);
    }

    enum ChangeEvents {
        UPDATE_ALARMS
    }
}
