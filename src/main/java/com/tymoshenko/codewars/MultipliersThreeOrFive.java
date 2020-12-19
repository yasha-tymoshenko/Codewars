package com.tymoshenko.codewars;

import java.util.stream.IntStream;

public class MultipliersThreeOrFive {

    public int solution(int number) {
        if (number <= 0) {
            return 0;
        }
        return IntStream.range(1, number)
                .filter(n -> n % 3 == 0 || n % 5 == 0)
                .sum();
    }

}
