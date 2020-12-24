package com.tymoshenko.codewars.romannumber;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class RomanNumeralsTest {

    @ParameterizedTest
    @CsvSource(value = {"1=I", "2=II", "2345=MMCCCXLV"}, delimiter = '=')
    void testToRoman(int decimal, String expectedRoman) {
        assertEquals(expectedRoman, RomanNumerals.toRoman(decimal));
    }

    @ParameterizedTest
    @CsvSource(value = {"I=1", "II=2", "V=5", "IX=9"}, delimiter = '=')
    void testFromRoman(String roman, int expectedDecimal) {
        assertEquals(expectedDecimal, RomanNumerals.fromRoman(roman));
    }

    @ParameterizedTest
    @CsvSource({"MCMXC,1990", "MDCLXVI,1666", "MI,1001", "MCMLXXXVII,1987"})
    void testFromRomanThousands(String roman, int expectedDecimal) {
        assertEquals(expectedDecimal, RomanNumerals.fromRoman(roman));
    }

    @Test
    void testFromRomanTens() {
        assertEquals(23, RomanNumerals.fromRoman("XXIII"));
    }
}