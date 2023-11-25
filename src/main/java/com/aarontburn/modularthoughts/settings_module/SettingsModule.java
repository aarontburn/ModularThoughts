package com.aarontburn.modularthoughts.settings_module;

import com.aarontburn.modularthoughts.module.Module;
import com.aarontburn.modularthoughts.module.settings.ModuleSettings;
import com.aarontburn.modularthoughts.module.settings.NumericSetting;

import java.util.ArrayList;
import java.util.List;

public class SettingsModule extends Module {

    private static final String MODULE_NAME = "Settings";

    private List<ModuleSettings> moduleSettingsList = new ArrayList<>();


    public SettingsModule() {
        super(MODULE_NAME, new SettingsGUI());
        getSettings().setSettingsName("General");
    }

    @Override
    public void registerSettings() {
        final ModuleSettings settings = getSettings();
    }

    public void addSettingsModule(final ModuleSettings moduleSettings) {
        this.moduleSettingsList.add(moduleSettings);
    }

    public void addSettingsModule(final List<ModuleSettings> moduleSettingsList) {
        this.moduleSettingsList.addAll(moduleSettingsList);
    }
}
