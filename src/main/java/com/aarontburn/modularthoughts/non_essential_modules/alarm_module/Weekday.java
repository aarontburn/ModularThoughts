package com.aarontburn.modularthoughts.non_essential_modules.alarm_module;

import java.util.Locale;

public enum Weekday {

    SUNDAY,
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY;

    public String getAbbreviation() {
        return name().charAt(0) + String.valueOf(name().charAt(1)).toLowerCase(Locale.ROOT);
    }









}
