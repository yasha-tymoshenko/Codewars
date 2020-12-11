package com.tymoshenko.codewars;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StringSplitTest {

    @Test
    public void testEvenString() {
        String s = "abcdef";
        String s1 = "HelloWorld";
        assertEquals("[ab, cd, ef]", Arrays.toString(StringSplit.solution(s)));
        assertEquals("[He, ll, oW, or, ld]", Arrays.toString(StringSplit.solution(s1)));
    }

    @Test
    public void testOddString() {
        String s = "abcde";
        String s1 = "LovePizza";
        assertEquals("[ab, cd, e_]", Arrays.toString(StringSplit.solution(s)));
        assertEquals("[Lo, ve, Pi, zz, a_]", Arrays.toString(StringSplit.solution(s1)));
    }

}