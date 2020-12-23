package com.tymoshenko.codewars.romannumber;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RomanNumeralsTest {
    @Test
    void testToRoman() {
        assertEquals("I", RomanNumerals.toRoman(1));
        assertEquals("II", RomanNumerals.toRoman(2));
    }

    @Test
    void testFromRoman() {
        assertEquals(1, RomanNumerals.fromRoman("I"));
        assertEquals(2, RomanNumerals.fromRoman("II"));
    }

    @Test
    void fromRomanThousands() {
        assertEquals(1990, RomanNumerals.fromRoman("MCMXC"));
        assertEquals(1666, RomanNumerals.fromRoman("MDCLXVI"));
        assertEquals(1001, RomanNumerals.fromRoman("MI"));
    }

    @Test
    void fromRomanTens() {
        assertEquals(23, RomanNumerals.fromRoman("XXIII"));
    }
}