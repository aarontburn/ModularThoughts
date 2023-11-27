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

import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class GUIHandler {

    private static final String WINDOW_TITLE = "Modular Thoughts";

    private static final String VIEW_BASE_PATH = "fxml/view-base.fxml";

    private static final Dimension WINDOW_DIMENSION = new Dimension(1920, 1080);

    private static final Map<String, Pane> PANE_MAP = new HashMap<>();
    private final Scene scene;
    private final Stage stage;


    private Pane includePane;
    private HBox moduleTabBox;
    private Label testLabel;


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

    public void show() {
        stage.show();
    }

    public void setOnExit(final Runnable r) {
        scene.getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, windowEvent -> r.run());
    }

    private void locateNodes() {
        includePane = (Pane) lookup("includePane");
        moduleTabBox = (HBox) lookup("moduleTabBox");
        testLabel = (Label) lookup("testLabel");

        testLabel.setOnMouseClicked(e -> {
            Logger.log("Test label pressed");

        });

    }

    public void setAccentColor(final String hexString) {
        if (!Pattern.compile("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$").matcher(hexString).matches()) {
            Logger.log("WARNING: Attempting to pass invalid hex string: " + hexString + " to GUI.");
            return;
        }
        scene.getRoot().setStyle("accent-color: " + hexString + ";");

    }


    public Node lookup(final String theNodeID) {
        return scene.lookup(theNodeID.charAt(0) == '#' ? theNodeID : "#" + theNodeID);
    }

    public void addGui(final ModuleGUI gui) {
        try {
            final Pane pane = new FXMLLoader(Main.class.getResource(gui.getFxmlPath())).load();
            pane.setVisible(false);

            PANE_MAP.put(gui.getFxmlPath(), pane);
            includePane.getChildren().add(Helper.setAnchor(pane, 0, 0, 0, 0));


            final Label tabLabel = new Label(gui.getModule().getModuleName());
            tabLabel.setStyle("-fx-font-size: 20;");
            tabLabel.setOnMouseClicked(e -> gui.show());
            tabLabel.setMaxHeight(Double.MAX_VALUE);
            tabLabel.setMaxWidth(Double.MAX_VALUE);
            moduleTabBox.getChildren().add(tabLabel);


        } catch (final IOException e) {
            Logger.log(e);
        }
    }

    public static void swapToGui(final ModuleGUI gui) {
        for (final String path : PANE_MAP.keySet()) {
            PANE_MAP.get(path).setVisible(false);
        }

        if (!gui.isInitialized()) {
            gui.getModule().initialize();
        }

        final Node node = PANE_MAP.get(gui.getFxmlPath());

        node.setVisible(true);
        node.requestFocus();
    }


}
