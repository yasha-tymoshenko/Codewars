package com.tymoshenko.codewars;

/**
 * Complete the solution so that it splits the string into pairs of two characters.
 * If the string contains an odd number of characters then it should replace the missing second character of the final
 * pair with an underscore ('_').
 *
 * Examples:
 *
 * StringSplit.solution("abc") // should return {"ab", "c_"}
 * StringSplit.solution("abcdef") // should return {"ab", "cd", "ef"}
 */
public class StringSplit {

    private StringSplit() {
    }

    /**
     * Better:
     *
     *  public static String[] solution(String s) {
     *         s = (s.length() % 2 == 0)?s:s+"_";
     *         return s.split("(?<=\\G.{2})");
     *  }
     *
     */
    public static String[] solution(String s) {
        int count = 0;
        String pair;
        char second;
        String[] split = new String[(int) Math.ceil((double) s.length() / 2.0)];
        for (int i = 0; i < s.length(); i += 2) {
            second = i < s.length() - 1 ? s.charAt(i + 1) : '_';
            pair = s.charAt(i) + String.valueOf(second);
            split[count++] = pair;
        }
        return split;
    }

}
