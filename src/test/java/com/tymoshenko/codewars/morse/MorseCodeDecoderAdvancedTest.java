package com.tymoshenko.codewars.morse;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * In this kata you have to write a Morse code decoder for wired electrical telegraph.
 * Electric telegraph is operated on a 2-wire line with a key that, when pressed, connects the wires together, which can be detected on a remote station. The Morse code encodes every character being transmitted as a sequence of "dots" (short presses on the key) and "dashes" (long presses on the key).
 * <p>
 * When transmitting the Morse code, the international standard specifies that:
 * <p>
 * "Dot" – is 1 time unit long.
 * "Dash" – is 3 time units long.
 * Pause between dots and dashes in a character – is 1 time unit long.
 * Pause between characters inside a word – is 3 time units long.
 * Pause between words – is 7 time units long.
 * However, the standard does not specify how long that "time unit" is. And in fact different operators would transmit at different speed. An amateur person may need a few seconds to transmit a single character, a skilled professional can transmit 60 words per minute, and robotic transmitters may go way faster.
 * <p>
 * For this kata we assume the message receiving is performed automatically by the hardware that checks the line periodically, and if the line is connected (the key at the remote station is down), 1 is recorded, and if the line is not connected (remote key is up), 0 is recorded. After the message is fully received, it gets to you for decoding as a string containing only symbols 0 and 1.
 * <p>
 * For example, the message HEY JUDE, that is ···· · −·−−   ·−−− ··− −·· · may be received as follows:
 * <p>
 * 1100110011001100000011000000111111001100111111001111110000000000000011001111110011111100111111000000110011001111110000001111110011001100000011
 * <p>
 * As you may see, this transmission is perfectly accurate according to the standard, and the hardware sampled the line exactly two times per "dot".
 * <p>
 * That said, your task is to implement two functions:
 * <p>
 * Function decodeBits(bits), that should find out the transmission rate of the message, correctly decode the message to dots ., dashes - and spaces (one between characters, three between words) and return those as a string. Note that some extra 0's may naturally occur at the beginning and the end of a message, make sure to ignore them. Also if you have trouble discerning if the particular sequence of 1's is a dot or a dash, assume it's a dot.
 * 2. Function decodeMorse(morseCode), that would take the output of the previous function and return a human-readable string.
 * <p>
 * NOTE: For coding purposes you have to use ASCII characters . and -, not Unicode characters.
 * <p>
 * The Morse code table is preloaded for you (see the solution setup, to get its identifier in your language).
 * <p>
 * <p>
 * Eg:
 * morseCodes(".--") //to access the morse translation of ".--"
 * All the test strings would be valid to the point that they could be reliably decoded as described above, so you may skip checking for errors and exceptions, just do your best in figuring out what the message is!
 * <p>
 * Good luck!
 */
class MorseCodeDecoderAdvancedTest {
    @Test
    void testExampleFromDescription() {
        assertEquals("HEY JUDE",
                decode(
                                        "11001100110011000000110000001111110011001111110011111100000000000000" +
                                                "11001111110011111100111111000000110011001111110000001111110011001100000011"));
    }

    @Test
    void extraZeroes() {
        assertEquals("HEY JUDE",
                decode(
                        "0011001100110011000000110000001111110011001111110011111100000000000000" +
                                "11001111110011111100111111000000110011001111110000001111110011001100000011000"));
    }

    @Test
    void oneLetter() {
        // I -> ..
        assertEquals("I", decode("1010"));
    }

    private String decode(String bits) {
        return MorseCodeDecoderAdvanced.decodeMorse(MorseCodeDecoderAdvanced.decodeBits(bits));
    }
}