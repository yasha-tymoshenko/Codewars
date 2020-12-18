package com.tymoshenko.codewars;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MultipliersThreeOrFiveTest {
   
    @Test
    void test() {
        assertEquals(23, new MultipliersThreeOrFive().solution(10));
    }

}