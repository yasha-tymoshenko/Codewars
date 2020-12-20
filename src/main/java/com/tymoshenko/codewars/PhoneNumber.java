package com.tymoshenko.codewars;

import java.util.stream.IntStream;

/**
 * 6kyu
 *
 * https://www.codewars.com/kata/525f50e3b73515a6db000b83/train/java
 */
public class PhoneNumber {

    public static String createPhoneNumber(int[] numbers) {
        return String.format("(%d%d%d) %d%d%d-%d%d%d%d", IntStream.of(numbers).boxed().toArray()
        );
    }

}
