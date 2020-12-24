package com.tymoshenko.codewars.morse;

import java.util.LinkedHashMap;
import java.util.Map;

public class MorseDictionary {

    private static final Map<String, String> dictionary;

    public static String translate(String morseCode) {
        return translate(morseCode, "");
    }

    public static String translate(String morseCode, String defaultTranslation) {
        return dictionary.getOrDefault(morseCode, defaultTranslation);
    }

    static {
        dictionary = new LinkedHashMap<>();
        // Letters
        dictionary.put(".-", "A");
        dictionary.put("-...", "B");
        dictionary.put("-.-.", "C");
        dictionary.put("-..", "D");
        dictionary.put(".", "E");
        dictionary.put("..-.", "F");
        dictionary.put("--.", "G");
        dictionary.put("....", "H");
        dictionary.put("..", "I");
        dictionary.put(".---", "J");
        dictionary.put("-.-", "K");
        dictionary.put(".-..", "L");
        dictionary.put("--", "M");
        dictionary.put("-.", "N");
        dictionary.put("---", "O");
        dictionary.put(".--.", "P");
        dictionary.put("--.-", "Q");
        dictionary.put(".-.", "R");
        dictionary.put("...", "S");
        dictionary.put("-", "T");
        dictionary.put("..-", "U");
        dictionary.put("...-", "V");
        dictionary.put(".--", "W");
        dictionary.put("-..-", "X");
        dictionary.put("-.--", "Y");
        dictionary.put("--..", "Z");
        // Digits
        dictionary.put("-----", "0");
        dictionary.put(".----", "1");
        dictionary.put("..---", "2");
        dictionary.put("...--", "3");
        dictionary.put("....-", "4");
        dictionary.put(".....", "5");
        dictionary.put("-....", "6");
        dictionary.put("--...", "7");
        dictionary.put("---..", "8");
        dictionary.put("----.", "9");
        // Punctuation
        dictionary.put(".-.-.-", ".");
        dictionary.put("--..--", ",");
        dictionary.put("..--..", "?");
        dictionary.put(".----.", "'");
        dictionary.put("-.-.--", "!");
        // Special codes
        dictionary.put("...---...", "SOS");
    }
}
