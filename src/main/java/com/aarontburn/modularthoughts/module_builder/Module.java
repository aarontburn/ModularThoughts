package com.aarontburn.modularthoughts.module_builder;

import com.aarontburn.modularthoughts.module_builder.change_reporter.ModuleChangeReporter;
import com.aarontburn.modularthoughts.module_builder.change_reporter.ModuleListener;

import java.util.Locale;

public abstract class Module {

    private final ModuleChangeReporter moduleChangeReporter = new ModuleChangeReporter();

    private final ModuleSettings moduleSettings;

    private final ModuleGUI moduleGUI;

    private final String moduleName;

    private boolean isInitialized;


    public Module(final String moduleName, final ModuleGUI correspondingGUI) {
        this.moduleGUI = correspondingGUI;
        this.moduleName = moduleName;

        moduleSettings = new ModuleSettings(this);
        registerSettings();


        addListener(correspondingGUI);
        correspondingGUI.setReferenceModule(this);
    }


    public abstract void registerSettings();

    public ModuleSettings getSettings() {
        return this.moduleSettings;
    }
    public final void addListener(final ModuleListener theListener) {
        moduleChangeReporter.addListener(theListener);
    }
    public final void notifyListeners(final String theEventName, final Object theData) {
        moduleChangeReporter.notifyListeners(theEventName, theData);
    }

    /**
     * Checks if both the module and GUI are properly initialized.
     *
     * @return True if both module and GUI are initialized, false otherwise.
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

    public abstract void refreshSettings();

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

    public String getSettingsFileName() {
        return this.moduleName.toLowerCase(Locale.ROOT) + "_settings.json";
    }


}
