package com.aarontburn.modularthoughts.handlers;


import com.aarontburn.modularthoughts.Logger;
import com.aarontburn.modularthoughts.built_ins.modules.home_module.HomeModule;
import com.aarontburn.modularthoughts.module_builder.Module;
import com.aarontburn.modularthoughts.built_ins.modules.settings_module.SettingsModule;
import com.aarontburn.modularthoughts.module_builder.ModuleSettings;
import com.aarontburn.modularthoughts.module_builder.settings.Setting;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ModuleController extends Application {


    private static GUIHandler GUI_HANDLER;
    private final List<Module> moduleList = new ArrayList<>();

    private SettingsModule settingsModule;

    public static GUIHandler getGuiHandler() {
        return GUI_HANDLER;
    }

    @Override
    public void start(final Stage stage) throws Exception {
        System.setProperty("prism.lcdtext", "true");
        // Boot up the settings module
        settingsModule = new SettingsModule();

        registerModules();
        checkSettings();

        // start the gui, but don't display the window yet
        GUI_HANDLER = new GUIHandler(stage);
        GUI_HANDLER.setOnExit(() -> {
            for (final Module module : moduleList) {
                module.stop();
            }
        });

        for (final Module module : moduleList) {
            GUI_HANDLER.addGui(module.getGUI());
        }

        GUI_HANDLER.show();
        settingsModule.initialize();

        for (final Module m : moduleList) {
            if (m.getClass() == HomeModule.class) {
                m.getGUI().show();
            }
        }

    }

    private void registerModules() {
        Logger.log("BOOT: Registering modules...");
        // Figure out what modules are active
        registerModule(new HomeModule());
        registerModule(settingsModule);
    }

    private void registerModule(final Module module) {
        moduleList.add(module);
    }

    private void checkSettings() {
        Logger.log("BOOT: Checking settings...");

        // Check settings for each module
        for (final Module module : moduleList) {
            final Map<String, Object> settingsMap = StorageHandler.readSettingsFromModuleStorage(module);

            if (settingsMap == null) {
                continue;
            }
            final ModuleSettings moduleSettings = module.getSettings();

            for (final String settingName : settingsMap.keySet()) {
                final Setting<?> setting = moduleSettings.findSettingByName(settingName);
                if (setting == null) {
                    Logger.log("WARNING: Invalid setting name: '" + settingName + "' found.");
                    continue;
                }
                setting.setValue(settingsMap.get(settingName));
            }
            StorageHandler.writeSettingsToModuleStorage(module);

        }


        // Register each module's setting in the settings module
        settingsModule.addModuleSettings(
                moduleList.stream()
                        .map(Module::getSettings)
                        .collect(Collectors.toList()));

    }


}