package com.genkey.foodmgt.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

public class SameDayOfWeek implements TemporalAdjuster {

    private final DayOfWeek dayOfWeek;

    public SameDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    @Override
    public Temporal adjustInto(Temporal temporal) {
        LocalDate date = LocalDate.from(temporal);
        int daysToAdd = dayOfWeek.getValue() - date.getDayOfWeek().getValue();
        if (daysToAdd < 0) {
            daysToAdd += 7;
        }
        return date.plusDays(daysToAdd);
    }
}
