package com.aarontburn.modularthoughts.module_builder;

import com.aarontburn.modularthoughts.module_builder.settings.Setting;

import java.util.*;

public class ModuleSettings {


    private final Map<String, Setting<?>> settingsMap = new LinkedHashMap<>();

    private final Module module;

    private String settingsName;

    public ModuleSettings(final Module module) {
        this.module = module;
    }

    public String getModuleSettingsName() {
        return settingsName == null ? this.module.getModuleName() : settingsName;
    }

    public List<Setting<?>> getSettingsList() {
        return new ArrayList<>(this.settingsMap.values());
    }

    public void setSettingsName(final String settingsName) {
        this.settingsName = settingsName;
    }

    public void addSetting(final Setting<?> theSetting) {
        settingsMap.put(theSetting.getSettingName(), theSetting);
    }

    public Setting<?> findSettingByName(final String theSettingName) {
        return settingsMap.get(theSettingName);
    }

    @Override
    public String toString() {
        return getSettingsList().toString();
    }









}
