package com.tymoshenko.codewars;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StringSplitTest {

    @Test
    void testEvenString() {
        assertEquals("[ab, cd, ef]", Arrays.toString(StringSplit.solution("abcdef")));
        assertEquals("[He, ll, oW, or, ld]", Arrays.toString(StringSplit.solution("HelloWorld")));
    }

    @Test
    void testOddString() {
        assertEquals("[ab, cd, e_]", Arrays.toString(StringSplit.solution("abcde")));
        assertEquals("[Lo, ve, Pi, zz, a_]", Arrays.toString(StringSplit.solution("LovePizza")));
    }

}