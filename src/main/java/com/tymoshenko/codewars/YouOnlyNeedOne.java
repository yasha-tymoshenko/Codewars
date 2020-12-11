package com.tymoshenko.codewars;

import java.util.Arrays;
import java.util.function.Predicate;

/**
 * You will be given an array a and a value x. All you need to do is check whether the provided array contains the value.
 *
 * Array can contain numbers or strings. X can be either.
 *
 * Return true if the array contains the value, false if not.
 */
public class YouOnlyNeedOne {

    private YouOnlyNeedOne() {
    }

    public static boolean check(Object[] a, Object x) {
        // Better solution 1
        /*
            Arrays.asList(a).contains(x);
         */

        // Better solution 2
        /*
             Arrays.stream( a ).anyMatch( e -> e == x );
         */

        Predicate<Object> predicate = x instanceof Number ? Number.class::isInstance :
                object -> (object instanceof String || object instanceof  Character);
        return Arrays.stream(a)
                .filter(predicate)
                .anyMatch(x::equals);
    }

}
