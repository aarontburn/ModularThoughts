package com.aarontburn.modularthoughts.built_ins.modules.home_module;

import com.aarontburn.modularthoughts.Helper;
import com.aarontburn.modularthoughts.Logger;
import com.aarontburn.modularthoughts.built_ins.settings.types.BooleanSetting;
import com.aarontburn.modularthoughts.built_ins.settings.types.NumericSetting;
import com.aarontburn.modularthoughts.built_ins.settings.types.StringSetting;
import com.aarontburn.modularthoughts.module_builder.Module;
import com.aarontburn.modularthoughts.module_builder.ModuleSettings;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class HomeModule extends Module {
    private static final String MODULE_NAME = "Home";
    private final HomeClock clock;

    public HomeModule() {
        super(MODULE_NAME, new HomeGUI());
        clock = new HomeClock();
    }

    @Override
    public void initialize() {
        super.initialize();
        clock.start();

        refreshSettings();
    }

    @Override
    public void refreshSettings() {
        notifyListeners(ChangeEvents.APPLY_SETTINGS.name(), null);
    }

    @Override
    public void registerSettings() {
        final ModuleSettings settings = getSettings();

        settings.addSetting(new NumericSetting(this)
                .setName("Full Date Font Size")
                .setDescription("Adjusts the font size of the full date display (ex. Sunday, January 1st, 2023).")
                .setDefault(40.0)
                .setBoundNodeID("HMdateLabel"));


        settings.addSetting(new NumericSetting(this)
                .setName("Abbreviated Date Font Size")
                .setDescription("Adjusts the font size of the abbreviated date display (ex. 1/01/2023).")
                .setDefault(30.0)
                .setBoundNodeID("HMabbreviatedDateLabel"));


        settings.addSetting(new NumericSetting(this)
                .setName("Standard Time Font Size")
                .setDescription("Adjusts the font size of the standard time display (ex. 11:59:59 PM).")
                .setDefault(90.0)
                .setBoundNodeID("HMstandardTimeLabel"));


        settings.addSetting(new NumericSetting(this)
                .setName("Military Time Font Size")
                .setDescription("Adjusts the font size of the military time display (ex. 23:59:49).")
                .setDefault(30.0)
                .setBoundNodeID("HMmilitaryTimeLabel"));


        settings.addSetting(new BooleanSetting(this)
                .setName("Test Boolean")
                .setDescription("A Boolean Setting")
                .setDefault(false));

        settings.addSetting(new StringSetting(this)
                .setName("Some sort of URL")
                .setDescription("Use this URL to do something.")
                .setDefault("https://google.com/"));


        settings.addSetting(new StringSetting(this)
                .setName("a")
                .setDescription("In hex.")
                .setDefault("#FFFFFF"));


        settings.addSetting(new StringSetting(this)
                .setName("2")
                .setDescription("In hex.")
                .setDefault("#FFFFFF"));


        settings.addSetting(new StringSetting(this)
                .setName("3")
                .setDescription("In hex.")
                .setDefault("#FFFFFF"));


        settings.addSetting(new StringSetting(this)
                .setName("4")
                .setDescription("In hex.")
                .setDefault("#FFFFFF"));



    }

    @Override
    public void stop() {
        super.stop();
        clock.stop();
    }

    public enum ChangeEvents {
        DATE_CHANGED, TIME_CHANGED, APPLY_SETTINGS
    }

    private class HomeClock {
        private static final DateTimeFormatter STANDARD_TIME_FORMATTER = DateTimeFormatter.ofPattern("h:mm:ss a");
        private static final DateTimeFormatter MILITARY_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
        private static final DateTimeFormatter SHORT_DATE_FORMATTER = DateTimeFormatter.ofPattern("M/d/yyyy");
        private static final DateTimeFormatter FULL_DATE_FORMATTER = DateTimeFormatter.ofPattern("EEEE, MMMM d");

        private final ScheduledExecutorService timeScheduler;
        private final ScheduledExecutorService dateScheduler;
        private final String[] timePayload = new String[2];
        private final String[] datePayload = new String[2];
        private LocalDateTime currentDateTime;


        private HomeClock() {
            timeScheduler = Executors.newSingleThreadScheduledExecutor();
            dateScheduler = Executors.newSingleThreadScheduledExecutor();
        }

        private void start() {
            currentDateTime = LocalDateTime.now().minusSeconds(1);

            updateTime();
            updateDate();

            timeScheduler.scheduleAtFixedRate(
                    this::updateTime,
                    1000 - LocalTime.now().get(ChronoField.MILLI_OF_SECOND),
                    1000,
                    TimeUnit.MILLISECONDS);

            // TODO: Verify this works!
            dateScheduler.scheduleAtFixedRate(
                    this::updateDate,
                    Duration.between(LocalTime.now(), LocalTime.MIDNIGHT.minusNanos(1)).toMillis(),
                    TimeUnit.DAYS.toMillis(1),
                    TimeUnit.MILLISECONDS
            );
        }

        private void updateTime() {
            currentDateTime = LocalDateTime.now();
            timePayload[0] = currentDateTime.format(STANDARD_TIME_FORMATTER);
            timePayload[1] = currentDateTime.format(MILITARY_TIME_FORMATTER);
            notifyListeners(ChangeEvents.TIME_CHANGED.name(), timePayload);
        }

        private void updateDate() {
            this.currentDateTime = LocalDateTime.now();

            datePayload[0] = currentDateTime.format(FULL_DATE_FORMATTER)
                    + Helper.getNumberSuffix(currentDateTime.getDayOfMonth())
                    + ", " + currentDateTime.getYear();

            datePayload[1] = currentDateTime.format(SHORT_DATE_FORMATTER);
            notifyListeners(ChangeEvents.DATE_CHANGED.name(), datePayload);

        }

        private void stop() {
            timeScheduler.shutdownNow();
            dateScheduler.shutdownNow();
        }
    }


}
