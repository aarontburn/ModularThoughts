package com.aarontburn.modularthoughts.home_module;

import com.aarontburn.modularthoughts.Helper;
import com.aarontburn.modularthoughts.Logger;
import com.aarontburn.modularthoughts.module.Module;
import com.aarontburn.modularthoughts.module.settings.ModuleSettings;
import com.aarontburn.modularthoughts.module.settings.NumericSetting;

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
    }

    @Override
    public void registerSettings() {
        final ModuleSettings settings = getSettings();
        settings.addSetting(new NumericSetting("Full Date Scale"));
        settings.addSetting(new NumericSetting("Abbreviated Date Scale"));
        settings.addSetting(new NumericSetting("Standard Time Scale"));
        settings.addSetting(new NumericSetting("Military Time Scale"));


        // Window size
        // etc
    }

    @Override
    public void stop() {
        super.stop();
        clock.stop();
    }

    private class HomeClock {
        private static final DateTimeFormatter STANDARD_TIME_FORMATTER = DateTimeFormatter.ofPattern("h:mm:ss a");
        private static final DateTimeFormatter MILITARY_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
        private static final DateTimeFormatter SHORT_DATE_FORMATTER = DateTimeFormatter.ofPattern("M/d/yyyy");
        private static final DateTimeFormatter FULL_DATE_FORMATTER = DateTimeFormatter.ofPattern("EEEE, MMMM d");

        private final ScheduledExecutorService timeScheduler;
        private final ScheduledExecutorService dateScheduler;

        private LocalDateTime currentDateTime;

        private final String[] timePayload = new String[2];
        private final String[] datePayload = new String[2];


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
            Logger.log("updating date");
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

    public enum ChangeEvents {
        DATE_CHANGED, TIME_CHANGED
    }
}
