package com.tymoshenko.codewars.morse;

import java.util.LinkedHashMap;
import java.util.Map;

public class MorseDictionary {

    private static final Map<String, String> morseToEnglishMap;

    public static String translate(String morseCode) {
        return translate(morseCode, "");
    }

    public static String translate(String morseCode, String defaultTranslation) {
        return morseToEnglishMap.getOrDefault(morseCode, defaultTranslation);
    }

    static {
        morseToEnglishMap = new LinkedHashMap<>();
        // Letters
        morseToEnglishMap.put(".-", "A");
        morseToEnglishMap.put("-...", "B");
        morseToEnglishMap.put("-.-.", "C");
        morseToEnglishMap.put("-..", "D");
        morseToEnglishMap.put(".", "E");
        morseToEnglishMap.put("..-.", "F");
        morseToEnglishMap.put("--.", "G");
        morseToEnglishMap.put("....", "H");
        morseToEnglishMap.put("..", "I");
        morseToEnglishMap.put(".---", "J");
        morseToEnglishMap.put("-.-", "K");
        morseToEnglishMap.put(".-..", "L");
        morseToEnglishMap.put("--", "M");
        morseToEnglishMap.put("-.", "N");
        morseToEnglishMap.put("---", "O");
        morseToEnglishMap.put(".--.", "P");
        morseToEnglishMap.put("--.-", "Q");
        morseToEnglishMap.put(".-.", "R");
        morseToEnglishMap.put("...", "S");
        morseToEnglishMap.put("-", "T");
        morseToEnglishMap.put("..-", "U");
        morseToEnglishMap.put("...-", "V");
        morseToEnglishMap.put(".--", "W");
        morseToEnglishMap.put("-..-", "X");
        morseToEnglishMap.put("-.--", "Y");
        morseToEnglishMap.put("--..", "Z");
        // Digits
        morseToEnglishMap.put("-----", "0");
        morseToEnglishMap.put(".----", "1");
        morseToEnglishMap.put("..---", "2");
        morseToEnglishMap.put("...--", "3");
        morseToEnglishMap.put("....-", "4");
        morseToEnglishMap.put(".....", "5");
        morseToEnglishMap.put("-....", "6");
        morseToEnglishMap.put("--...", "7");
        morseToEnglishMap.put("---..", "8");
        morseToEnglishMap.put("----.", "9");
        // Punctuation
        morseToEnglishMap.put(".-.-.-", ".");
        morseToEnglishMap.put("--..--", ",");
        morseToEnglishMap.put("..--..", "?");
        morseToEnglishMap.put(".----.", "'");
        morseToEnglishMap.put("-.-.--", "!");
        // Special codes
        morseToEnglishMap.put("...---...", "SOS");
    }
}
