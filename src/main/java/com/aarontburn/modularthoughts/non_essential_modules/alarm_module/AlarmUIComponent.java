package com.aarontburn.modularthoughts.non_essential_modules.alarm_module;

import com.aarontburn.modularthoughts.tools.CSSBuilder;
import com.dukescript.layouts.jfxflexbox.FlexBoxPane;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javax.annotation.Nonnull;
import java.time.format.DateTimeFormatter;


public class AlarmUIComponent extends VBox {
    private static final DateTimeFormatter STANDARD_TIME_FORMATTER = DateTimeFormatter.ofPattern("h:mm a");
    private static final DateTimeFormatter MILITARY_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    static final int MIN_WIDTH = 454;
    private static final int VERTICAL_SPACING = 48;
    private static final int NAME_FONT_SIZE = 40;
    private static final int STANDARD_TIME_FONT_SIZE = 50;
    private static final int MILITARY_TIME_FONT_SIZE = 25;

    protected static final String WEEKDAY_SELECTOR_STYLE
            = new CSSBuilder()
            .setFontSize(24)
            .setBorderColor("white")
            .setBorderRadius(100)
            .setPadding(7)
            .build();


    private static final ColumnConstraints WEEKDAY_SELECTOR_CONSTRAINTS = new ColumnConstraints();
    static {
        WEEKDAY_SELECTOR_CONSTRAINTS.setPercentWidth(100.0 / Weekday.values().length);
        WEEKDAY_SELECTOR_CONSTRAINTS.setHalignment(HPos.CENTER);
    }

    private final Alarm alarm;

    private Label alarmNameLabel;
    private Label onLabel;
    private Label offLabel;

    public AlarmUIComponent(@Nonnull final Alarm theAlarm) {
        super(VERTICAL_SPACING);
        this.alarm = theAlarm;
        this.setFillWidth(true);
        this.setMaxWidth(Double.MAX_VALUE);
        FlexBoxPane.setGrow(this, 1.0f);
        FlexBoxPane.setMargin(this, new Insets(8));
        this.setStyle(new CSSBuilder()
                .setBorderColor("white")
                .setBorderRadius(5)
                .setPadding(16)
                .build());

        final VBox topBox = new VBox();

        final HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);


        onLabel = CSSBuilder.styleNewLabel("On", new CSSBuilder().setFontSize(24));
        final Label separator = CSSBuilder.styleNewLabel("/", new CSSBuilder().setFontSize(24).setTextFill("accent-color"));
        offLabel = CSSBuilder.styleNewLabel("Off", new CSSBuilder().setFontSize(24));
        hBox.getChildren().addAll(onLabel, separator, offLabel);

        alarmNameLabel = CSSBuilder.styleNewLabel(alarm.getAlarmName(), new CSSBuilder().setFontSize(NAME_FONT_SIZE));

        topBox.getChildren().addAll(hBox, alarmNameLabel);

        final VBox timeBox = new VBox();
        timeBox.setAlignment(Pos.CENTER);
        final Label standardTimeLabel = CSSBuilder.styleNewLabel(theAlarm.getTime().format(STANDARD_TIME_FORMATTER), new CSSBuilder().setFontSize(STANDARD_TIME_FONT_SIZE));

        final Label militaryTimeLabel = CSSBuilder.styleNewLabel(theAlarm.getTime().format(MILITARY_TIME_FORMATTER), new CSSBuilder().setFontSize(MILITARY_TIME_FONT_SIZE));
        timeBox.getChildren().addAll(standardTimeLabel, militaryTimeLabel);

        final GridPane weekdaySelectorGrid = new GridPane();
        weekdaySelectorGrid.setAlignment(Pos.CENTER);
        weekdaySelectorGrid.setMinWidth(420);

        final Weekday[] weekdays = Weekday.values();
        for (int i = 0; i < weekdays.length; i++) {
            weekdaySelectorGrid.getColumnConstraints().add(WEEKDAY_SELECTOR_CONSTRAINTS);

            final Label weekdayLabel = new Label(weekdays[i].getAbbreviation());
            weekdayLabel.setMinSize(50, 50);
            weekdayLabel.setStyle(WEEKDAY_SELECTOR_STYLE);
            weekdayLabel.setAlignment(Pos.CENTER);
            weekdaySelectorGrid.add(weekdayLabel, i, 0);
        }

        this.getChildren().addAll(topBox, timeBox, weekdaySelectorGrid);
    }

    public Alarm getAlarm() {
        return this.alarm;
    }

    void updateUi() {
        this.alarmNameLabel.setText(alarm.getAlarmName());

    }


}
