package com.tymoshenko.codewars;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The purpose of this kata is to write a program that can do some algebra.
 * Write a function expand that takes in an expresion with a single, one character variable, and expands it.
 * The expresion is in the form (ax+b)^n where a and b are integers which may be positive or negative,
 * x is any one character long variable, and n is a natural number.
 * If a = 1, no coeficient will be placed in front of the variable.
 * If a = -1, a "-" will be placed in front of the variable.
 * <p>
 * The expanded form should be returned as a string in the form ax^b+cx^d+ex^f...
 * where a, c, and e are the coefficients of the term,
 * x is the original one character variable that was passed in the original expression and b, d, and f,
 * are the powers that x is being raised to in each term and are in decreasing order.
 * If the coeficient of a term is zero, the term should not be included.
 * If the coeficient of a term is one, the coeficient should not be included.
 * If the coeficient of a term is -1, only the "-" should be included. I
 * f the power of the term is 0, only the coeficient should be included.
 * If the power of the term is 1, the carrot and power should be excluded.
 */
class BinomialExpansionTest {
    @Test
    void testBPositive() {
        assertEquals("1", BinomialExpansion.expand("(x+1)^0"));
        assertEquals("x+1", BinomialExpansion.expand("(x+1)^1"));
        assertEquals("x^2+2x+1", BinomialExpansion.expand("(x+1)^2"));
    }

    @Test
    void testBNegative() {
        assertEquals("1", BinomialExpansion.expand("(x-1)^0"));
        assertEquals("x-1", BinomialExpansion.expand("(x-1)^1"));
        assertEquals("x^2-2x+1", BinomialExpansion.expand("(x-1)^2"));
    }

    @Test
    void testAPositive() {
        assertEquals("625m^4+1500m^3+1350m^2+540m+81", BinomialExpansion.expand("(5m+3)^4"));
        assertEquals("8x^3-36x^2+54x-27", BinomialExpansion.expand("(2x-3)^3"));
        assertEquals("1", BinomialExpansion.expand("(7x-7)^0"));
    }

    @Test
    void testANegative() {
        assertEquals("625m^4-1500m^3+1350m^2-540m+81", BinomialExpansion.expand("(-5m+3)^4"));
        assertEquals("-8k^3-36k^2-54k-27", BinomialExpansion.expand("(-2k-3)^3"));
        assertEquals("1", BinomialExpansion.expand("(-7x-7)^0"));
        assertEquals("-x-1", BinomialExpansion.expand("(-x-1)^1"));
        assertEquals("-x^3-6x^2-12x-8", BinomialExpansion.expand("(-x-2)^3"));
    }

    @Test
    void testBZero() {
        assertEquals("81t^2", BinomialExpansion.expand("(9t+0)^2"));
    }

    @Test
    void testBigN() {
        assertEquals("", BinomialExpansion.expand("(9j-9)^8"));
    }
}