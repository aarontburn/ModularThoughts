package com.aarontburn.modularthoughts.module_builder;

import com.aarontburn.modularthoughts.module_builder.change_reporter.ModuleChangeReporter;
import com.aarontburn.modularthoughts.module_builder.change_reporter.ModuleListener;
import com.aarontburn.modularthoughts.module_builder.settings.Setting;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.util.Locale;
import java.util.Objects;

public abstract class Module {
    private final ModuleChangeReporter moduleChangeReporter = new ModuleChangeReporter();
    private final ModuleSettings moduleSettings = new ModuleSettings(this);
    private final ModuleGUI moduleGUI;
    private final String moduleName;
    private boolean isInitialized;

    public Module(@Nonnull final String moduleName) {
        this.moduleName = moduleName;
        this.moduleSettings.addAllSettings(registerSettings());

        this.moduleGUI = setModuleGui();
        addListener(this.moduleGUI);
    }

    public final @Nonnull ModuleGUI getGUI() {
        return moduleGUI;
    }

    public final @Nonnull String getModuleName() {
        return this.moduleName;
    }

    public final @Nonnull ModuleSettings getSettings() {
        return this.moduleSettings;
    }

    public final void addListener(@Nonnull final ModuleListener theListener) {
        moduleChangeReporter.addListener(theListener);
    }

    public final void notifyListeners(@Nonnull String theEventName, @Nullable final Object theData) {
        moduleChangeReporter.notifyListeners(theEventName, theData);
    }

    public @Nonnull String getSettingsFileName() {
        return this.moduleName.toLowerCase(Locale.ROOT) + "_settings.json";
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
    @OverridingMethodsMustInvokeSuper
    public void initialize() {
        moduleGUI.initialize();
        isInitialized = true;

        // Override this, and do a super.initialize() after initializing model.
    }
    protected abstract @Nonnull ModuleGUI setModuleGui();

    protected abstract @Nullable Setting<?>[] registerSettings();

    public @Nonnull String getResourceDirectory() {
        return Objects.requireNonNull(getClass().getResource("resources/")).getPath();
    }

    public abstract void refreshSettings();

    /**
     * Overridable method that is called when the GUI is displayed.
     */
    void onGuiShown() {
        // Do nothing by default
    }

    /**
     * Overrideable method to stop a module and GUI. This is useful to shut down threads inside other modules.
     * Child classes should still do a call to super() to ensure the GUI is properly shut down.
     */
    @OverridingMethodsMustInvokeSuper
    public void stop() {
        moduleGUI.stop();
        // Do nothing by default
    }




}
