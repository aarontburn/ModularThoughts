package com.aarontburn.modularthoughts.module_builder;

import com.aarontburn.modularthoughts.handlers.GUIHandler;
import com.aarontburn.modularthoughts.handlers.ModuleController;
import com.aarontburn.modularthoughts.module_builder.change_reporter.ModuleListener;
import javafx.scene.Node;

import javax.annotation.Nonnull;

public abstract class ModuleGUI implements ModuleListener {

    private final String fxmlPath;

    private final Module module;

    public ModuleGUI(@Nonnull final Module theModule,
                     @Nonnull final String theFXMLPath) {

        this.module = theModule;
        this.fxmlPath = theFXMLPath;
    }


    public final Module getModule() {
        return this.module;
    }

    public final String getFxmlPath() {
        return fxmlPath;
    }

    public final boolean isInitialized() {
        return module.isInitialized();
    }

    /**
     * This is called to make this module visible.
     */
    public final void onGuiShown() {
        this.module.onGuiShown();
    }

    protected Node lookup(final String nodeID) {
        return ModuleController.getGuiHandler().lookup(nodeID);
    }

    public abstract void initialize();



    void stop() {
        // do nothing
    }

}
