package com.tymoshenko.codewars;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void incProgress() {
        /*
        User user = new User();
        user.rank; // => -8
        user.progress; // => 0
        user.incProgress(-7);
        user.progress; // => 10
        user.incProgress(-5); // will add 90 progress
        user.progress; // => 0 // progress is now zero
        user.rank; // => -7 // rank was upgraded to -7
        */
        User user = new User();
        assertEquals(-8, user.rank);
        assertEquals(0, user.progress);

        user.incProgress(-7);
        assertEquals(10, user.progress);

        user.incProgress(-5);
        assertEquals(0, user.progress);
        assertEquals(-7, user.rank);

        user.rank = -1;
        user.progress = 0;
        user.incProgress(1);
        assertEquals(-1, user.rank);
        assertEquals(10, user.progress);
    }

}