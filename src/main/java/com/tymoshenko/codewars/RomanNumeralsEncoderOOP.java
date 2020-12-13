package com.tymoshenko.codewars;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static com.tymoshenko.codewars.RomanNumeralsEncoderOOP.RomanDigit.*;

public class RomanNumeralsEncoderOOP implements DecimalToRomanConverter {

    @Override
    public String solution(int n) {
        /*
         * E.g.
         * n = 4056
         *
         * ones = 6
         * tens = 5
         * hundreds = 0
         * thousands = 4
         */
        int ones = n % 10;
        int tens = (n % 100) / 10;
        int hundreds = (n % 1000) / 100;
        int thousands = n / 1000;

        return toRoman(thousands)
                .addRight(toRoman(hundreds, C, D, M))
                .addRight(toRoman(tens, X, L, C))
                .addRight(toRoman(ones, I, V, X))
                .toString();
    }

    private RomanNumber toRoman(int thousands) {
        RomanNumber roman = RomanNumber.zero();
        while (roman.isLessThan(M.times(thousands))) {
            roman = roman.addRight(M);
        }
        return roman;
    }

    private RomanNumber toRoman(int number, RomanDigit first, RomanDigit fifth, RomanDigit tenth) {
        switch (number) {
            case 0:
                return RomanNumber.zero();
            case 4:
                return fifth.addLeft(first);
            case 9:
                return tenth.addLeft(first);
            default:
                RomanNumber roman = RomanNumber.zero();
                if (number >= 5) {
                    roman.addRight(fifth);
                }
                while (roman.isNotEqualsTo(first.times(number))) {
                    roman.addRight(first);
                }
                return roman;
        }
    }

    enum RomanDigit {
        I(1), V(5),
        X(10), L(50),
        C(100), D(500),
        M(1000),
        ZERO(0) {
            @Override
            public String toString() {
                return "";
            }
        };

        private final int decimalValue;

        RomanDigit(int decimalValue) {
            this.decimalValue = decimalValue;
        }

        int toDecimal() {
            return decimalValue;
        }

        int times(int number) {
            return this.toDecimal() * number;
        }

        RomanNumber addLeft(RomanDigit other) {
            return this.toNumber().addLeft(other.toNumber());
        }

        RomanNumber toNumber() {
            return new RomanNumber(this);
        }
    }

    private static final class RomanNumber {

        static RomanNumber zero() {
            return new RomanNumber(RomanDigit.ZERO);
        }

        private List<RomanDigit> digits;

        private RomanNumber(RomanDigit digit) {
            this.digits = new LinkedList<>();
            if (RomanDigit.ZERO != digit) {
                this.digits.add(digit);
            }
        }

        boolean isNotZero() {
            return !(digits.isEmpty() || digits.get(0) == ZERO);
        }

        RomanNumber addRight(RomanDigit digit) {
            return addRight(digit.toNumber());
        }

        RomanNumber addRight(RomanNumber other) {
            if (other.isNotZero()) {
                this.digits.addAll(other.digits);
            }
            return this;
        }

        RomanNumber addLeft(RomanNumber other) {
            if (other.isNotZero()) {
                List<RomanDigit> tmp = this.digits;
                this.digits = other.digits;
                this.digits.addAll(tmp);
            }
            return this;
        }

        boolean isLessThan(int number) {
            return this.toDecimal() < number;
        }

        boolean isNotEqualsTo(int number) {
            return this.toDecimal() != number;
        }

        private int toDecimal() {
            return this.digits.stream()
                    .mapToInt(RomanDigit::toDecimal)
                    .sum();
        }

        @Override
        public String toString() {
            return this.digits.stream()
                    .map(RomanDigit::toString)
                    .collect(Collectors.joining());
        }
    }
}
