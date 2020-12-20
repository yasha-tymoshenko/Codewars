package com.tymoshenko.codewars;

import java.util.*;

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

    private static final Map<String, String> morseDictionary;

    public static String decode(String morseCode) {
        if (morseCode == null || morseCode.isBlank()) {
            return "";
        }
        return Arrays.stream(split(morseCode, " {3}"))
                .map(encodedWord -> Arrays.stream(split(encodedWord, " "))
                        .map(encodedSymbol -> morseDictionary.getOrDefault(encodedSymbol, ""))
                        .collect(joining()))
                .collect(joining(" "));
    }

    private static String[] split(String string, String separator) {
        return string.trim().split(separator);
    }

    static {
        morseDictionary = new LinkedHashMap<>();
        // Letters
        morseDictionary.put(".-", "A");
        morseDictionary.put("-...", "B");
        morseDictionary.put("-.-.", "C");
        morseDictionary.put("-..", "D");
        morseDictionary.put(".", "E");
        morseDictionary.put("..-.", "F");
        morseDictionary.put("--.", "G");
        morseDictionary.put("....", "H");
        morseDictionary.put("..", "I");
        morseDictionary.put(".---", "J");
        morseDictionary.put("-.-", "K");
        morseDictionary.put(".-..", "L");
        morseDictionary.put("--", "M");
        morseDictionary.put("-.", "N");
        morseDictionary.put("---", "O");
        morseDictionary.put(".--.", "P");
        morseDictionary.put("--.-", "Q");
        morseDictionary.put(".-.", "R");
        morseDictionary.put("...", "S");
        morseDictionary.put("-", "T");
        morseDictionary.put("..-", "U");
        morseDictionary.put("...-", "V");
        morseDictionary.put(".--", "W");
        morseDictionary.put("-..-", "X");
        morseDictionary.put("-.--", "Y");
        morseDictionary.put("--..", "Z");
        // Digits
        morseDictionary.put("-----", "0");
        morseDictionary.put(".----", "1");
        morseDictionary.put("..---", "2");
        morseDictionary.put("...--", "3");
        morseDictionary.put("....-", "4");
        morseDictionary.put(".....", "5");
        morseDictionary.put("-....", "6");
        morseDictionary.put("--...", "7");
        morseDictionary.put("---..", "8");
        morseDictionary.put("----.", "9");
        // Punctuation
        morseDictionary.put(".-.-.-", ".");
        morseDictionary.put("--..--", ",");
        morseDictionary.put("..--..", "?");
        morseDictionary.put(".----.", "'");
        morseDictionary.put("-.-.--", "!");
        // Special codes
        morseDictionary.put("...---...", "SOS");
    }

}
