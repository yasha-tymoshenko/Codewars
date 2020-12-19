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

    private static final String BLANK_STRING = "";
    private static final String MORSE_LETTER_SEPARATOR = " ";
    private static final String MORSE_WORD_SEPARATOR = MORSE_LETTER_SEPARATOR + "{3}";
    private static final String ENGLISH_LETTER_SEPARATOR = BLANK_STRING;
    private static final String ENGLISH_WORD_SEPARATOR = MORSE_LETTER_SEPARATOR;

    private static final Map<String, String> morseDictionary;

    public static String decode(String morseCode) {
        if (morseCode == null || morseCode.isBlank()) {
            return BLANK_STRING;
        }
        return Arrays.stream(split(morseCode, MORSE_WORD_SEPARATOR))
                .map(encodedWord -> Arrays.stream(split(encodedWord, MORSE_LETTER_SEPARATOR))
                        .map(encodedSymbol -> morseDictionary.getOrDefault(encodedSymbol, BLANK_STRING))
                        .collect(joining(ENGLISH_LETTER_SEPARATOR)))
                .collect(joining(ENGLISH_WORD_SEPARATOR));
    }

    private static String[] split(String morseCode, String morseWordSeparator) {
        return morseCode.trim().split(morseWordSeparator);
    }

    static {
        morseDictionary = new LinkedHashMap<>();
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
    }

}
