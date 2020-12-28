package com.tymoshenko.codewars;

import java.util.stream.IntStream;

import static java.util.stream.Collectors.joining;

/**
 * Your goal is to return multiplication table for number that is always an integer from 1 to 10.
 */
public class MultiplicationTable {

    public static String multiTable(int num) {
        return IntStream.rangeClosed(1, 10)
                .mapToObj(i -> String.format("%d * %d = %d", i, num, i * num))
                .collect(joining("\n"));
    }

}
