package com.tymoshenko.codewars;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * <p>
 * Your task in order to complete this Kata is to write a function which formats a duration,
 * given as a number of seconds, in a human-friendly way.
 * <p>
 * The function must accept a non-negative integer. If it is zero, it just returns "now".
 * Otherwise, the duration is expressed as a combination of years, days, hours, minutes and seconds.
 * <p>
 * It is much easier to understand with an example:
 * <p>
 * TimeFormatter.formatDuration(62)   //returns "1 minute and 2 seconds"
 * TimeFormatter.formatDuration(3662) //returns "1 hour, 1 minute and 2 seconds"
 */
class TimeFormatterTest {

    @ParameterizedTest
    @CsvSource(value = {
            "1:1 second",
            "62:1 minute and 2 seconds",
            "120:2 minutes",
            "3600:1 hour",
            "3662:1 hour, 1 minute and 2 seconds",
            "-300:5 minutes ago"
    }, delimiter = ':')
    void timeFormatting(int seconds, String humanReadableTime) {
        assertEquals(humanReadableTime, TimeFormatter.formatDuration(seconds));
    }
}