package com.tymoshenko.codewars;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RomanNumeralsEncoderTest {

    private final DecimalToRomanConverter conversion = new RomanNumeralsEncoderCleverer();

    @Test
    void shouldConvertToRoman_1_9() {
        assertEquals( "I", conversion.solution(1));
        assertEquals( "IV", conversion.solution(4));
        assertEquals( "VI", conversion.solution(6));
    }

    @Test
    void convert_10_20() {
        assertEquals("X", conversion.solution(10));
        assertEquals("XI", conversion.solution(11));
        assertEquals("XIV", conversion.solution(14));
        assertEquals("XV", conversion.solution(15));
        assertEquals("XVIII", conversion.solution(18));
        assertEquals("XX", conversion.solution(20));
    }

    @Test
    void convert_20_90() {
        assertEquals("XXI", conversion.solution(21));
        assertEquals("XXXV", conversion.solution(35));
        assertEquals("LXXV", conversion.solution(75));
        assertEquals("LXXXIX", conversion.solution(89));
        assertEquals("XC", conversion.solution(90));
    }

    @Test
    void convertShouldCorrectlyProcessZeroesInTheMiddle() {
        assertEquals("MMLXVI", conversion.solution(2066));
    }

    @Test
    void convertMaxNumber() {
        assertEquals("MMMCMXCIX", conversion.solution(3999));
    }

    @Test
    void exceptionWhenConvertingZero() {

    }
}