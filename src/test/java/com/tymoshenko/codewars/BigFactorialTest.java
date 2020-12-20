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
        assertEquals("117499720439091082394795827163851716458059626095095089986332811032878850206552392125984170354456588787206137541623641592892713800361142894297576474973649309989915800122274950466132528824767026591868029083847822165619507200000000000000000000000000000000000",
                BigFactorial.factorial(146));
    }
}