package com.tymoshenko.codewars;

import java.util.Arrays;
import java.util.List;

/**
 * Create a function taking a positive integer as its parameter and returning a string containing the Roman Numeral
 * representation of that integer.
 * <p>
 * Modern Roman numerals are written by expressing each digit separately starting with the left most digit and skipping
 * any digit with a value of zero.
 * <p>
 * In Roman numerals 1990 is rendered:
 * 1000=M, 900=CM, 90=XC; resulting in MCMXC.
 * 2008 is written as 2000=MM, 8=VIII; or MMVIII.
 * 1666 uses each Roman symbol in descending order: MDCLXVI.
 * <p>
 * Example:
 * <p>
 * conversion.solution(1000); //should return "M"
 * Help:
 * <p>
 * Symbol    Value
 * I          1
 * V          5
 * X          10
 * L          50
 * C          100
 * D          500
 * M          1,000
 * Remember that there can't be more than 3 identical sfymbols in a row.
 * <p>
 * 1987 = MCMLXXXVII
 */
public class RomanNumeralsEncoder {

    private static final int MAX_IDENTICAL_SYMBOLS_IN_A_ROW = 3;

    RomanNumeralsEncoder() {
    }

    public String solution(int n) {
        return numberToRoman(n);
    }

    private String numberToRoman(int n) {
        String number = Integer.toString(n);
        // 1 -> 1, 2 -> 10, 3 -> 100... (the magnitude is powers of 10 of the most significant digit in the number).
        int magnitude = number.length() - 1;
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < number.length(); i++) {
            int mantissa = Integer.parseInt("" + number.charAt(i));
            if (mantissa == 0) {
                continue;
            }
            int decade = magnitude--;
            result.append(numberToRoman(mantissa, decade));
        }

        return result.toString();
    }

    private StringBuilder numberToRoman(int mantissa, int decade) {
        StringBuilder result = new StringBuilder(RomanNumber.NOT_A_NUMBER.name());
        if ((mantissa == 1 || mantissa == 5)
                && decade <= 3) {
            int n = mantissa * (int) Math.pow(10, decade);
            result = new StringBuilder(RomanNumber.fromDecimalValue(n).name());
        } else if (decade == 0) {
            result = new StringBuilder(digitToRoman(mantissa));
        } else {
            result = RomanNumber.giveClosestRomanNumber(mantissa, decade);
        }
        return result;
    }

    private String digitToRoman(int digit) {
        String result = RomanNumber.NOT_A_NUMBER.name();
        // I, V, X
        RomanNumber romanNumber = RomanNumber.fromDecimalValue(digit);
        if (RomanNumber.NOT_A_NUMBER != romanNumber) {
            result = romanNumber.name();
        } else if (digit <= 3) {
            int sum = 2;
            result = RomanNumber.I.name() + RomanNumber.I.name();
            while (sum < digit) {
                result += RomanNumber.I.name();
                sum += RomanNumber.I.decimalValue;
            }
        } else if (digit == 4) {
            result = RomanNumber.I.name() + RomanNumber.V.name();
        } else if (digit <= 8) {
            int sum = 6;
            result = RomanNumber.V.name() + RomanNumber.I.name();
            while (sum < digit) {
                result += RomanNumber.I.name();
                sum += RomanNumber.I.decimalValue;
            }
        } else if (digit == 9) {
            result = RomanNumber.I.name() + RomanNumber.X.name();
        }
        return result;
    }

    enum RomanNumber {
        // Do we really need other than 1,5,10,50,100,500,1000?
        NOT_A_NUMBER(0),
        I(1),
        //        II(2),
//        III(3),
//        IV(4),
        V(5),
        //        VI(6),
//        VII(7),
//        VIII(8),
//        IX(9),
        X(10),
        L(50),
        C(100),
        D(500),
        M(1000);
        private final int decimalValue;

        private static final List<RomanNumber> ONES = Arrays.asList(I, X, C, M);
        private static final List<RomanNumber> FIVES = Arrays.asList(V, L, D);


        RomanNumber(int decimalValue) {
            this.decimalValue = decimalValue;
        }

        int add(RomanNumber other) {
            return this.decimalValue + other.decimalValue;
        }

        boolean equalsToDecimal(int decimalValue) {
            return this.decimalValue == decimalValue;
        }

        static StringBuilder giveClosestRomanNumber(int mantissa, int decade) {
            StringBuilder result = new StringBuilder();
            RomanNumber romanNumber = NOT_A_NUMBER;
            final int decimal = mantissa * (int) Math.pow(10, decade);
            int identicalSymbols = 1;

            if (mantissa < 4 && decade <= 3) {
                // 1,2,3 and x10, x100, x1000.
                romanNumber = ONES.get(decade);
                result.append(romanNumber);
                int sum = romanNumber.decimalValue;
                while (sum < decimal && identicalSymbols <= MAX_IDENTICAL_SYMBOLS_IN_A_ROW) {
                    result.append(romanNumber);
                    sum += romanNumber.decimalValue;
                    identicalSymbols++;
                }
            } else if (mantissa == 4 && decade < 3) {
                // 4, 40, 400.
                romanNumber = FIVES.get(decade);
                RomanNumber one = ONES.get(decade);
                result.append(one).append(romanNumber);
            } else if (mantissa > 4 && mantissa < 9 && decade <= 3) {
                // 5, 6, 7, 8 and x10, x100.
                romanNumber = FIVES.get(decade);
                result.append(romanNumber);
                RomanNumber one = ONES.get(decade);
                int sum = romanNumber.decimalValue;
                identicalSymbols = 0;
                while (sum < decimal && identicalSymbols <= MAX_IDENTICAL_SYMBOLS_IN_A_ROW) {
                    result.append(one);
                    sum += one.decimalValue;
                    identicalSymbols++;
                }
            } else if (mantissa == 9 && decade < 3) {
                // 9, 90, 900.
                RomanNumber one = ONES.get(decade);
                RomanNumber ten = ONES.get(decade + 1);
                result.append(one).append(ten);
            }
            return result;
        }

        static RomanNumber fromDecimalValue(int decimalValue) {
            for (RomanNumber value : values()) {
                if (value.decimalValue == decimalValue) {
                    return value;
                }
            }
            return NOT_A_NUMBER;
        }
    }
}
