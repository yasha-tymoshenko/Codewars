package com.tymoshenko.codewars;

import java.math.BigDecimal;
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
            BigDecimal ai = BigDecimal.valueOf(a).pow(n - i);
            BigDecimal bi = BigDecimal.valueOf(b).pow(i);
            BigDecimal k = ai.multiply(bi).multiply(BigDecimal.valueOf(pascalCoefficient(n, i)));
            if (i == 0) {
                if (!k.equals(BigDecimal.ZERO)) {
                    sb.append(coefficient(k)).append(x).append(power(n - i));
                }
            } else if (i == n) {
                sb.append(addendum(bi));
            } else {
                if (!k.equals(BigDecimal.ZERO)) {
                    sb.append(addendum(k)).append(x).append(power(n - i));
                }
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

    private static String coefficient(int k) {
        if (k == 1) return "";
        if (k == -1) return "-";
        return Integer.toString(k);
    }

    private static String coefficient(BigDecimal k) {
        if (k.equals(BigDecimal.ONE)) {
            return "";
        }
        if (k.equals(BigDecimal.valueOf(-1))) {
            return "-";
        }
        return k.toString();
    }

    private static String addendum(int a) {
        if (a == 0) {
            return "";
        }
        return (a < 0 ? "" : "+") + a;
    }

    private static String addendum(BigDecimal a) {
        if (a.equals(BigDecimal.ZERO)) {
            return "";
        }
        return (a.signum() < 0 ? "" : "+") + a.toString();
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
