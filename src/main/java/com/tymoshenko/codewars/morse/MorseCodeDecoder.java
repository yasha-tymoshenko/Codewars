package com.tymoshenko.codewars.morse;

import java.util.Arrays;

import static java.util.stream.Collectors.joining;

/**
 * 6 kyu
 * <p>
 * https://www.codewars.com/kata/54b724efac3d5402db00065e
 * <p>
 * MorseCodeDecoder.decode(".... . -.--   .--- ..- -.. .")
 * //should return "HEY JUDE"
 */
public class MorseCodeDecoder {

    public static String decode(String morseCode) {
        if (morseCode == null || morseCode.isBlank()) {
            return "";
        }
        return Arrays.stream(split(morseCode, " {3}"))
                .map(encodedWord -> Arrays.stream(split(encodedWord, " "))
                        .map(MorseDictionary::translate)
                        .collect(joining()))
                .collect(joining(" "));
    }

    private static String[] split(String string, String separator) {
        return string.trim().split(separator);
    }
}
