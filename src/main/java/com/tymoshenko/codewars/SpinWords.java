package com.tymoshenko.codewars;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

public class SpinWords {

    private static final String ANY_WHITESPACE_REGEX = "\\s";

    public String spinWords(String sentence) {
        String reversedSentence = "";
        if (sentence != null) {
            String[] words = sentence.split(ANY_WHITESPACE_REGEX);
            reversedSentence = reverseWords(words);
        }
        return reversedSentence;
    }

    @NotNull
    private String reverseWords(String[] words) {
        List<String> reversedWords = new LinkedList<>();
        for (String word : words) {
            reversedWords.add(reverse(word));
        }
        return String.join(" ", reversedWords);
    }

    private String reverse(String word) {
        String reversedWord = word;
        if (word == null || word.equals("") || word.trim().equals("")) {
            reversedWord = "";
        } else if (word.length() >= 5) {
            StringBuilder sb = new StringBuilder(word);
            reversedWord = sb.reverse().toString();
        }
        return reversedWord;
    }

}
