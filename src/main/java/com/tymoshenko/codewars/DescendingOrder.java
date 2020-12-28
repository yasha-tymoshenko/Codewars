package com.tymoshenko.codewars;

import java.util.Comparator;

import static java.util.stream.Collectors.joining;

/**
 * Your task is to make a function that can take any non-negative integer as an argument
 * and return it with its digits in descending order.
 * Essentially, rearrange the digits to create the highest possible number.
 */
public class DescendingOrder {

    public static int sortDesc(final int num) {
        return Integer.parseInt(Integer.toString(num)
                        .chars()
                        .mapToObj(c -> String.valueOf((char) c))
                        .sorted(Comparator.reverseOrder())
                        .collect(joining()));
    }

}


