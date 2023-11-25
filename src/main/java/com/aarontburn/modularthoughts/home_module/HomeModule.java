package com.aarontburn.modularthoughts.home_module;

import com.aarontburn.modularthoughts.module.Module;

public class HomeModule extends Module {




    public HomeModule() {
        super(new HomeGUI());



        initializeGUI();
    }

    @Override
    public void registerSettings() {
        // Window size
        // etc

    }
}
