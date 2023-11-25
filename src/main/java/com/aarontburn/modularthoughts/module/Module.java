package com.aarontburn.modularthoughts.module;

import com.aarontburn.modularthoughts.GUIHandler;
import com.aarontburn.modularthoughts.module.change_reporter.ModuleChangeReporter;
import com.aarontburn.modularthoughts.module.change_reporter.ModuleListener;
import com.aarontburn.modularthoughts.module.settings.ModuleSettings;

public abstract class Module {

    private final ModuleChangeReporter moduleChangeReporter = new ModuleChangeReporter();

    private final ModuleSettings moduleSettings = new ModuleSettings();

    private final ModuleGUI moduleGUI;

    private final String moduleName;

    private boolean isInitialized;


    public Module(final String moduleName, final ModuleGUI correspondingGUI) {
        this.moduleGUI = correspondingGUI;
        this.moduleName = moduleName;
        addListener(correspondingGUI);
        correspondingGUI.setReferenceModule(this);
        GUIHandler.addGui(correspondingGUI);
    }


    public abstract void registerSettings();

    public final void addListener(final ModuleListener theListener) {
        moduleChangeReporter.addListener(theListener);
    }

    public final void notifyListeners(final String theEventName, final Object theData) {
        moduleChangeReporter.notifyListeners(theEventName, theData);
    }

    /**
     * Checks if both the module and GUI are properly initialized.
     *
     * @return True if both module and GUI are initalized, false otherwise.
     */
    public boolean isInitialized() {
        return this.isInitialized;
    }

    /**
     * Overridable method for modules that do something on initialization.
     * <p>
     * Child classes should still do a call to super() to ensure the GUI is initialized as well.
     */
    public void initialize() {
        // Override this, and do a super.initialize() after initializing model.

        moduleGUI.initialize();
        isInitialized = true;
    }


    /**
     * Overridable method that is called when the GUI is displayed.
     */
    public void onGuiShown() {
        // Do nothing by default
    }

    /**
     * Overrideable method to stop a module and GUI. This is useful to shut down threads inside other modules.
     * Child classes should still do a call to super() to ensure the GUI is properly shut down.
     */
    public void stop() {
        moduleGUI.stop();
        // Do nothing by default
    }

    public ModuleGUI getGUI() {
        return moduleGUI;
    }

    public String getModuleName() {
        return this.moduleName;
    }


}
