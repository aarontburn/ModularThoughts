package com.aarontburn.modularthoughts.built_ins.modules.home_module;

import com.aarontburn.modularthoughts.Helper;
import com.aarontburn.modularthoughts.built_ins.settings.types.NumericSetting;
import com.aarontburn.modularthoughts.built_ins.settings.types.StringSetting;
import com.aarontburn.modularthoughts.module_builder.Module;
import com.aarontburn.modularthoughts.module_builder.ModuleGUI;
import com.aarontburn.modularthoughts.module_builder.settings.Setting;

import javax.annotation.Nonnull;
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
        super(MODULE_NAME);
        clock = new HomeClock();
    }

    @Override
    public void initialize() {
        super.initialize();
        clock.start();

        refreshSettings();
    }

    @Nonnull
    @Override
    protected ModuleGUI setModuleGui() {
        return new HomeGUI(this);
    }

    @Override
    public void refreshSettings() {
        notifyListeners(ChangeEvents.APPLY_SETTINGS.name(), null);
    }

    @Override
    protected Setting<?>[] registerSettings() {
        return new Setting[]{
                new NumericSetting(this)
                        .setName("Full Date Font Size (1)")
                        .setDescription("Adjusts the font size of the full date display (ex. Sunday, January 1st, 2023).")
                        .setDefault(40.0)
                        .setBoundNodeId("HMdateLabel"),

                new NumericSetting(this)
                        .setName("Abbreviated Date Font Size (2)")
                        .setDescription("Adjusts the font size of the abbreviated date display (ex. 1/01/2023).")
                        .setDefault(30.0)
                        .setBoundNodeId("HMabbreviatedDateLabel"),

                new NumericSetting(this)
                        .setName("Standard Time Font Size (3)")
                        .setDescription("Adjusts the font size of the standard time display (ex. 11:59:59 PM).")
                        .setDefault(90.0)
                        .setBoundNodeId("HMstandardTimeLabel"),

                new NumericSetting(this)
                        .setName("Military Time Font Size (4)")
                        .setDescription("Adjusts the font size of the military time display (ex. 23:59:49).")
                        .setDefault(30.0)
                        .setBoundNodeId("HMmilitaryTimeLabel"),

                new StringSetting(this)
                        .setName("Display Order")
                        .setDescription("Adjusts the order of the time/date displays.")
                        .setDefault("12 34")
                        .setValidator(o -> {
                    final String s = String.valueOf(o);
                    return (s.isEmpty() || s.matches("^(?!.*(\\d).*\\1)[1-4\\s]+$")) ? s : null;
                }),

        };
    }

    @Override
    public void stop() {
        super.stop();
        clock.stop();
    }

    enum ChangeEvents {
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
