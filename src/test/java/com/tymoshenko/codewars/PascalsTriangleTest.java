package com.tymoshenko.codewars;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class PascalsTriangleTest {

    @Test
    void row0() {
        assertArrayEquals(new int[]{1}, PascalsTriangle.coefficients(0));
    }

    @Test
    void row1() {
        assertArrayEquals(new int[]{1, 1}, PascalsTriangle.coefficients(1));
    }

    @Test
    void row2() {
        assertArrayEquals(new int[]{1, 2, 1}, PascalsTriangle.coefficients(2));
    }

    @Test
    void row3() {
        assertArrayEquals(new int[]{1, 3, 3, 1}, PascalsTriangle.coefficients(3));
    }

    @Test
    void row4() {
        assertArrayEquals(new int[]{1, 4, 6, 4, 1}, PascalsTriangle.coefficients(4));
    }

    @Test
    void row8() {
        assertArrayEquals(new int[] {1, 8, 28, 56, 70, 56, 28, 8, 1}, PascalsTriangle.coefficients(8));
    }
}