package com.aarontburn.modularthoughts.non_essential_modules.alarm_module;

import com.aarontburn.modularthoughts.module_builder.Module;
import com.aarontburn.modularthoughts.module_builder.ModuleGUI;
import com.aarontburn.modularthoughts.module_builder.change_reporter.ModuleEvent;
import com.dukescript.layouts.flexbox.FlexboxLayout;
import com.dukescript.layouts.jfxflexbox.FlexBoxPane;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

import static com.aarontburn.modularthoughts.non_essential_modules.alarm_module.AlarmUIComponent.WEEKDAY_SELECTOR_STYLE;

public class AlarmGUI extends ModuleGUI {

    private static final String FXML_PATH = "fxml/alarm-view.fxml";

    private final AlarmModule module;
    private final List<AlarmUIComponent> alarmUiList = new ArrayList<>();
    private AnchorPane rootPane;
    private FlexBoxPane alarmContainer;
    private Label newAlarmButton;
    private ScrollPane scrollPane;

    private PopupHandler popup;



    public AlarmGUI(@Nonnull Module theModule) {
        super(theModule, FXML_PATH);
        this.module = (AlarmModule) theModule;
    }

    Node findNode(final String nodeId) {
        return lookup(nodeId);
    }

    @Override
    public void initialize() {
        locateNodes();
        popup = new PopupHandler(this);
        popup.setVisible(false);


        alarmContainer = new FlexBoxPane();
        alarmContainer.setFlexDirection(FlexboxLayout.FlexDirection.ROW);
        scrollPane.setContent(alarmContainer);

        attachEvents();
    }

    private void locateNodes() {
        this.rootPane = (AnchorPane) lookup("AMroot");
        this.newAlarmButton = (Label) lookup("AMnewAlarmButton");

        scrollPane = (ScrollPane) lookup("AMscrollpane");


    }

    private void attachEvents() {
        newAlarmButton.setOnMouseClicked(e -> popup.setVisible(true));


    }

    @Override
    public void eventFired(final ModuleEvent theEvent) {
        switch (AlarmModule.ChangeEvents.valueOf(theEvent.getEventName())) {
            case UPDATE_SETTING -> {
                final Alarm alarm = (Alarm) theEvent.getData();

                boolean found = false;
                for (final AlarmUIComponent ui : alarmUiList) {
                    if (ui.getAlarm().getUuid().equals(alarm.getUuid())) {
                        found = true;
                        ui.updateUi();
                        break;
                    }
                }

                if (!found) {
                    final AlarmUIComponent uiComponent = new AlarmUIComponent(alarm);
                    alarmContainer.getChildren().add(uiComponent);
                }

            }
        }
    }
}
