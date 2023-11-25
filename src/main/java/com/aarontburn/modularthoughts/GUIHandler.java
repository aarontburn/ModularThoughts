package com.aarontburn.modularthoughts;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;

public class GUIHandler extends Application {

    private static final String WINDOW_TITLE = "Modular Thoughts";

    private static final String VIEW_BASE_PATH = "fxml/view-base.fxml";

    private static final Dimension WINDOW_DIMENSION = new Dimension(1920, 1080);

    private Scene scene;

    private Pane includePane;

    private Map<String, Pane> paneMap = new HashMap<>();

    @Override
    public void start(final Stage stage) throws Exception {
        final FXMLLoader fxmlLoader
                = new FXMLLoader(Main.class.getResource(VIEW_BASE_PATH));

        scene = new Scene(fxmlLoader.load(),
                WINDOW_DIMENSION.getWidth(),
                WINDOW_DIMENSION.getHeight());

        stage.setTitle(WINDOW_TITLE);
        stage.setScene(scene);
        stage.show();

        locateNodes();
    }


    private void locateNodes() {
        includePane = (Pane) lookup("includePane");

    }


    Node lookup(final String theNodeID) {
        return scene.lookup(theNodeID.charAt(0) == '#' ? theNodeID : "#" + theNodeID);
    }


}
