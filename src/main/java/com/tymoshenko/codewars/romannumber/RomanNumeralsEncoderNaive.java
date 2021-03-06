package com.tymoshenko.codewars.romannumber;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unused")
public class RomanNumeralsEncoderNaive implements DecimalToRomanConverter {

    private static final int MAX_IDENTICAL_SYMBOLS_IN_A_ROW = 3;

    @Override
    public String solution(int n) {
        String decimalNumber = Integer.toString(n);
        int mantissa;
        int decade = decimalNumber.length() - 1;

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < decimalNumber.length(); i++) {
            mantissa = Integer.parseInt(String.valueOf(decimalNumber.charAt(i)));
            if (mantissa == 0) {
                // Skip zeroes.
                decade--;
                continue;
            }
            result.append(RomanNumber.convert(mantissa, decade--));
        }

        return result.toString();
    }

    enum RomanNumber {
        NOT_A_NUMBER(0),
        I(1),
        V(5),
        X(10),
        L(50),
        C(100),
        D(500),
        M(1000);

        private static final List<RomanNumber> ONES = Arrays.asList(I, X, C, M);
        private static final List<RomanNumber> FIVES = Arrays.asList(V, L, D);

        private final int decimalValue;

        RomanNumber(int decimalValue) {
            this.decimalValue = decimalValue;
        }

        int toDecimal() {
            return decimalValue;
        }

        static String convert(int mantissa, int decade) {
            final int decimalNumber = mantissa * (int) Math.pow(10, decade);
            String result = NOT_A_NUMBER.name();
            RomanNumber romanNumber;
            if ((mantissa < 4 || (mantissa > 4 && mantissa < 9)) && decade <= 3) {
                // 1,2,3 and 5,6,7,8 and x10, x100, x1000.
                romanNumber = mantissa < 4 ? ONES.get(decade) : FIVES.get(decade);
                result = addOnesToRomanNumber(romanNumber, decimalNumber, decade);
            } else if ((mantissa == 4 || mantissa == 9) && decade < 3) {
                // 4 and 9 and x10, x100.
                romanNumber = mantissa == 4 ? FIVES.get(decade) : ONES.get(decade + 1);
                result = String.format("%s%s", ONES.get(decade), romanNumber);
            } else if (mantissa < 1 || mantissa > 9 || decade > 3) {
                // Max encodable roman number is 3999 MMMCMXCIX so decade cannot be > 3.
                result = NOT_A_NUMBER.name();
            }
            return result;
        }

        private static String addOnesToRomanNumber(RomanNumber romanNumber, int decimalNumber, int decade) {
            StringBuilder result = new StringBuilder(romanNumber.name());
            RomanNumber one = ONES.get(decade);
            int sum = romanNumber.toDecimal();
            int identicalSymbols = romanNumber == one ? 1 : 0;
            while (sum < decimalNumber && identicalSymbols <= MAX_IDENTICAL_SYMBOLS_IN_A_ROW) {
                result.append(one);
                sum += one.toDecimal();
                identicalSymbols++;
            }
            return result.toString();
        }
    }
}
