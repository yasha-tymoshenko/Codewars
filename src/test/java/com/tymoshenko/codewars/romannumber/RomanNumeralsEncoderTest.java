package com.tymoshenko.codewars.romannumber;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RomanNumeralsEncoderTest {

    private final DecimalToRomanConverter conversion = new RomanNumeralsEncoderOOP();

    @ParameterizedTest
    @CsvSource({"1,I", "4,IV", "6,VI"})
    void shouldConvertToRoman_1_9(int dec, String rom) {
        assertEquals(rom, conversion.solution(dec));
    }

    @ParameterizedTest
    @CsvSource({"X,10", "XI,11", "XIV,14", "XV,15", "XX,20"})
    void convert_10_20(String rom, int dec) {
        assertEquals(rom, conversion.solution(dec));
    }

    @ParameterizedTest
    @CsvSource({"XXI,21", "XXXV,35", "LXXV,75", "LXXXIX,89", "XC,90"})
    void convert_20_90(String rom, int dec) {
        assertEquals(rom, conversion.solution(dec));
    }

    @Test
    void convertShouldCorrectlyProcessZeroesInTheMiddle() {
        assertEquals("MMLXVI", conversion.solution(2066));
    }

    @Test
    void convertMaxNumber() {
        assertEquals("MMMCMXCIX", conversion.solution(3999));
    }

}