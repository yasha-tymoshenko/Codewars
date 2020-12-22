package com.tymoshenko.codewars.morse;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MorseCodeDecoderTest {

    @Test
    void heyJude() {
        assertEquals("HEY JUDE", MorseCodeDecoder.decode(".... . -.--   .--- ..- -.. ."));
    }

    @Test
    void sos() {
        assertEquals("SOS", MorseCodeDecoder.decode("...---..."));
    }
}