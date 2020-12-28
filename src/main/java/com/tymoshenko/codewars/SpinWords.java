package com.tymoshenko.codewars;

import java.util.Arrays;

import static java.util.stream.Collectors.joining;

public class SpinWords {

    private static final String ANY_WHITESPACE_REGEX = "\\s";

    public String spinWords(String sentence) {
        return sentence == null ? "" : Arrays.stream(
                sentence.split(ANY_WHITESPACE_REGEX))
                .map(this::reverse)
                .collect(joining(" "));
    }

    private String reverse(String word) {
        if (word == null || word.isBlank()) {
            return "";
        } else if (word.length() < 5) {
            return word;
        } else {
            return new StringBuilder(word).reverse().toString();
        }
    }

}
