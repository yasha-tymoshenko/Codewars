package com.tymoshenko.codewars.morse;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.joining;

/**
 * 4 kyu
 * https://www.codewars.com/kata/54b72c16cd7f5154e9000457/train/java
 */
public class MorseCodeDecoderAdvanced {
    private static final int PAUSE_IN_LETTER = 1;
    private static final int PAUSE_IN_WORD = 3;
    private static final int PAUSE_IN_TEXT = 7;

    private static boolean trouble = false;

    public static String decodeBits(String bits) {
        int unit = unitLength(trimZeroes(bits));
        return Arrays.stream(splitBits(bits, PAUSE_IN_TEXT, unit))
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
        int maxOnexSeq = 1;
        int minOnesSeq = bits.length();
        long zeroes = count(bits, '0');
        Pattern p = Pattern.compile("(1+)");
        Matcher m = p.matcher(bits);
        while (m.find()) {
            maxOnexSeq = Math.max(maxOnexSeq, m.group(1).length());
            minOnesSeq = Math.min(minOnesSeq, m.group(1).length());
        }
        trouble = maxOnexSeq == minOnesSeq && zeroes == 0;
        if (minOnesSeq == maxOnexSeq && (zeroes > 0 && zeroes < minOnesSeq)) {
            return (int) zeroes;
        } else if (minOnesSeq == maxOnexSeq && zeroes == maxOnexSeq) {
            return maxOnexSeq;
        } else {
            return minOnesSeq;
        }
    }

    private static String[] split(String string, String separator) {
        return string.trim().split(separator);
    }

    private static String[] splitBits(String code, int pauseLength, int unit) {
        String bits = trimZeroes(code);
        if (bits.contains("1") || pauseLength == PAUSE_IN_LETTER) {
            int delimiterLength = unit * pauseLength;
            long zeroes = count(code, '0');
            if (delimiterLength < bits.length() && zeroes >= unit) {
                return bits.split("0{" + delimiterLength + "}");
            } else {
                return new String[]{bits};
            }
        } else {
            return new String[]{bits};
        }
    }

    private static String translateBits(String bits, int unit) {
        long numberOfOnes = count(bits, '1');
        if (numberOfOnes / unit == 1 || (trouble)) return ".";
        if (numberOfOnes / unit == 3) return "-";
        return ".";
    }

    private static long count(String bits, char charToCount) {
        return bits.chars().filter(ch -> ch == charToCount).count();
    }

    private static String trimZeroes(String bits) {
        String prefixZeroes = "^0*";
        String suffixZeroes = "0*$";
        return bits.replaceAll(prefixZeroes, "").replaceAll(suffixZeroes, "");
    }
}
