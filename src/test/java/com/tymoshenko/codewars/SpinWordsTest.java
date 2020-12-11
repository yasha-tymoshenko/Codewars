package com.tymoshenko.codewars;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpinWordsTest {

    private final SpinWords spinWords = new SpinWords();

    private String arg;
    private String expected;

    @Test
    void reverseWord5LettersAngKeepCase() {
        arg = "Green";
        expected = "neerG";
        test();
    }

    @Test
    void reverseWords5LettersAllUpperCase() {
        arg = "HOUSE";
        expected = "ESUOH";
        test();
    }

    @Test
    void reverseWords5LettersAllLowerCase() {
        arg = "train";
        expected = "niart";
        test();
    }

    @Test
    void reverseWords5LettersRussian() {
        arg = "школа";
        expected = "алокш";
        test();
    }

    @Test
    void reverseWordLongerThan5Letters() {
        arg = "Hogwarts";
        expected = "strawgoH";
        test();
    }

    @Test
    void noReverseWordLessThan5Letter() {
        arg = "Tree";
        expected = "Tree";
        test();
    }

    @Test
    void exceptionWhenNullArg() {
        assertThrows(IllegalArgumentException.class, () -> spinWords.spinWords(null));
    }

    @Test
    void exceptionWhenEmptyString() {
        assertThrows(IllegalArgumentException.class, () -> spinWords.spinWords(""));
    }

    @Test
    void exceptionWhenBlankString() {
        assertThrows(IllegalArgumentException.class, () -> spinWords.spinWords("    "));
    }

    @Test
    void reverseAllWordsLongerThan5Letters() {
        arg = "Harry Potter is a wizard who defeated Lord Voldemort.";
        expected = "yrraH rettoP is a draziw who detaefed Lord .tromedloV";
        test();
    }

    private void test() {
        String actual = spinWords.spinWords(arg);
        assertEquals(expected, actual);
    }

}