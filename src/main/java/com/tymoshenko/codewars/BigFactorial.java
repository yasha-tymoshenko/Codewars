package com.tymoshenko.codewars;

import java.math.BigInteger;

/**
 * 4 kyu
 *
 * https://www.codewars.com/kata/557f6437bf8dcdd135000010/train/java
 */
public class BigFactorial {

    public static String factorial(int n) {
        return f(n).toString();
    }

    private static BigInteger f(int n) {
        return n == 0 ? BigInteger.ONE : BigInteger.valueOf(n).multiply(f(n - 1));
    }

}
