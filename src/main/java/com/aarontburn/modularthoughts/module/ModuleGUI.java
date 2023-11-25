package com.aarontburn.modularthoughts.module;

import com.aarontburn.modularthoughts.handlers.GUIHandler;
import com.aarontburn.modularthoughts.Logger;
import com.aarontburn.modularthoughts.handlers.ModuleController;
import com.aarontburn.modularthoughts.module.change_reporter.ModuleListener;
import javafx.scene.Node;

public abstract class ModuleGUI implements ModuleListener {

    private final String fxmlPath;

    private Module module;

    public ModuleGUI(final String theFXMLPath) {
        this.fxmlPath = theFXMLPath;
    }

    public void setReferenceModule(final Module theModule) {
        if (module != null) {
            Logger.log("WARNING: Attempted to reassign reference module for " + module.getModuleName());
            return;
        }
        this.module = theModule;
    }

    public Module getModule() {
        return this.module;
    }

    /**
     * This is called to make this module visible.
     */
    public void show() {
        GUIHandler.swapToGui(this);
        this.module.onGuiShown();
    }

    public String getFxmlPath() {
        return fxmlPath;
    }

    public Node lookup(final String nodeID) {
        return ModuleController.getGuiHandler().lookup(nodeID);
    }

    public abstract void initialize();

    public boolean isInitialized() {
        return module.isInitialized();
    }

    public void stop() {
        // do nothing
    }

}
