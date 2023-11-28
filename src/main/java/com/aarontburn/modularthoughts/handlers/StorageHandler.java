package com.aarontburn.modularthoughts.handlers;

import com.aarontburn.modularthoughts.Logger;
import com.aarontburn.modularthoughts.module_builder.Module;
import com.aarontburn.modularthoughts.module_builder.ModuleSettings;
import com.aarontburn.modularthoughts.module_builder.settings.Setting;
import com.google.gson.Gson;

import java.io.*;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class StorageHandler {

    private static final File STORAGE_PATH = new File(System.getProperty("user.dir") + "/storage/");

    private static final Gson GSON = new Gson();

    public static void writeToModuleStorage(final Module theModule,
                                            final String theFileName,
                                            final String theContents) {


        final String dirName = theModule.getModuleName().toLowerCase(Locale.ROOT);
        final File file = new File(STORAGE_PATH + "/" + dirName + "/", theFileName);

        new File(file.getParent()).mkdirs();


        try (final BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(theContents);
        } catch (IOException e) {
            Logger.log(e);
        }

    }

    public static void writeSettingsToModuleStorage(final Module theModule) {
        final Map<String, Object> settingsMap = new HashMap<>();
        final ModuleSettings settings = theModule.getSettings();

        for (final Setting<?> setting : settings.getSettingsList()) {
            settingsMap.put(setting.getSettingName(), setting.getValue());
        }

        writeToModuleStorage(theModule, theModule.getSettingsFileName(), GSON.toJson(settingsMap));
    }

    public static Map<String, Object> readSettingsFromModuleStorage(final Module theModule) {
        Map<String, Object> settingsMap = null;

        final String dirName = theModule.getModuleName().toLowerCase(Locale.ROOT);
        final File file = new File(STORAGE_PATH + "/" + dirName + "/", theModule.getSettingsFileName());

        try (final Reader reader = new FileReader(file)) {
            settingsMap = GSON.fromJson(reader, Map.class);

        } catch (IOException e) {
            Logger.log("WARNING: Couldn't find settings for " + theModule.getModuleName());
        }

        return settingsMap;
    }


}
