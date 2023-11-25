package com.aarontburn.modularthoughts;

import com.aarontburn.modularthoughts.module.ModuleGUI;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.awt.Dimension;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GUIHandler {

    private static final String WINDOW_TITLE = "Modular Thoughts";

    private static final String VIEW_BASE_PATH = "fxml/view-base.fxml";

    private static final Dimension WINDOW_DIMENSION = new Dimension(1920, 1080);

    private static final Map<String, Pane> PANE_MAP = new HashMap<>();


    private static Pane INCLUDE_PANE;
    private static HBox MODULE_TAB_BOX;


    private final Scene scene;

    private final Stage stage;


    public GUIHandler(final Stage stage) throws Exception {
        this.stage = stage;

        final FXMLLoader fxmlLoader
                = new FXMLLoader(Main.class.getResource(VIEW_BASE_PATH));

        scene = new Scene(fxmlLoader.load(),
                WINDOW_DIMENSION.getWidth(),
                WINDOW_DIMENSION.getHeight());

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
        INCLUDE_PANE = (Pane) lookup("includePane");
        MODULE_TAB_BOX = (HBox) lookup("moduleTabBox");

    }


    public Node lookup(final String theNodeID) {
        return scene.lookup(theNodeID.charAt(0) == '#' ? theNodeID : "#" + theNodeID);
    }

    public static void addGui(final ModuleGUI gui) {
        System.out.println("adding " + gui.getFxmlPath());
        try {
            final Pane pane = new FXMLLoader(Main.class.getResource(gui.getFxmlPath())).load();
            pane.setVisible(false);

            PANE_MAP.put(gui.getFxmlPath(), pane);
            INCLUDE_PANE.getChildren().add(Helper.setAnchor(pane, 0, 0, 0, 0));


            final Label tabLabel = new Label(gui.getModule().getModuleName());
            tabLabel.setStyle("-fx-font-size: 20;");
            tabLabel.setOnMouseClicked(e -> gui.show());
            tabLabel.setMaxHeight(Double.MAX_VALUE);
            tabLabel.setMaxWidth(Double.MAX_VALUE);
            MODULE_TAB_BOX.getChildren().add(tabLabel);


        } catch (final IOException e) {
            Logger.log(e);
        }
        System.out.println(PANE_MAP);
    }

    public static void swapToGui(final ModuleGUI gui) {
        System.out.println("swapping to " + gui.getFxmlPath());
        for (final String path : PANE_MAP.keySet()) {
            PANE_MAP.get(path).setVisible(false);
        }

        if (!gui.isInitialized()) {
            gui.getModule().initialize();
        }
        PANE_MAP.get(gui.getFxmlPath()).setVisible(true);
    }


}
