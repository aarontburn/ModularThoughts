package com.aarontburn.modularthoughts.built_ins.modules.settings_module;

import com.aarontburn.modularthoughts.built_ins.settings.types.HexColorSetting;
import com.aarontburn.modularthoughts.built_ins.settings.types.NumericSetting;
import com.aarontburn.modularthoughts.built_ins.settings.types.StringSetting;
import com.aarontburn.modularthoughts.built_ins.settings.ui_components.HexColorSettingBox;
import com.aarontburn.modularthoughts.handlers.GUIHandler;
import com.aarontburn.modularthoughts.handlers.ModuleController;
import com.aarontburn.modularthoughts.module_builder.Module;
import com.aarontburn.modularthoughts.module_builder.ModuleSettings;
import com.aarontburn.modularthoughts.module_builder.settings.Setting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SettingsModule extends Module {

    private static final String MODULE_NAME = "Settings";

    private final ModuleController controller;
    private final GUIHandler guiHandler;

    private final List<ModuleSettings> moduleSettingsList = new ArrayList<>();

    private final Map<String, List<Setting<?>>> settingsMap = new HashMap<>();


    public SettingsModule(final ModuleController moduleController, final GUIHandler guiHandler) {
        super(MODULE_NAME, new SettingsGUI());
        this.controller = moduleController;
        this.guiHandler = guiHandler;
        getSettings().setSettingsName("General");
    }

    @Override
    public void registerSettings() {
        final ModuleSettings settings = getSettings();

        // This is where general settings will be located
//        final StringSetting hexColorPicker = (StringSetting)
//                new StringSetting(this)
//                .setName("Accent Color")
//                .setDefault("#2290B5")
//                .setValidator(o -> String.valueOf(o).matches("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$"));
//
//        hexColorPicker.setUIComponent(new HexColorSettingBox(hexColorPicker));

        settings.addSetting(new HexColorSetting(this)
                        .setName("Accent Color")
                        .setDefault("#2290B5"));


    }

    @Override
    public void initialize() {
        super.initialize();

        for (final ModuleSettings moduleSettings : moduleSettingsList) {
            final String name = moduleSettings.getModuleSettingsName();
            final List<Setting<?>> list = moduleSettings.getSettingsList();
            settingsMap.put(name, list);
        }
        refreshSettings();
        notifyListeners(ChangeEvents.POPULATE_SETTINGS_LIST.name(), settingsMap);
    }

    @Override
    public void refreshSettings() {
        guiHandler.setAccentColor(String.valueOf(
                getSettings()
                        .getSettingByName("Accent Color")
                        .getValue()));
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
