package com.aarontburn.modularthoughts.module;

import com.aarontburn.modularthoughts.module.settings.Setting;

import java.util.ArrayList;
import java.util.List;

public class ModuleSettings {


    private final List<Setting<?>> settingsList = new ArrayList<>();


    public ModuleSettings() {

    }

    public void addSetting(final Setting<?> theSetting) {
        settingsList.add(theSetting);
    }









}
