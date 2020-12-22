package com.tymoshenko.codewars;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 3 kyu
 * <p>
 * https://www.codewars.com/kata/540d0fdd3b6532e5c3000b5b/train/java
 */
public class BinomialExpansion {

    private static int a;
    private static char x;
    private static int b;
    private static int n;

    public static String expand(String expr) {
        parse(expr);
        return expand();
    }

    /**
     * expr - (ax+b)^n
     */
    private static void parse(String expr) {
        Pattern pattern = Pattern.compile("\\((?<a>-?\\d*)(?<x>[a-zA-Z])(?<b>\\s*[+-]\\s*\\d+)\\)\\^(?<n>\\d+)");
        Matcher matcher = pattern.matcher(expr);
        if (matcher.matches()) {
            a = numberToCoefficient(matcher.group("a"));
            x = matcher.group("x").charAt(0);
            b = numberToCoefficient(matcher.group("b"));
            n = numberToCoefficient(matcher.group("n"));
        } else {
            throw new IllegalArgumentException("Unable to parse the input.");
        }
    }

    private static String expand() {
        if (n == 0) return "1";
        if (n == 1) return coefficient(a) + x + addendum(b);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <= n; i++) {
            long ai = (long) Math.pow(a, (double) n - i);
            long bi = (long) Math.pow(b, i);
            long k = pascalCoefficient(n, i) * ai * bi;
            if (k == 0) {
                continue;
            }
            if (i == 0) {
                sb.append(coefficient(k)).append(x).append(power(n - i));
            } else if (i == n) {
                sb.append(addendum(bi));
            } else {
                sb.append(addendum(k)).append(x).append(power(n - i));
            }
        }
        return sb.toString();
    }

    private static int numberToCoefficient(String str) {
        if (str == null || str.isBlank()) {
            return 1;
        }
        if (str.equals("-")) {
            return -1;
        }
        return Integer.parseInt(str);
    }

    public static int pascalCoefficient(int row, int col) {
        if (row == 0 || col == 0 || col == row) {
            return 1;
        }
        return pascalCoefficient(row - 1, col - 1) + pascalCoefficient(row - 1, col);
    }

    private static String coefficient(long k) {
        if (k == 1) return "";
        if (k == -1) return "-";
        return Long.toString(k);
    }

    private static String addendum(long a) {
        if (a == 0) {
            return "";
        }
        return (a < 0 ? "" : "+") + a;
    }

    /**
     * @param n >= 0
     */
    private static String power(int n) {
        if (n <= 1) {
            return "";
        }
        return "^" + n;
    }

}
