package com.aarontburn.modularthoughts.built_ins.modules.settings_module;

import com.aarontburn.modularthoughts.built_ins.settings.types.HexColorSetting;
import com.aarontburn.modularthoughts.handlers.GUIHandler;
import com.aarontburn.modularthoughts.handlers.ModuleController;
import com.aarontburn.modularthoughts.module_builder.Module;
import com.aarontburn.modularthoughts.module_builder.ModuleGUI;
import com.aarontburn.modularthoughts.module_builder.ModuleSettings;
import com.aarontburn.modularthoughts.module_builder.settings.Setting;

import javax.annotation.Nonnull;
import java.util.*;

public class SettingsModule extends Module {

    private static final String MODULE_NAME = "Settings";

    private final ModuleController controller;
    private final GUIHandler guiHandler;

    private final List<ModuleSettings> moduleSettingsList = new ArrayList<>();

    private final Map<String, List<Setting<?>>> settingsMap = new LinkedHashMap<>();


    public SettingsModule(final ModuleController moduleController, final GUIHandler guiHandler) {
        super(MODULE_NAME);
        this.controller = moduleController;
        this.guiHandler = guiHandler;
        getSettings().setSettingsName("General");
    }

    @Override
    protected Setting<?>[] registerSettings() {
        return new Setting[]{
                new HexColorSetting(this)
                        .setName("Accent Color")
                        .setDefault("#2290B5"),
        };
    }

    @Override
    public void initialize() {
        super.initialize();

        final ModuleSettings thisSettings = getSettings();
        settingsMap.put(thisSettings.getModuleSettingsName(), thisSettings.getSettingsList());

        for (final ModuleSettings moduleSettings : moduleSettingsList) {
            if (moduleSettings == thisSettings) {
                continue;
            }

            final String name = moduleSettings.getModuleSettingsName();
            final List<Setting<?>> list = moduleSettings.getSettingsList();
            settingsMap.put(name, list);
        }
        refreshSettings();
        notifyListeners(ChangeEvents.POPULATE_SETTINGS_LIST.name(), settingsMap);
    }

    @Nonnull
    @Override
    protected ModuleGUI setModuleGui() {
        return new SettingsGUI(this);
    }

    @Override
    public void refreshSettings() {
        guiHandler.setAccentColor(String.valueOf(
                getSettings()
                        .getSettingByName("Accent Color")
                        .getValue()));
    }

    public void addModuleSettings(final List<ModuleSettings> moduleSettingsList) {
        this.moduleSettingsList.addAll(moduleSettingsList);
    }

    enum ChangeEvents {
        POPULATE_SETTINGS_LIST
    }

    @Nonnull
    @Override
    public String getSettingsFileName() {
        return "general_settings.json";
    }


}
