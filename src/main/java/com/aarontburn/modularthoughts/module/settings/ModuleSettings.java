package com.aarontburn.modularthoughts.module.settings;

import com.aarontburn.modularthoughts.module.Module;

import java.util.ArrayList;
import java.util.List;

public class ModuleSettings {


    private final List<Setting<?>> settingsList = new ArrayList<>();

    private final Module module;

    private String settingsName;

    public ModuleSettings(final Module module) {
        this.module = module;
    }

    public String getModuleSettingsName() {
        return settingsName == null ? this.module.getModuleName() : settingsName;
    }

    public void setSettingsName(final String settingsName) {
        this.settingsName = settingsName;
    }



    public void addSetting(final Setting<?> theSetting) {
        settingsList.add(theSetting);
    }









}
