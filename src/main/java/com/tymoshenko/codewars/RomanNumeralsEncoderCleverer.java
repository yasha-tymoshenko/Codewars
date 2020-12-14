package com.tymoshenko.codewars;

@SuppressWarnings("unused")
public class RomanNumeralsEncoderCleverer implements DecimalToRomanConverter {

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

        return thousandsToRoman(thousands)
                .append(toRoman(hundreds, "C", "D", "M"))
                .append(toRoman(tens, "X", "L", "C"))
                .append(toRoman(ones, "I", "V", "X"))
                .toString();
    }

    private StringBuilder thousandsToRoman(int thousands) {
        StringBuilder sb = new StringBuilder();
        while (thousands > 0) {
            sb.append("M");
            thousands--;
        }
        return sb;
    }

    private StringBuilder toRoman(int number, String one, String five, String ten) {
        StringBuilder sb = new StringBuilder();
        switch (number) {
            case 0:
                // Skip zeroes.
                break;
            case 4:
                sb.append(one).append(five);
                break;
            case 9:
                sb.append(one).append(ten);
                break;
            default:
                if (number >= 5) {
                    sb.append(five);
                }
                int moduloFive = number % 5;
                while (moduloFive > 0) {
                    sb.append(one);
                    moduloFive--;
                }
                break;
        }
        return sb;
    }

}
