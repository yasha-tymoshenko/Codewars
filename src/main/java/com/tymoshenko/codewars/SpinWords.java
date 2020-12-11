package com.tymoshenko.codewars;

import java.util.LinkedList;
import java.util.List;

public class SpinWords {

    public String spinWords(String sentence) {
        // Split by any whitespace.
        String[] words = sentence.split("\\s");

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
