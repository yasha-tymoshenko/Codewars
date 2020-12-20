package com.tymoshenko.codewars;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Your goal is to return multiplication table for number that is always an integer from 1 to 10.
 * <p>
 * For example, a multiplication table (string) for number == 5 looks like below:
 * <p>
 * 1 * 5 = 5
 * 2 * 5 = 10
 * 3 * 5 = 15
 * 4 * 5 = 20
 * 5 * 5 = 25
 * 6 * 5 = 30
 * 7 * 5 = 35
 * 8 * 5 = 40
 * 9 * 5 = 45
 * 10 * 5 = 50
 * P. S. You can use \n in string to jump to the next line.
 * <p>
 * Note: newlines should be added between rows, but there should be no trailing newline at the end.
 */
class MultiplicationTableTest {

    @Test
    void test1() {
        String actual = MultiplicationTable.multiTable(5);
        assertEquals("1 * 5 = 5\n2 * 5 = 10\n3 * 5 = 15\n4 * 5 = 20\n5 * 5 = 25\n6 * 5 = 30\n7 * 5 = 35\n8 * 5 = 40\n9 * 5 = 45\n10 * 5 = 50", actual);
    }

    @Test
    void test2() {
        String actual = MultiplicationTable.multiTable(1);
        assertEquals("1 * 1 = 1\n2 * 1 = 2\n3 * 1 = 3\n4 * 1 = 4\n5 * 1 = 5\n6 * 1 = 6\n7 * 1 = 7\n8 * 1 = 8\n9 * 1 = 9\n10 * 1 = 10", actual);
    }

}