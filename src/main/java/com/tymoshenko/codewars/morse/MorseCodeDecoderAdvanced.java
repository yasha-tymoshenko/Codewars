package com.tymoshenko.codewars.morse;

import java.util.Arrays;

import static java.util.stream.Collectors.joining;

/**
 * 4 kyu
 * https://www.codewars.com/kata/54b72c16cd7f5154e9000457/train/java
 */
public class MorseCodeDecoderAdvanced {
    private static final int PAUSE_IN_LETTER = 1;
    private static final int PAUSE_IN_WORD = 3;
    private static final int PAUSE_IN_TEXT = 7;

    public static String decodeBits(String bits) {
        int unit = unitLength(bits);
        return Arrays.stream(splitBits((bits), PAUSE_IN_TEXT, unit))
                .map(wordBits -> Arrays.stream(splitBits(wordBits, PAUSE_IN_WORD, unit))
                        .map(morseSymbolBits -> Arrays.stream(splitBits(morseSymbolBits, PAUSE_IN_LETTER, unit))
                                .map(dashOrDot -> translateBits(dashOrDot, unit))
                                // Morse symbol.
                                .collect(joining()))
                        // Symbols in a word.
                        .collect(joining(" ")))
                // Morse encoded text.
                .collect(joining("   "));
    }

    public static String decodeMorse(String morseCode) {
        return Arrays.stream(split(morseCode, " {3}"))
                .map(encodedWord -> Arrays.stream(split(encodedWord, " "))
                        .map(MorseDictionary::translate)
                        .collect(joining()))
                .collect(joining(" "));
    }

    private static int unitLength(String bits) {
        StringBuilder sb = new StringBuilder("1");
        for (int i = 1; i < bits.length() - 1; i++) {
            sb.append("1");
            if (bits.contains(sb)) {
                return i + 1;
            }
        }
        return 1;
    }

    private static String[] split(String string, String separator) {
        return string.trim().split(separator);
    }

    private static String[] splitBits(String code, int pauseLength, int unit) {
        return trimZeroes(code).split("0{" + unit * pauseLength + "}");
    }

    private static String translateBits(String bits, int unit) {
        long numberOfOnes = bits.chars().filter(ch -> ch == '1').count();
        if (numberOfOnes / unit == 1) return ".";
        if (numberOfOnes / unit == 3) return "-";
        return ".";
    }

    private static String trimZeroes(String bits) {
        return bits.replaceAll("^0*", "").replaceAll("0{2,}$", "");
    }
}
