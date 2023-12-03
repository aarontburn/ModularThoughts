package com.aarontburn.modularthoughts.non_essential_modules.alarm_module;

import com.aarontburn.modularthoughts.CSSBuilder;
import com.aarontburn.modularthoughts.Helper;
import com.aarontburn.modularthoughts.module_builder.Module;
import com.aarontburn.modularthoughts.module_builder.ModuleGUI;
import com.aarontburn.modularthoughts.module_builder.change_reporter.ModuleEvent;
import com.dukescript.layouts.flexbox.FlexboxLayout;
import com.dukescript.layouts.jfxflexbox.FlexBoxPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class AlarmGUI extends ModuleGUI {

    private static final String FXML_PATH = "fxml/alarm-view.fxml";

    private AnchorPane rootPane;
    private FlexBoxPane alarmContainer;
    private Label newAlarmButton;
    private final AlarmModule module;
    private final List<AlarmUIComponent> alarmUiList = new ArrayList<>();

    public AlarmGUI(@Nonnull Module theModule) {
        super(theModule, FXML_PATH);
        this.module = (AlarmModule) theModule;
    }

    @Override
    public void initialize() {
        this.rootPane = (AnchorPane) lookup("AMroot");
        newAlarmButton = (Label) lookup("AMnewAlarmButton");

        final ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        this.rootPane.getChildren().add( Helper.setAnchor(scrollPane, 72, 24, 24, 24));

        alarmContainer = new FlexBoxPane();
        alarmContainer.setFlexDirection(FlexboxLayout.FlexDirection.ROW);
        scrollPane.setContent(alarmContainer);

        attachEvents();
    }

    private void attachEvents() {
        newAlarmButton.setOnMouseClicked(e -> {
            module.createNewSetting();
        });
    }

    @Override
    public void eventFired(final ModuleEvent theEvent) {
        switch (AlarmModule.ChangeEvents.valueOf(theEvent.getEventName())) {
            case UPDATE_SETTING -> {
                final Alarm alarm = (Alarm) theEvent.getData();

                boolean found = false;
                for (final AlarmUIComponent ui :alarmUiList) {
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
