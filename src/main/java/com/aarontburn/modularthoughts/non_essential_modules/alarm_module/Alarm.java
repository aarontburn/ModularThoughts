package com.aarontburn.modularthoughts.non_essential_modules.alarm_module;

import javax.annotation.Nonnull;
import java.time.LocalTime;
import java.util.*;

public class Alarm {


    private final Set<Weekday> activeWeekdays = new HashSet<>();
    private String name;
    private LocalTime time = LocalTime.of(12, 0);
    private final UUID uuid = UUID.randomUUID();

    public Alarm(@Nonnull final String theAlarmName) {
        this.name = theAlarmName;
    }

    public Alarm setAlarmName(@Nonnull final String theName) {
        this.name = theName;
        return this;
    }

    public String getAlarmName() {
        return this.name;
    }

    public Alarm setTime(@Nonnull final LocalTime theTime) {
        this.time = theTime;
        return this;
    }

    public LocalTime getTime() {
        return this.time;
    }

    public Alarm addWeekday(@Nonnull final Weekday theWeekday) {
        this.activeWeekdays.add(theWeekday);
        return this;
    }

    public Alarm addWeekdays(final Weekday... theWeekdays) {
        this.activeWeekdays.addAll(Arrays.asList(theWeekdays));
        return this;
    }

    public Alarm removeWeekday(@Nonnull final Weekday theWeekday) {
        this.activeWeekdays.remove(theWeekday);
        return this;
    }

    public List<Weekday> getWeekdays() {
        return new ArrayList<>(activeWeekdays);
    }

    public UUID getUuid() {
        return this.uuid;
    }

    @Override
    public boolean equals(final Object theOther) {
        if (this == theOther) {
            return true;
        }
        if (theOther == null || getClass() != theOther.getClass()) {
            return false;
        }

        final Alarm alarm = (Alarm) theOther;
        return Objects.equals(name, alarm.name)
                && Objects.equals(time, alarm.time)
                && Objects.equals(activeWeekdays, alarm.activeWeekdays);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, time, activeWeekdays);
    }

    @Override
    public String toString() {
        return String.format("Alarm [name: %s | time: %s])", name, time);
    }
}
