package com.tymoshenko.codewars.romannumber;

import java.util.HashMap;
import java.util.Map;

/**
 * 4kyu
 * <p>
 * https://www.codewars.com/kata/51b66044bce5799a7f000003/train/java
 */
public class RomanNumerals {

    private static final char[] ONES = new char[]{'I', 'V', 'X'};
    private static final char[] TENS = new char[]{'X', 'L', 'C'};
    private static final char[] HUNDREDS = new char[]{'C', 'D', 'M'};
    private static final Map<Character, Integer> romanToArabicMap = new HashMap<>();

    static {
        romanToArabicMap.put('I', 1);
        romanToArabicMap.put('V', 5);
        romanToArabicMap.put('X', 10);
        romanToArabicMap.put('L', 50);
        romanToArabicMap.put('C', 100);
        romanToArabicMap.put('D', 500);
        romanToArabicMap.put('M', 1000);
    }

    public static String toRoman(int decimal) {
        return thousandsToRoman(decimal / 1000)
                .append(toRoman((decimal % 1000) / 100, HUNDREDS))
                .append(toRoman((decimal % 100) / 10, TENS))
                .append(toRoman(decimal % 10, ONES))
                .toString();
    }

    public static int fromRoman(String romanNumeral) {
        if (romanNumeral == null || romanNumeral.isBlank()) {
            return 0;
        }
        int i = 0;
        int sum = 0;
        while (i < romanNumeral.length()) {
            int current = toArabic(romanNumeral.charAt(i));
            if (i + 1 < romanNumeral.length()) {
                int next = toArabic(romanNumeral.charAt(i + 1));
                if (current < next) {
                    sum += next - current;
                    i++;
                } else {
                    sum += current;
                }
            } else {
                sum += current;
            }
            i++;
        }
        return sum;
    }

    private static StringBuilder thousandsToRoman(int thousands) {
        StringBuilder sb = new StringBuilder();
        while (thousands > 0) {
            sb.append(HUNDREDS[2]);
            thousands--;
        }
        return sb;
    }

    private static StringBuilder toRoman(int decimalNumber, char[] romans) {
        StringBuilder sb = new StringBuilder();
        char one = romans[0];
        char five = romans[1];
        char ten = romans[2];
        switch (decimalNumber) {
            case 0:
                break;
            case 4:
                sb.append(one).append(five);
                break;
            case 9:
                sb.append(one).append(ten);
                break;
            default:
                if (decimalNumber >= 5) {
                    sb.append(five);
                }
                sb.append(String.valueOf(one).repeat(decimalNumber % 5));
                break;
        }
        return sb;
    }

    private static int toArabic(char roman) {
        return romanToArabicMap.getOrDefault(Character.toUpperCase(roman), 0);
    }
}
