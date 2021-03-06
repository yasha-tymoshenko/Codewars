package com.tymoshenko.codewars.romannumber;

import java.util.LinkedList;
import java.util.List;

import static com.tymoshenko.codewars.romannumber.RomanDigit.*;
import static java.util.stream.Collectors.joining;

public class RomanNumeralsEncoderOOP implements DecimalToRomanConverter {

    @Override
    public String solution(int n) {
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
        while (roman.isLessThan(M.multiply(thousands))) {
            roman.addRight(M);
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
                while (roman.isLessThan(first.multiply(number))) {
                    roman.addRight(first);
                }
                return roman;
        }
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

    RomanNumber addLeft(RomanDigit other) {
        return this.toRoman().addLeft(other.toRoman());
    }

    RomanNumber toRoman() {
        return new RomanNumber(this);
    }

    int toDecimal() {
        return decimalValue;
    }

    int multiply(int number) {
        return this.toDecimal() * number;
    }
}

final class RomanNumber {
    static RomanNumber zero() {
        return new RomanNumber(RomanDigit.ZERO);
    }

    private List<RomanDigit> digits;

    RomanNumber(RomanDigit digit) {
        this.digits = new LinkedList<>();
        if (RomanDigit.ZERO != digit) {
            this.digits.add(digit);
        }
    }

    boolean isNotZero() {
        return !digits.isEmpty();
    }

    void addRight(RomanDigit digit) {
        addRight(digit.toRoman());
    }

    RomanNumber addRight(RomanNumber other) {
        this.digits.addAll(other.digits);
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

    private int toDecimal() {
        return this.digits.stream()
                .mapToInt(RomanDigit::toDecimal)
                .sum();
    }

    @Override
    public String toString() {
        return this.digits.stream()
                .map(RomanDigit::toString)
                .collect(joining());
    }
}
