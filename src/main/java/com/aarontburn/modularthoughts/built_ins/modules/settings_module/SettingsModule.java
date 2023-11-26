package com.aarontburn.modularthoughts.built_ins.modules.settings_module;

import com.aarontburn.modularthoughts.built_ins.settings.types.BooleanSetting;
import com.aarontburn.modularthoughts.built_ins.settings.types.StringSetting;
import com.aarontburn.modularthoughts.module_builder.Module;
import com.aarontburn.modularthoughts.module_builder.ModuleSettings;
import com.aarontburn.modularthoughts.module_builder.settings.Setting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SettingsModule extends Module {

    private static final String MODULE_NAME = "Settings";

    private final List<ModuleSettings> moduleSettingsList = new ArrayList<>();

    private final Map<String, List<Setting<?>>> settingsMap = new HashMap<>();


    public SettingsModule() {
        super(MODULE_NAME, new SettingsGUI());
        getSettings().setSettingsName("General");
    }

    @Override
    public void registerSettings() {
        final ModuleSettings settings = getSettings();

        // This is where general settings will be located
        settings.addSetting(new StringSetting(this)
                .setName("Accent Color")
                .setDefault("#00FFFF"));

        settings.addSetting(new StringSetting(this)
                .setName("Test String Setting")
                .setDescription("This is a test setting for Strings.")
                .setDefault("hello"));
    }

    @Override
    public void initialize() {
        super.initialize();

        for (final ModuleSettings moduleSettings : moduleSettingsList) {
            final String name = moduleSettings.getModuleSettingsName();
            final List<Setting<?>> list = moduleSettings.getSettingsList();
            settingsMap.put(name, list);
        }
        notifyListeners(ChangeEvents.POPULATE_SETTINGS_LIST.name(), settingsMap);
    }

    @Override
    public void refreshSettings() {

    }


    public void addModuleSettings(final ModuleSettings moduleSettings) {
        this.moduleSettingsList.add(moduleSettings);
    }

    public void addModuleSettings(final List<ModuleSettings> moduleSettingsList) {
        this.moduleSettingsList.addAll(moduleSettingsList);
    }

    public enum ChangeEvents {
        POPULATE_SETTINGS_LIST
    }


}
