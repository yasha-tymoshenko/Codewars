package com.tymoshenko.codewars;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void exampleTests() {
        assertEquals("1 second", TimeFormatter.formatDuration(1));
        assertEquals("1 minute and 2 seconds", TimeFormatter.formatDuration(62));
        assertEquals("2 minutes", TimeFormatter.formatDuration(120));
        assertEquals("1 hour", TimeFormatter.formatDuration(3600));
        assertEquals("1 hour, 1 minute and 2 seconds", TimeFormatter.formatDuration(3662));
    }

}