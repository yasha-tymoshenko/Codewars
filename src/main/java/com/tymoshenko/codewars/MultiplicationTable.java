package com.tymoshenko.codewars;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Your goal is to return multiplication table for number that is always an integer from 1 to 10.
 * <p>
 * For example, a multiplication table (string) for number == 5 looks like below:
 * <p>
 * 1 * 5 = 5
 * 2 * 5 = 10
 * 3 * 5 = 15
 * 4 * 5 = 20
 * 5 * 5 = 25
 * 6 * 5 = 30
 * 7 * 5 = 35
 * 8 * 5 = 40
 * 9 * 5 = 45
 * 10 * 5 = 50
 * P. S. You can use \n in string to jump to the next line.
 * <p>
 * Note: newlines should be added between rows, but there should be no trailing newline at the end.
 */
public class MultiplicationTable {

    private MultiplicationTable() {
    }

    public static String multiTable(int num) {
        return IntStream.rangeClosed(1, 10)
                .mapToObj(i -> String.format("%d * %d = %d", i, num, i * num))
                .collect(Collectors.joining("\n"));
    }

}
