package com.aarontburn.modularthoughts.non_essential_modules.alarm_module;

import com.aarontburn.modularthoughts.tools.CSSBuilder;
import com.aarontburn.modularthoughts.tools.CSSParser;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import javax.swing.text.html.CSS;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import static com.aarontburn.modularthoughts.non_essential_modules.alarm_module.AlarmUIComponent.WEEKDAY_SELECTOR_STYLE;

public class PopupHandler {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("hh:mma");


    private final AlarmGUI gui;
    private Set<Weekday> selectedWeekdays = new HashSet<>();

    private TextField nameTextField;
    private Text hourText, minuteText, periodText;
    private GridPane weekdaySelectorGrid;
    private Label saveButton, cancelButton;
    private VBox popup, popupWindow;
    private Text selectedTextInput;


    public PopupHandler(final AlarmGUI theGUI) {
        gui = theGUI;
        locateNodes();
        attachEvents();

        popup.setVisible(false);

        final CSSBuilder unselectedStyle =
                new CSSBuilder(WEEKDAY_SELECTOR_STYLE)
                        .setBorderColor("gray")
                        .setTextFill("gray");

        final CSSBuilder selectedStyle =
                new CSSBuilder(WEEKDAY_SELECTOR_STYLE)
                        .setBorderColor(CSSBuilder.ACCENT)
                        .setTextFill(CSSBuilder.ACCENT);

        final Weekday[] weekdays = Weekday.values();
        for (int i = 0; i < weekdaySelectorGrid.getColumnCount(); i++) {
            final Weekday weekday = weekdays[i];
            final Label weekdayLabel = new Label(weekdays[i].getAbbreviation());
            weekdayLabel.setMinSize(50, 50);
            weekdayLabel.setStyle(unselectedStyle.build());
            weekdayLabel.setAlignment(Pos.CENTER);
            weekdayLabel.setOnMousePressed(e -> {
                if (selectedWeekdays.contains(weekday)) {
                    // should be selected -> unselect
                    weekdayLabel.setStyle(unselectedStyle.build());
                    selectedWeekdays.remove(weekday);
                    return;
                }
                weekdayLabel.setStyle(selectedStyle.build());
                selectedWeekdays.add(weekday);

            });
            weekdaySelectorGrid.add(weekdayLabel, i, 0);
        }
    }


    private void locateNodes() {
        this.popup = (VBox) gui.findNode("AMpopup");
        this.nameTextField = (TextField) gui.findNode("AMpopupName");
        this.hourText = (Text) gui.findNode("AMhourText");
        this.minuteText = (Text) gui.findNode("AMminuteText");
        this.periodText = (Text) gui.findNode("AMperiodText");

        this.weekdaySelectorGrid = (GridPane) gui.findNode("AMweekdaySelectorGrid");
        this.saveButton = (Label) gui.findNode("AMsaveButton");
        this.cancelButton = (Label) gui.findNode("AMcancelButton");
        this.popupWindow = (VBox) gui.findNode("AMpopupWindow");
    }

    private void attachEvents() {
        saveButton.setOnMousePressed(e -> {
            final Alarm alarm = new Alarm(nameTextField.getText())
                    .addWeekdays(selectedWeekdays.toArray(new Weekday[0]))
                    .setTime(LocalTime.parse(hourText.getText() + ":" + minuteText.getText() + periodText.getText(), TIME_FORMATTER));


            gui.createNewAlarm(alarm);
            popup.setVisible(false);
        });


        popup.setOnMousePressed(e -> {
            Node parent = (Node) e.getTarget();

            while (parent.getParent() != null && !(parent instanceof VBox)) {
                parent = parent.getParent();
            }
            if (parent.getId().equals("AMpopup")) {
                popup.setVisible(false);
            }
        });

        popupWindow.setOnMousePressed(e -> popupWindow.requestFocus());

        hourText.setOnMousePressed(e -> setSelectedText(hourText));
        minuteText.setOnMousePressed(e -> setSelectedText(minuteText));
        periodText.setOnMousePressed(e -> setSelectedText(periodText));


        popup.setOnKeyPressed(k -> {
            if (selectedTextInput == null) {
                return;
            }

            final String in = k.getText();

            if (selectedTextInput == periodText) {
                if (in.equalsIgnoreCase("a")) {
                    selectedTextInput.setText("AM");
                } else if (in.equalsIgnoreCase("p")) {
                    selectedTextInput.setText("PM");
                }
                return;
            }


            if (in.isEmpty() || !in.matches("^[0-9]*$")) {
                return;
            }
            final int currentNum = Integer.parseInt(selectedTextInput.getText());
            final int num = Integer.parseInt(in);

            if (selectedTextInput == hourText) {
                if (currentNum != 1 && num == 0) {
                    selectedTextInput.setText("01");
                } else if (currentNum == 1 && num < 3) {
                    selectedTextInput.setText("1" + num);
                } else {
                    selectedTextInput.setText("0" + num);
                }
            } else if (selectedTextInput == minuteText) {
                final String min = selectedTextInput.getText().charAt(1) + String.valueOf(num);

                if (Integer.parseInt(min) < 60) {
                    selectedTextInput.setText(min);
                } else {
                    selectedTextInput.setText("0" + num);

                }
            }

        });


    }

    private void setSelectedText(final Text text) {
        if (selectedTextInput != null) {
            selectedTextInput.setStyle("");
        }
        selectedTextInput = text;
        selectedTextInput.setStyle(new CSSBuilder().setFill(CSSBuilder.ACCENT).build());
    }

    void setVisible(final boolean isVisible) {
        popup.setVisible(isVisible);
        if (!isVisible) {
            return;
        }

        popup.requestFocus();

    }

}
