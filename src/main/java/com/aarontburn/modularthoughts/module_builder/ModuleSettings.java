package com.aarontburn.modularthoughts.module_builder;

import com.aarontburn.modularthoughts.module_builder.settings.Setting;

import java.util.*;

public class ModuleSettings {

    private final Map<String, Setting<?>> settingsMap = new LinkedHashMap<>();
    private final Module module;
    private String settingsName;

    public ModuleSettings(final Module theModule) {
        this.module = theModule;
    }

    public final String getModuleSettingsName() {
        return settingsName == null ? this.module.getModuleName() : settingsName;
    }

    public final List<Setting<?>> getSettingsList() {
        return new ArrayList<>(this.settingsMap.values());
    }

    public final void setSettingsName(final String theModuleSettingName) {
        this.settingsName = theModuleSettingName;
    }

    public final void addSetting(final Setting<?> theSetting) {
        settingsMap.put(theSetting.getSettingName(), theSetting);
    }

    public final void addAllSettings(final Setting<?>... theSettings) {
        if (theSettings == null) {
            return;
        }
        for (final Setting<?> setting : theSettings) {
            addSetting(setting);
        }
    }

    public final Setting<?> getSettingByName(final String theSettingName) {
        return settingsMap.get(theSettingName);
    }

    @Override
    public String toString() {
        return getSettingsList().toString();
    }

}
