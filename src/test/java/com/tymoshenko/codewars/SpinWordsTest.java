package com.tymoshenko.codewars;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpinWordsTest {

    private SpinWords spinWords = new SpinWords();
    private String arg;
    private String expected;
    private String actual;

    @Test
    void reverseWord5LettersAngKeepCase() {
        arg = "Green";
        expected = "Neerg";
        test();
    }

    @Test
    void reverseWords5LettersAllUpperCase() {

    }

    @Test
    void reverseWords5LettersAllLowerCase() {

    }

    @Test
    void reverseWords5LettersRussian() {

    }

    @Test
    void reverseWordLongerThan5Letters() {

    }

    @Test
    void noReverseWordLessThan5Letter() {

    }

    @Test
    void exceptionWhenNullArg() {

    }

    @Test
    void exceptionWhenEmptyString() {

    }

    @Test
    void reverseAllWordsLongerThan5Letters() {

    }

    private void test() {
        actual = spinWords.spinWords(arg);
        assertEquals(expected, actual);
    }

}