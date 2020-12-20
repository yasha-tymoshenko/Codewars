package com.tymoshenko.codewars;

import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * <p>
 * 4 kyu
 * https://www.codewars.com/kata/52742f58faf5485cae000b9a/train/java
 *
 * Example:
 * TimeFormatter.formatDuration(62)   //returns "1 minute and 2 seconds"
 * TimeFormatter.formatDuration(3662) //returns "1 hour, 1 minute and 2 seconds"
 */
public class TimeFormatter {

    public static String formatDuration(int seconds) {
        if (seconds == 0) {
            return "now";
        }
        return Arrays.stream(TimeUnit.values())
                .map(timeUnit -> formatTime(seconds, timeUnit))
                .filter(durationFormatted -> !durationFormatted.isBlank())
                .collect(Collectors.joining(", "))
                .replaceAll("(.+), (.+?)$", "$1 and $2");
    }

    private static String formatTime(int time, TimeUnit timeUnit) {
        String formatted = "";
        int duration = time / timeUnit.getSeconds();
        if (timeUnit.getLimit() > 0) {
            duration %= timeUnit.getLimit();
        }
        if (duration > 0) {
            formatted = String.format("%d %s", duration, String.format("%s%s", timeUnit, duration > 1 ? "s" : ""));
        }
        return formatted;
    }

}

enum TimeUnit {

    YEAR(365 * 24 * 60 * 60, 0),
    DAY(24 * 60 * 60, 365),
    HOUR(60 * 60, 24),
    MINUTE(60, 60),
    SECOND(1, 60);

    private final int seconds;
    private final int limit;

    TimeUnit(int seconds, int limit) {
        this.seconds = seconds;
        this.limit = limit;
    }

    public int getSeconds() {
        return seconds;
    }

    public int getLimit() {
        return limit;
    }

    @Override
    public String toString() {
        return name().toLowerCase(Locale.ROOT);
    }

}

