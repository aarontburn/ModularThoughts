package com.aarontburn.modularthoughts.non_essential_modules.alarm_module;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import static com.aarontburn.modularthoughts.non_essential_modules.alarm_module.AlarmUIComponent.WEEKDAY_SELECTOR_STYLE;

public class PopupHandler {

    private final AlarmGUI gui;

    private TextField nameTextField, hourTextField, minuteTextField, periodTextField;
    private GridPane weekdaySelectorGrid;
    private Label saveButton, cancelButton;
    private VBox popup;


    public PopupHandler(final AlarmGUI theGUI) {
        gui = theGUI;
        locateNodes();
        attachEvents();

        popup.setVisible(false);

        final Weekday[] weekdays = Weekday.values();
        for (int i = 0; i < weekdaySelectorGrid.getColumnCount(); i++) {
            final Label weekdayLabel = new Label(weekdays[i].getAbbreviation());
            weekdayLabel.setMinSize(50, 50);
            weekdayLabel.setStyle(WEEKDAY_SELECTOR_STYLE);
            weekdayLabel.setAlignment(Pos.CENTER);
            weekdaySelectorGrid.add(weekdayLabel, i, 0);
        }
    }


    private void locateNodes() {
        this.popup = (VBox) gui.findNode("AMpopup");
        this.nameTextField = (TextField) gui.findNode("AMpopupName");
        this.hourTextField = (TextField) gui.findNode("AMhourTextField");
        minuteTextField = (TextField) gui.findNode("AMminuteTextField");
        periodTextField = (TextField) gui.findNode("AMperiodTextField");
        weekdaySelectorGrid = (GridPane) gui.findNode("AMweekdaySelectorGrid");
        saveButton = (Label) gui.findNode("AMsaveButton");
        cancelButton = (Label) gui.findNode("AMcancelButton");
    }

    private void attachEvents() {
        popup.setOnMousePressed(e -> {
            Node parent = (Node) e.getTarget();

            while (parent.getParent() != null && !(parent instanceof VBox)) {
                parent = parent.getParent();
            }
            if (parent.getId().equals("AMpopup")) {
                popup.setVisible(false);
            }
        });


        hourTextField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                hourTextField.setText("12");
            }


            if (oldValue.equals("01") && (newValue.equals("10") || newValue.equals("11") || newValue.equals("12"))) {
                hourTextField.setText(newValue);
            }


        });

    }

    void setVisible(final boolean isVisible) {
        popup.setVisible(isVisible);
        if (!isVisible) {
            return;
        }

        popup.requestFocus();

    }

}
