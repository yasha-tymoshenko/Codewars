package com.tymoshenko.codewars;

import java.util.Arrays;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

/**
 * 5 kyu
 * <p>
 * https://www.codewars.com/kata/5270d0d18625160ada0000e4/train/java
 * <p>
 * Greed is a dice game played with five six-sided dice.
 * <p>
 * Three 1's => 1000 points
 * Three 6's =>  600 points
 * Three 5's =>  500 points
 * Three 4's =>  400 points
 * Three 3's =>  300 points
 * Three 2's =>  200 points
 * One   1   =>  100 points
 * One   5   =>   50 point
 */
public class GreedIsGood {

    public static int greedy(int[] dice) {
        return (int) Arrays.stream(dice)
                .boxed()
                .collect(groupingBy(die -> die, counting()))
                .entrySet()
                .stream()
                .mapToLong(entry -> score(entry.getKey(), entry.getValue()))
                .sum();
    }

    private static long score(int die, long times) {
        return (times / 3) * tripleScore(die) + (times % 3) * singleScore(die);
    }

    private static int singleScore(int die) {
        switch (die) {
            case 1:
                return 100;
            case 5:
                return 50;
            default:
                return 0;
        }
    }

    private static int tripleScore(int die) {
        if (die == 1) {
            return 1000;
        } else {
            return die * 100;
        }
    }
}
