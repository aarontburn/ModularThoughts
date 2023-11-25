package com.aarontburn.modularthoughts.module;

import com.aarontburn.modularthoughts.module.change_reporter.ModuleChangeReporter;
import com.aarontburn.modularthoughts.module.change_reporter.ModuleListener;

public abstract class Module {

    private final ModuleChangeReporter moduleChangeReporter = new ModuleChangeReporter();

    private final ModuleSettings moduleSettings = new ModuleSettings();

    private final ModuleGUI moduleGUI;


    public Module(final ModuleGUI correspondingGUI) {
        this.moduleGUI = correspondingGUI;
        addListener(correspondingGUI);
    }


    public abstract void registerSettings();

    public final void addListener(final ModuleListener theListener) {
        moduleChangeReporter.addListener(theListener);
    }

    public final void notifyListeners(final String theEventName, final Object theData) {
        moduleChangeReporter.notifyListeners(theEventName, theData);
    }

    public void initializeGUI() {
        moduleGUI.initialize();
    }












}
