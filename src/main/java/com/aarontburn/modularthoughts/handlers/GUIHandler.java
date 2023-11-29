package com.aarontburn.modularthoughts.handlers;

import com.aarontburn.modularthoughts.Helper;
import com.aarontburn.modularthoughts.Logger;
import com.aarontburn.modularthoughts.Main;
import com.aarontburn.modularthoughts.module_builder.ModuleGUI;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.annotation.Nonnull;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class GUIHandler {

    private static final String WINDOW_TITLE = "Modular Thoughts";
    private static final String VIEW_BASE_PATH = "fxml/view-base.fxml";
    private static final Dimension WINDOW_DIMENSION = new Dimension(1920, 1080);
    private static final Map<String, Pane> PANE_MAP = new HashMap<>();
    private static boolean GUI_INITIALIZED;
    private final Scene scene;
    private final Stage stage;


    private Pane includePane;
    private HBox moduleTabBox;


    public GUIHandler(final Stage stage) throws Exception {
        this.stage = stage;

        final FXMLLoader fxmlLoader
                = new FXMLLoader(Main.class.getResource(VIEW_BASE_PATH));

        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        scene = new Scene(fxmlLoader.load(),
                Math.min(screenSize.getWidth() * 0.9, WINDOW_DIMENSION.getWidth()),
                Math.min(screenSize.getHeight() * 0.9, WINDOW_DIMENSION.getHeight()));

        stage.setTitle(WINDOW_TITLE);
        stage.setScene(scene);
        locateNodes();
    }

    private void locateNodes() {
        includePane = (Pane) lookup("includePane");
        moduleTabBox = (HBox) lookup("moduleTabBox");


        Label testLabel = (Label) lookup("testLabel");
        testLabel.setOnMouseClicked(e -> Logger.log("Test label pressed"));

    }

    final void show() {
        stage.show();
        GUI_INITIALIZED = true;
    }

    final void setOnExit(final Runnable theExitAction) {
        scene.getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, windowEvent -> theExitAction.run());
    }

    public static boolean isGuiInitialized() {
        return GUI_INITIALIZED;
    }

    public void setAccentColor(final String hexString) {
        if (!hexString.matches("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$")) {
            Logger.log("WARNING: Attempting to pass invalid hex string: " + hexString + " to GUI.");
            return;
        }
        scene.getRoot().setStyle("accent-color: " + hexString + ";");
    }


    public Node lookup(@Nonnull final String theNodeID) {
        return scene.lookup(theNodeID.charAt(0) == '#' ? theNodeID : "#" + theNodeID);
    }

    void addGui(@Nonnull final ModuleGUI gui) {
        if (GUI_INITIALIZED) {
            throw new UnsupportedOperationException("Cannot add a module after the GUI has been initialized.");
        }

        try {
            final Pane pane = new FXMLLoader(Main.class.getResource(gui.getFxmlPath())).load();
            pane.setVisible(false);

            PANE_MAP.put(gui.getFxmlPath(), pane);
            includePane.getChildren().add(Helper.setAnchor(pane, 0, 0, 0, 0));

            final Label tabLabel = new Label(gui.getModule().getModuleName());
            tabLabel.setStyle("-fx-font-size: 20;");
            tabLabel.setOnMouseClicked(e -> swapToGui(gui));
            tabLabel.setMaxHeight(Double.MAX_VALUE);
            tabLabel.setMaxWidth(Double.MAX_VALUE);
            moduleTabBox.getChildren().add(tabLabel);

        } catch (final IOException e) {
            Logger.log(e);
        }
    }

    public static void swapToGui(@Nonnull final ModuleGUI gui) {
        final Node node = PANE_MAP.get(gui.getFxmlPath());

        if (node == null) {
            throw new IllegalArgumentException("Could not find node for module: " + gui.getModule().getModuleName());
        }

        for (final String path : PANE_MAP.keySet()) {
            PANE_MAP.get(path).setVisible(false);
        }

        if (!gui.isInitialized()) {
            gui.getModule().initialize();
        }

        gui.onGuiShown();
        node.setVisible(true);
        node.requestFocus();
    }


}
