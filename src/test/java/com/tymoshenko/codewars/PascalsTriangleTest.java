package com.tymoshenko.codewars;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.params.provider.Arguments.of;

class PascalsTriangleTest {

    @ParameterizedTest
    @MethodSource
    void pascalsTriangle(int rowNumber, int[] expectedTriangleRow) {
        assertArrayEquals(expectedTriangleRow, PascalsTriangle.coefficients(rowNumber));
    }

    @SuppressWarnings("unused")
    private static Stream<Arguments> pascalsTriangle() {
        return Stream.of(
                pascal(0, 1),
                pascal(1, 1, 1),
                pascal(2, 1, 2, 1),
                pascal(3, 1, 3, 3, 1),
                pascal(4, 1, 4, 6, 4, 1),
                pascal(5, 1, 5, 10, 10, 5, 1),
                pascal(8, 1, 8, 28, 56, 70, 56, 28, 8, 1)
        );
    }

    private static Arguments pascal(int row, int... coefficients) {
        return of(row, coefficients);
    }
}