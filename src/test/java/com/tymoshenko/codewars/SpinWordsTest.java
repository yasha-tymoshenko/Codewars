package com.tymoshenko.codewars;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SpinWordsTest {

    private final SpinWords spinWords = new SpinWords();

    @ParameterizedTest
    @CsvSource({
            "Green,neerG",
            "HOUSE,ESUOH",
            "train,niart",
            "школа,алокш",
            "Hogwarts,strawgoH",
            "Tree,Tree",
            "Harry Potter is the wizard who defeated Lord Voldemort.,yrraH rettoP is the draziw who detaefed Lord .tromedloV",
    })
    void spinWordsLongerThan5Letters(String text, String expected) {
        assertEquals(expected, spinWords.spinWords(text));
    }

    @ParameterizedTest
    @NullSource
    @EmptySource
    void spinWordsShouldHandleNullsAndEmptyStrings(String text) {
        assertTrue(spinWords.spinWords(text).isEmpty());
    }
}