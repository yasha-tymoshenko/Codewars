package com.tymoshenko.codewars;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GreedIsGoodTest {

    @Test
    void testExample() {
        assertEquals(250, GreedIsGood.greedy(new int[]{5, 1, 3, 4, 1}));
        assertEquals(1100, GreedIsGood.greedy(new int[]{1, 1, 1, 3, 1}));
        assertEquals(450, GreedIsGood.greedy(new int[]{2, 4, 4, 5, 4}));
    }

}