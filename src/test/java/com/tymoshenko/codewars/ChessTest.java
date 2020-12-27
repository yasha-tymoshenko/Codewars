package com.tymoshenko.codewars;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChessTest {

    @ParameterizedTest
    @CsvSource({"a1,c1,2", "a1,f1,3", "a1,f3,3", "a1,f4,4", "a1,f7,5"})
    void knightMoves(String start, String finish, int expectedMinMoves) {
        assertEquals(expectedMinMoves, Chess.knight(start, finish));
    }
}