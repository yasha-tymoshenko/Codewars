package com.tymoshenko.codewars;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BigFactorialTest {

    @Test
    void basicNumberss()
    {
        assertEquals("1", BigFactorial.factorial(1));
        assertEquals("120", BigFactorial.factorial(5));
        assertEquals("362880", BigFactorial.factorial(9));
        assertEquals("1307674368000", BigFactorial.factorial(15));
    }

    @Test
    void bigNumber() {
        assertEquals("93326215443944152681699238856266700490715968264381621468592963895217599993229915608941463976156518286253697920827223758251185210916864000000000000000000000000",
                BigFactorial.factorial(146));
    }
}