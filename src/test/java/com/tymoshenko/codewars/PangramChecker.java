package com.tymoshenko.codewars;


import java.util.*;

/**
 * A pangram is a sentence that contains every single letter of the alphabet at least once.
 * For example, the sentence "The quick brown fox jumps over the lazy dog" is a pangram,
 * because it uses the letters A-Z at least once (case is irrelevant).
 * <p>
 * Given a string, detect whether or not it is a pangram.
 * Return True if it is, False if not. Ignore numbers and punctuation.
 */
public class PangramChecker {

    public boolean check(String sentence){
        char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        Set<Character> alphabet = new HashSet<>();
        for (char c : chars) {
            alphabet.add(c);
        }

        sentence = sentence.replace(" ", "");
        sentence = sentence.toLowerCase(Locale.ROOT);
        chars = sentence.toCharArray();
        Set<Character> sentenceChars = new HashSet<>();
        for (char c : chars) {
            sentenceChars.add(c);
        }

        return sentenceChars.containsAll(alphabet);
    }

}
