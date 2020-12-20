package com.tymoshenko.codewars;

/**
 * 4 kyu
 * <p>
 * https://www.codewars.com/kata/551f23362ff852e2ab000037/train/java
 * <p>
 */
public class PyramidSlideDown {

    public static int longestSlideDown(int[][] pyramid) {
        for (int i = pyramid.length - 1; i > 0; i--) {
            for (int j = 0; j < pyramid[i].length - 1; j++) {
                pyramid[i - 1][j] += Math.max(pyramid[i][j], pyramid[i][j + 1]);
            }
        }
        return pyramid[0][0];
    }

}
