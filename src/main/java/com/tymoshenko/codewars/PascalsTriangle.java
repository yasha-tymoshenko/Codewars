package com.tymoshenko.codewars;

import java.util.stream.IntStream;

/**
 * https://en.wikipedia.org/wiki/Pascal%27s_triangle
 *     1
 *    1 1
 *   1 2 1
 *  1 3 3 1
 * 1 4 6 4 1
 * ...
 */
public class PascalsTriangle {

    public static int[] coefficients(int row) {
        return IntStream.rangeClosed(0, row)
                .map(col -> coefficient(row, col))
                .toArray();
    }

    public static int coefficient(int row, int col) {
        if (row == 0 || col == 0 || col == row) {
            return 1;
        }
        return coefficient(row - 1, col - 1) + coefficient(row - 1, col);
    }

}
