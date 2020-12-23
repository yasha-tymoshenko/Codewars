package com.tymoshenko.codewars;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PathFinderTest {

    @Test
    void sampleTests() {

        String a = ".W.\n" +
                ".W.\n" +
                "...",

                b = ".W.\n" +
                        ".W.\n" +
                        "W..",

                c = "......\n" +
                        "......\n" +
                        "......\n" +
                        "......\n" +
                        "......\n" +
                        "......",

                d = "......\n" +
                        "......\n" +
                        "......\n" +
                        "......\n" +
                        ".....W\n" +
                        "....W.";

        assertEquals(4, PathFinder.pathFinder(a), a);
        assertEquals(-1, PathFinder.pathFinder(b), b);
        assertEquals(10, PathFinder.pathFinder(c), c);
        assertEquals(-1, PathFinder.pathFinder(d), d);
    }
}