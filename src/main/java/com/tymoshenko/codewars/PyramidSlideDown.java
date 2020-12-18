package com.tymoshenko.codewars;

/**
 * 4 kyu
 * <p>
 * https://www.codewars.com/kata/551f23362ff852e2ab000037/train/java
 * <p>
 * Lyrics...
 * <p>
 * Pyramids are amazing! Both in architectural and mathematical sense.
 * If you have a computer, you can mess with pyramids even if you are not in Egypt at the time.
 * For example, let's consider the following problem. Imagine that you have a pyramid built of numbers, like this one here:
 * <p>
 * /3/
 * \7\ 4
 * 2 \4\ 6
 * 8 5 \9\ 3
 * Here comes the task...
 * <p>
 * Let's say that the 'slide down' is the maximum sum of consecutive numbers from the top to the bottom of the pyramid.
 * As you can see, the longest 'slide down' is 3 + 7 + 4 + 9 = 23
 * <p>
 * Your task is to write a function longestSlideDown (in ruby/crystal/julia: longest_slide_down)
 * that takes a pyramid representation as argument and returns its' largest 'slide down'. For example,
 * <p>
 * longestSlideDown [[3], [7, 4], [2, 4, 6], [8, 5, 9, 3]] => 23
 * By the way...
 * <p>
 * My tests include some extraordinarily high pyramids so as you can guess, brute-force method is a bad idea unless you
 * have a few centuries to waste. You must come up with something more clever than that.
 * <p>
 * (c) This task is a lyrical version of the Problem 18 and/or Problem 67 on ProjectEuler.
 */
public class PyramidSlideDown {

    private PyramidSlideDown() {
    }

    public static int longestSlideDown(int[][] pyramid) {
        int[][] copy = new int[pyramid.length][];
        for (int i = 0; i < pyramid.length; i++) {
            copy[i] = new int[pyramid[i].length];
            for (int j = 0; j < pyramid[i].length; j++) {
                copy[i][j] = pyramid[i][j];
            }
        }

        int max = 0;
        for (int i = 0; i < pyramid.length; i++) {
            for (int j = 0; j < pyramid[i].length; j++) {
                if (i + 1 < pyramid.length) {
                    // left child
                    if (pyramid[i + 1][j] < copy[i + 1][j] + pyramid[i][j])
                        pyramid[i + 1][j] = copy[i + 1][j] + pyramid[i][j];
                    // right child
                    if (pyramid[i + 1][j + 1] < copy[i + 1][j + 1] + pyramid[i][j])
                        pyramid[i + 1][j + 1] = copy[i + 1][j + 1] + pyramid[i][j];
                } else {
                    max = Math.max(pyramid[i][j], max);
                }
            }
        }
        return max;
    }

}
