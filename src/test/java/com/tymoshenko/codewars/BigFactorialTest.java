package com.tymoshenko.codewars;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BigFactorialTest {
    @ParameterizedTest
    @CsvSource(value = {"1!1", "5!120", "9!362880", "15!1307674368000",
            "146!117499720439091082394795827163851716458059626095095089986332811032878850206552392125984170354456588787206137541623641592892713800361142894297576474973649309989915800122274950466132528824767026591868029083847822165619507200000000000000000000000000000000000"}, delimiter = '!')
    void factorial(int n, String factorial) {
        assertEquals(factorial, BigFactorial.factorial(n));
    }
}